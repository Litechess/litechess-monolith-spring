package com.trymad.litechess_monolith.chessgame.internal.game;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trymad.litechess_monolith.chessgame.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.ChessPartyRepository;
import com.trymad.litechess_monolith.chessgame.GameMove;
import com.trymad.litechess_monolith.chessgame.internal.model.LiveGame;
import com.trymad.litechess_monolith.chessgame.internal.model.PlayerColor;
import com.trymad.litechess_monolith.websocket.MoveEvent;
import com.trymad.litechess_monolith.websocket.MoveRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

// TODO logic when game end
public class ChessPartyService {
	
	private final ChessPartyRepository chessPartyRepository;
	private final ChessPartyEmulatorFactory emulatorFactory;
	private final LiveGameStore liveGameStore;

	public boolean doMove(MoveEvent moveEvent) {
		final Long gameId = moveEvent.gameId();

		if(!liveGameStore.contains(gameId)) {
			ChessParty chessParty = chessPartyRepository.getById(gameId);
			if(chessParty.getStatus() != ChessGameStatus.NOT_FINISHED) {
				throw new IllegalStateException("Game is already finished: " + gameId);
			}
			
			liveGameStore.createGame(chessParty, emulatorFactory.create(chessParty));
		}

		
		final LiveGame liveGame = liveGameStore.get(gameId);
		final ChessPartyEmulator partyEmulator = liveGame.emulator();
		final Map<PlayerColor, UUID> players = liveGame.players();
		final PlayerColor currentColorTurn = partyEmulator.getCurrentTurnColor();
		final MoveRequest moveRequest = moveEvent.moveRequest();
		final GameMove move = new GameMove(moveRequest.from(), moveRequest.to(), moveRequest.promotion());

		if(!players.get(currentColorTurn).equals(moveEvent.playerId()) ||
			!partyEmulator.isLegalMove(move)) {
			return false;	
		}

		partyEmulator.move(move);
		return true;
	}

	public ChessParty get(Long id) {
		return chessPartyRepository.getById(id);
	}

	public boolean exists(Long id) {
		return chessPartyRepository.existsById(id);
	}
	
	public ChessParty save(ChessParty chessParty) {
		return chessPartyRepository.save(chessParty);
	}
}
