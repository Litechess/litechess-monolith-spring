package com.trymad.litechess_monolith.chessgame.internal.game;

import com.trymad.litechess_monolith.chessgame.ChessParty;

public interface ChessPartyEmulatorFactory {
	
	ChessPartyEmulator create(ChessParty chessParty);

}
