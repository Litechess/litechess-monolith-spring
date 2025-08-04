package com.trymad.litechess_monolith.chessgame.internal.game;

import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.internal.model.LiveGame;

public interface LiveGameStore {
	
	LiveGame createGame(ChessParty chessParty, ChessPartyEmulator partyEmulator);

	LiveGame get(Long id);

	LiveGame delete(Long id);

	boolean contains(Long id);

}
