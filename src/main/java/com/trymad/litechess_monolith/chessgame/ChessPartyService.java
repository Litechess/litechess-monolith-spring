package com.trymad.litechess_monolith.chessgame;

import com.trymad.litechess_monolith.websocket.MoveEvent;

public interface ChessPartyService {
	
	boolean doMove(MoveEvent moveEvent);

	ChessParty get(Long partyId);

	boolean exists(Long partyId);

	ChessParty save(ChessParty chessParty);

	ChessParty create(CreateGameDTO createGameDTO);

}
