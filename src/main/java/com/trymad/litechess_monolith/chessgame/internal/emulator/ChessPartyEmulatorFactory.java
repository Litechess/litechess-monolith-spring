package com.trymad.litechess_monolith.chessgame.internal.emulator;

import com.trymad.litechess_monolith.chessgame.internal.model.ChessParty;

public interface ChessPartyEmulatorFactory {
	
	ChessPartyEmulator create(ChessParty chessParty);

}
