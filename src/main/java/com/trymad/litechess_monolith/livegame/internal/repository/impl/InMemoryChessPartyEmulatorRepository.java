package com.trymad.litechess_monolith.livegame.internal.repository.impl;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.livegame.internal.emulator.ChessPartyEmulator;
import com.trymad.litechess_monolith.livegame.internal.repository.ChessPartyEmulatorRepository;

@Component
public class InMemoryChessPartyEmulatorRepository implements ChessPartyEmulatorRepository {

	private final Map<Long, ChessPartyEmulator> store = new ConcurrentHashMap<>();

	@Override
	public Optional<ChessPartyEmulator> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public ChessPartyEmulator save(Long id, ChessPartyEmulator emulator) {
		return store.put(id, emulator);
	}

	@Override
	public void delete(Long id) {
		store.remove(id);
	}
	
}
