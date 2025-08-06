package com.trymad.litechess_monolith.chessgame.internal.game.emulator;

import com.trymad.litechess_monolith.chessgame.ChessParty;

public interface ChessPartyEmulatorFactory {
	
	ChessPartyEmulator create(ChessParty chessParty);

}
