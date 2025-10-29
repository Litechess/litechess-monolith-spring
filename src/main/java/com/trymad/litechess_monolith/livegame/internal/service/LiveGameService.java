package com.trymad.litechess_monolith.livegame.internal.service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.chessparty.api.event.MoveAcceptedEvent;
import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;
import com.trymad.litechess_monolith.chessparty.api.model.TimeControlType;
import com.trymad.litechess_monolith.livegame.api.event.DeclineDrawEvent;
import com.trymad.litechess_monolith.livegame.api.event.DrawPropositionEvent;
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
import com.trymad.litechess_monolith.websocket.api.model.GameEventType;

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

	private final Logger logger = Logger.getLogger("liveService");

	public LiveGame create(ChessPartyDTO chessParty) {
		if(chessParty.status() != ChessGameStatus.NOT_FINISHED) {
			throw new IllegalStateException("Can't create live game for finished party: " + chessParty.id());
		}

		final GameTimer gameTimer = chessParty.timeControl().type() == TimeControlType.NO_CONTROL ? null :
			gameTimeService.createTimer(chessParty.timeControl(), new TimerHistory(chessParty.timerHistory()));
		
		final LiveGame newLiveGame = new LiveGame(chessParty, gameTimer);
		emulatorService.createEmulator(chessParty.id()).setPosition(chessParty.moves());

		final LiveGame gameFromRepo = liveGameRepository.save(newLiveGame);

		if(gameTimer != null) {
			gameTimer.start();
			gameTimeService.startTimer(chessParty.id(), gameTimer, whenTimeout(chessParty.id(), gameTimer));
		}


		final LiveGameStartEvent event = new LiveGameStartEvent(liveGameMapper.toDto(gameFromRepo));
		eventPublisher.publish(event);

		return gameFromRepo;
	}

	public LiveGame get(String id) {
		return liveGameRepository.findById(id).orElseThrow(
			() -> new NoSuchElementException("Live game " + id + "not found"));
	}

	public List<LiveGame> get(LiveGameFilter filter) {
		return liveGameRepository.findAll(filter);
	}

	public void delete(String id) {
		liveGameRepository.delete(id);
	}

	public boolean contains(String id) {
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

		final Map<PlayerColor, Long> timers = liveGame.getTimer() == null ? null : new HashMap<>();

		if(liveGame.isDrawProposed()) {
			declineDraw(liveGame, event.playerId());
		}

		liveGame.applyMove(move);
		if(liveGame.getTimer() != null) {
			final GameTimer gameTimer = liveGame.getTimer();
			final Runnable whenTimeout = whenTimeout(liveGame.getId(), gameTimer);
			gameTimeService.startTimer(liveGame.getId(), gameTimer, whenTimeout);
			timers.put(PlayerColor.WHITE, gameTimer.getRemainingTime(PlayerColor.WHITE).toEpochMilli());
			timers.put(PlayerColor.BLACK, gameTimer.getRemainingTime(PlayerColor.BLACK).toEpochMilli());

			logger.info(timers.toString());
		}


		final MoveAcceptedEvent acceptedMoveEvent = 
			new MoveAcceptedEvent(move, event.gameId(), timers);
		eventPublisher.publish(acceptedMoveEvent);

		liveGameRepository.save(liveGame);

		if(emulator.gameStatus() != ChessGameStatus.NOT_FINISHED) {
			this.finishGame(liveGame.getId(), emulator.gameStatus());
			return;
		}
	}

	private Runnable whenTimeout(String gameId, GameTimer gameTimer) {
		return () -> {
			logger.warning("TIMEOUT NOW");
			gameTimer.stop();
			final PlayerColor winner = gameTimer.getCurrentTurn().flip();
			final ChessGameStatus status = winner == PlayerColor.WHITE ? 
				ChessGameStatus.TIMEOUT_WIN_WHITE : ChessGameStatus.TIMEOUT_WIN_BLACK;
			
			logger.info("TIMEOUT, WIN: " + status);
			finishGame(gameId, status);
		};
	}

	public void surrender(String gameId, UUID playerId) {
		final LiveGame game = this.get(gameId);
		final boolean isGamePlayer = isPlayerInGame(game, playerId);

		if(!isGamePlayer) throw new IllegalArgumentException("Player " + playerId + " is not in game " + gameId);

		final ChessGameStatus status = game.getPlayerSides().get(PlayerColor.WHITE).equals(playerId) ? 
			ChessGameStatus.SURRENDER_WIN_BLACK : ChessGameStatus.SURRENDER_WIN_WHITE;

		finishGame(gameId, status);
	}

	public void drawProposition(LiveGame game, UUID playerId) {
		final boolean isGamePlayer = isPlayerInGame(game, playerId);

		if(!isGamePlayer) throw new IllegalArgumentException("Player " + playerId + " is not in game " + game.getId());

		if(game.isDrawProposed()) {
			if(game.getDrawSender().get().equals(playerId)) return;
			finishGame(game.getId(), ChessGameStatus.DRAW);
			return;
		}

		game.proposeDraw(playerId);
		
		final DrawPropositionEvent event = new DrawPropositionEvent(GameEventType.DRAW_PROPOSITION, game.getId(), playerId);
		eventPublisher.publish(event);
	}

	public void drawProposition(String gameId, UUID playerId) {
		final LiveGame game = this.get(gameId);
		drawProposition(game, playerId);
	}

	public void declineDraw(String gameId, UUID playerId) {
		final LiveGame game = this.get(gameId);
		declineDraw(game, playerId);
	}

	public void declineDraw(LiveGame game, UUID playerId) {
		final boolean isGamePlayer = isPlayerInGame(game, playerId);
		if(!isGamePlayer) throw new IllegalArgumentException("Player " + playerId + " is not in game " + game.getId());

		final DeclineDrawEvent declineDrawEvent = new DeclineDrawEvent(GameEventType.DRAW_DECLINE, game.getId(), playerId);
		eventPublisher.publish(declineDrawEvent);
	}

	private boolean isPlayerInGame(LiveGame game, UUID playerId) {
		return game.getPlayerSides().values().stream().anyMatch(id -> id.equals(playerId));
	}

	public void finishGame(LiveGame game, ChessGameStatus status) {
		gameTimeService.stopTimer(game.getId());
		
		if(game.getTimer() != null) {
			game.getTimer().stop();
			if(status == ChessGameStatus.TIMEOUT_WIN_BLACK || status == ChessGameStatus.TIMEOUT_WIN_WHITE) {
				game.getTimerHistory().addTime(Duration.ZERO);
			}
		}

		final GameFinishEvent event = new GameFinishEvent(GameEventType.GAME_FINISH, liveGameMapper.toDto(game), status);
		eventPublisher.publish(event);

		liveGameRepository.delete(game.getId());
		emulatorService.deleteEmulator(game.getId());
	}

	public void finishGame(String gameId, ChessGameStatus status) {
		final LiveGame game = this.get(gameId);
		finishGame(game, status);
	}
}
