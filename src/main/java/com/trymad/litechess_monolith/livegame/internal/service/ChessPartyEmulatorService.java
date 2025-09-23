package com.trymad.litechess_monolith.livegame.internal.service;

import com.trymad.litechess_monolith.livegame.internal.emulator.ChessPartyEmulator;

public interface ChessPartyEmulatorService {
	
	ChessPartyEmulator getEmulator(Long gameId);

	ChessPartyEmulator createEmulator(Long gameId);

	void deleteEmulator(Long gameId);

}
