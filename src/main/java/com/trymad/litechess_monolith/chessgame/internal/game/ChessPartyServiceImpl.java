package com.trymad.litechess_monolith.chessgame.internal.game;

import org.springframework.stereotype.Service;

import com.trymad.litechess_monolith.chessgame.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.ChessPartyService;
import com.trymad.litechess_monolith.chessgame.CreateGameDTO;
import com.trymad.litechess_monolith.chessgame.GameMove;
import com.trymad.litechess_monolith.chessgame.internal.model.LiveGame;
import com.trymad.litechess_monolith.websocket.MoveEvent;
import com.trymad.litechess_monolith.websocket.MoveRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

// TODO logic when game end
public class ChessPartyServiceImpl implements ChessPartyService{
	
	private final ChessPartyRepository chessPartyRepository;
	private final LiveGameStore liveGameStore;

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
		final GameMove move = new GameMove(moveRequest.from(), moveRequest.to(), moveRequest.promotion());

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
	public ChessParty create(CreateGameDTO createGameDTO) {
		return chessPartyRepository.create(createGameDTO);
	}

}
