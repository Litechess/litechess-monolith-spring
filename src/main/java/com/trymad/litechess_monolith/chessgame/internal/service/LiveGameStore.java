package com.trymad.litechess_monolith.chessgame.internal.service;

import java.util.List;

import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.internal.model.LiveGame;
import com.trymad.litechess_monolith.websocket.MoveEvent;

public interface LiveGameStore {
	
	LiveGame create(ChessParty chessParty);

	LiveGame get(Long id);

	List<LiveGame> getAll();

	LiveGame delete(Long id);

	boolean contains(Long id);

	boolean doMove(MoveEvent move);

}
