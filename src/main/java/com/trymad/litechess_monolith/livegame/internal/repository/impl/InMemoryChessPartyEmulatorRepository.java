package com.trymad.litechess_monolith.livegame.internal.repository.impl;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.livegame.internal.emulator.ChessPartyEmulator;
import com.trymad.litechess_monolith.livegame.internal.repository.ChessPartyEmulatorRepository;

@Component
public class InMemoryChessPartyEmulatorRepository implements ChessPartyEmulatorRepository {

	private final Map<String, ChessPartyEmulator> store = new ConcurrentHashMap<>();

	@Override
	public Optional<ChessPartyEmulator> findById(String id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public ChessPartyEmulator save(String id, ChessPartyEmulator emulator) {
		store.put(id, emulator);
		return emulator;
	}

	@Override
	public void delete(String id) {
		store.remove(id);
	}
	
}
