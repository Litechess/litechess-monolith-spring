package com.trymad.litechess_monolith.chessgame.internal.service;

import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.internal.model.LiveGame;

public interface LiveGameStore {
	
	LiveGame create(ChessParty chessParty);

	LiveGame get(Long id);

	LiveGame delete(Long id);

	boolean contains(Long id);

}
