package com.trymad.litechess_monolith.chessgame;

import java.util.List;

import com.trymad.litechess_monolith.chessgame.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.chessgame.api.dto.CreatePartyDTO;
import com.trymad.litechess_monolith.chessgame.api.event.GameFinishEvent;
import com.trymad.litechess_monolith.matchmaking.api.event.GameFindedEvent;
import com.trymad.litechess_monolith.websocket.api.event.MoveEvent;

public interface ChessPartyService {
	
	boolean doMove(MoveEvent moveEvent);

	ChessParty get(Long partyId);

	List<ChessParty> getAll(boolean activeGames);

	boolean exists(Long partyId);

	ChessParty save(ChessParty chessParty);

	ChessParty create(CreatePartyDTO createGameDTO);

	ChessPartyDTO getDto(ChessParty chessParty); // move to mapper later TODO

	boolean stopActiveGame(Long gameId);

	void finishGame(GameFinishEvent event);

	void createGame(GameFindedEvent event);
}
