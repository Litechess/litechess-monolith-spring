package com.trymad.litechess_monolith.livegame.internal.service;

import com.trymad.litechess_monolith.livegame.internal.emulator.ChessPartyEmulator;

public interface ChessPartyEmulatorService {
	
	ChessPartyEmulator getEmulator(String gameId);

	ChessPartyEmulator createEmulator(String gameId);

	void deleteEmulator(String gameId);

}
