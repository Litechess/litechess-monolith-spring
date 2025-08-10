package com.trymad.litechess_monolith.chessgame.internal.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.trymad.litechess_monolith.chessgame.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.ChessPartyDTO;
import com.trymad.litechess_monolith.chessgame.ChessPartyService;
import com.trymad.litechess_monolith.chessgame.CreatePartyDTO;
import com.trymad.litechess_monolith.chessgame.GameMove;
import com.trymad.litechess_monolith.chessgame.internal.event.ChessPartyCreatedEventPublisher;
import com.trymad.litechess_monolith.chessgame.internal.game.ChessPartyRepository;
import com.trymad.litechess_monolith.chessgame.internal.model.LiveGame;
import com.trymad.litechess_monolith.websocket.ChessPartyCreatedEvent;
import com.trymad.litechess_monolith.websocket.GameFindedEvent;
import com.trymad.litechess_monolith.websocket.MoveEvent;
import com.trymad.litechess_monolith.websocket.MoveRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

// TODO logic when game end
public class ChessPartyServiceImpl implements ChessPartyService{
	
	private final ChessPartyRepository chessPartyRepository;
	private final LiveGameStore liveGameStore;
	private final ChessUtilService chessUtilService;
	private final ChessPartyCreatedEventPublisher createdEventPublisher;

	
	public boolean doMove(MoveEvent moveEvent) {
		final Long gameId = moveEvent.gameId();

		if(!liveGameStore.contains(gameId)) {
			ChessParty chessParty = chessPartyRepository.getById(gameId);
			if(chessParty.getStatus() != ChessGameStatus.NOT_FINISHED) {
				throw new IllegalStateException("Game is already finished: " + gameId);
			}
			
			liveGameStore.create(chessParty);
		}

		final LiveGame liveGame = liveGameStore.get(gameId);
		final MoveRequest moveRequest = moveEvent.moveRequest();
		final GameMove move = new GameMove(moveRequest.from(), moveRequest.to(), moveRequest.promotion(), moveRequest.san());

		return liveGame.playMove(move, moveEvent.playerId());
	}

	public ChessParty get(Long id) {
		if(liveGameStore.contains(id)) return liveGameStore.get(id).getChessParty();
		return chessPartyRepository.getById(id);
	}

	public boolean exists(Long id) {
		return chessPartyRepository.existsById(id);
	}
	
	public ChessParty save(ChessParty chessParty) {
		return chessPartyRepository.update(chessParty);
	}

	@Override
	public ChessParty create(CreatePartyDTO createGameDTO) {
		return chessPartyRepository.create(createGameDTO);
	}

	@Override
	public ChessPartyDTO getDto(ChessParty chessParty) {
		return new ChessPartyDTO(
			chessParty.getId(),
			chessParty.getWhite(),
			chessParty.getBlack(),
			chessParty.getMoveList().stream()
			.map(move -> {
				 return move.from() + move.to() + (move.promotion() == null ? "" : move.promotion().sanName().toLowerCase());
			}).toArray(String[]::new),
			chessUtilService.getFen(chessParty.getInitFen(), chessParty.getMoveList()),
			chessParty.getInitFen(),
			chessParty.getStatus());
	}

	@EventListener
	@Async
	void on(GameFindedEvent event) {
		final int whiteIndex = ThreadLocalRandom.current().nextInt(2);
		final int blackIndex = whiteIndex == 0 ? 1 : 0;
		final ChessParty chessParty = this.create(new CreatePartyDTO(
			event.players().get(whiteIndex),
			event.players().get(blackIndex)));
			
		createdEventPublisher.publish(new ChessPartyCreatedEvent(chessParty));
	}
}
