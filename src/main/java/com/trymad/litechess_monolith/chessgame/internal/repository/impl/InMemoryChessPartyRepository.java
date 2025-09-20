package com.trymad.litechess_monolith.chessgame.internal.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.internal.model.ChessParty;
import com.trymad.litechess_monolith.chessgame.internal.repository.ChessPartyRepository;


@Component
public class InMemoryChessPartyRepository implements ChessPartyRepository {
	private final Map<Long, ChessParty> store = new ConcurrentHashMap<>();
	private Long idSequence = 1l;

	@Override
	public Optional<ChessParty> getById(Long id) {
		return Optional.of(store.get(id));
	}

	@Override
	public boolean existsById(Long id) {
		return store.containsKey(id);
	}

	@Override
	public ChessParty save(ChessParty chessParty) {
		final Long gameId = idSequence++;
		chessParty.setId(gameId);
		store.put(gameId, chessParty);

		return chessParty;
	}

	@Override
	public List<ChessParty> getAll() {
		final List<ChessParty> parties = new ArrayList<>(store.size());
		parties.addAll(store.values());
		return parties;
	}
	
}
