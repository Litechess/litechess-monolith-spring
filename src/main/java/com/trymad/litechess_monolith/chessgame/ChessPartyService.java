package com.trymad.litechess_monolith.chessgame;

import java.util.List;

import com.trymad.litechess_monolith.websocket.MoveEvent;

public interface ChessPartyService {
	
	boolean doMove(MoveEvent moveEvent);

	ChessParty get(Long partyId);

	List<ChessParty> getAll(boolean activeGames);

	boolean exists(Long partyId);

	ChessParty save(ChessParty chessParty);

	ChessParty create(CreatePartyDTO createGameDTO);

	ChessPartyDTO getDto(ChessParty chessParty); // move to mapper later TODO
}
