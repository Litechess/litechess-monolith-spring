package com.trymad.litechess_monolith.livegame.internal.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.chessparty.api.event.MoveAcceptedEvent;
import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;
import com.trymad.litechess_monolith.livegame.api.event.GameFinishEvent;
import com.trymad.litechess_monolith.livegame.api.event.LiveGameStartEvent;
import com.trymad.litechess_monolith.livegame.internal.controller.filter.LiveGameFilter;
import com.trymad.litechess_monolith.livegame.internal.emulator.ChessPartyEmulator;
import com.trymad.litechess_monolith.livegame.internal.mapper.LiveGameMapper;
import com.trymad.litechess_monolith.livegame.internal.mapper.MoveMapper;
import com.trymad.litechess_monolith.livegame.internal.model.GameTimer;
import com.trymad.litechess_monolith.livegame.internal.model.LiveGame;
import com.trymad.litechess_monolith.livegame.internal.model.TimerHistory;
import com.trymad.litechess_monolith.livegame.internal.repository.LiveGameRepository;
import com.trymad.litechess_monolith.shared.event.EventPublisher;
import com.trymad.litechess_monolith.websocket.api.event.MoveEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LiveGameService  {

	private final LiveGameRepository liveGameRepository;
	
	private final GameTimeService gameTimeService;
	private final ChessPartyEmulatorService emulatorService;

	private final MoveMapper moveMapper;
	private final LiveGameMapper liveGameMapper;

	private final EventPublisher eventPublisher;

	public LiveGame create(ChessPartyDTO chessParty) {
		if(chessParty.status() != ChessGameStatus.NOT_FINISHED) {
			throw new IllegalStateException("Can't create live game for finished party: " + chessParty.id());
		}

		final GameTimer gameTimer = 
			gameTimeService.createTimer(chessParty.timeControl(), new TimerHistory(chessParty.timerHistory()));
		final LiveGame newLiveGame = new LiveGame(chessParty, gameTimer);
		emulatorService.createEmulator(chessParty.id()).setPosition(chessParty.moves());

		final LiveGame gameFromRepo = liveGameRepository.save(newLiveGame);
		gameTimeService.startTimer(chessParty.id(), gameTimer, whenTimeout(chessParty.id(), gameTimer));

		final LiveGameStartEvent event = new LiveGameStartEvent(liveGameMapper.toDto(gameFromRepo));
		eventPublisher.publish(event);
		
		return gameFromRepo;
	}

	public LiveGame get(Long id) {
		return liveGameRepository.findById(id).orElseThrow(
			() -> new NoSuchElementException("Live game " + id + "not found"));
	}

	public List<LiveGame> get(LiveGameFilter filter) {
		return liveGameRepository.findAll(filter);
	}

	public void delete(Long id) {
		liveGameRepository.delete(id);
	}

	public boolean contains(Long id) {
		return liveGameRepository.existsById(id);
	}

	public List<LiveGame> getAll() {
		return liveGameRepository.findAll();
	}

	// need refactor with emulator when microservice migration
	public void playMove(MoveEvent event) {
		final LiveGame liveGame = this.get(event.gameId());
		if(event.playerId().equals(liveGame.getPlayer(liveGame.getCurrentTurnColor())) == false) {
			throw new IllegalStateException("It's not player turn: " + event.playerId());
		}

		final ChessPartyEmulator emulator = emulatorService.getEmulator(liveGame.getId());

		if(emulator.gameStatus() != ChessGameStatus.NOT_FINISHED) {
			this.finishGame(liveGame.getId(), emulator.gameStatus());
			return;
		}

		final GameMove move = emulator.move(moveMapper.toEntity(event.moveRequest()));
		liveGame.applyMove(move);

		if(liveGame.getTimer() != null) {
			final GameTimer gameTimer = liveGame.getTimer();
			final Runnable whenTimeout = whenTimeout(liveGame.getId(), gameTimer);
			gameTimeService.startTimer(liveGame.getId(), gameTimer, whenTimeout);
		}

		final MoveAcceptedEvent acceptedMoveEvent = 
			new MoveAcceptedEvent(event.moveRequest(), event.gameId(), event.playerId());
		eventPublisher.publish(acceptedMoveEvent);

		liveGameRepository.save(liveGame);

		if(emulator.gameStatus() != ChessGameStatus.NOT_FINISHED) {
			this.finishGame(liveGame.getId(), emulator.gameStatus());
			return;
		}
	}

	private Runnable whenTimeout(Long gameId, GameTimer gameTimer) {
		return () -> {
			final PlayerColor winner = gameTimer.getCurrentTurn().flip();
			final ChessGameStatus status = winner == PlayerColor.WHITE ? 
				ChessGameStatus.WIN_WHITE : ChessGameStatus.WIN_BLACK;
			finishGame(gameId, status);
		};
	}

	public void finishGame(Long gameId, ChessGameStatus status) {
		final LiveGame game = this.get(gameId);
		final GameFinishEvent event = new GameFinishEvent(liveGameMapper.toDto(game), status);
		eventPublisher.publish(event);

		liveGameRepository.delete(gameId);
		emulatorService.deleteEmulator(gameId);
	}
}
