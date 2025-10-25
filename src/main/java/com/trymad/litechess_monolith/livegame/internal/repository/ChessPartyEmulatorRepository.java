package com.trymad.litechess_monolith.livegame.internal.repository;

import java.util.Optional;

import com.trymad.litechess_monolith.livegame.internal.emulator.ChessPartyEmulator;

public interface ChessPartyEmulatorRepository {
	
	Optional<ChessPartyEmulator> findById(String id);

	ChessPartyEmulator save(String id, ChessPartyEmulator emulator);

	void delete(String id);

}
