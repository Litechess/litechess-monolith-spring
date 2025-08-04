package com.trymad.litechess_monolith.chessgame.internal.game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.ChessPartyRepository;

@Component
public class InMemoryChessPartyRepository implements ChessPartyRepository {

	private final Map<Long, ChessParty> store = new ConcurrentHashMap<>();

	@Override
	public ChessParty getById(Long id) {
		return store.get(id);
	}

	@Override
	public boolean existsById(Long id) {
		return store.containsKey(id);
	}

	@Override
	public ChessParty save(ChessParty party) {
		return store.put(party.getId(), party);
	}
	
}
