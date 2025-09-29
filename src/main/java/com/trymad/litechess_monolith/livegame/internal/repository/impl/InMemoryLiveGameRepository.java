package com.trymad.litechess_monolith.livegame.internal.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;
import com.trymad.litechess_monolith.livegame.internal.controller.filter.LiveGameFilter;
import com.trymad.litechess_monolith.livegame.internal.model.LiveGame;
import com.trymad.litechess_monolith.livegame.internal.repository.LiveGameRepository;

@Component
public class InMemoryLiveGameRepository implements LiveGameRepository {

	private final Map<Long, LiveGame> store = new ConcurrentHashMap<>();

	@Override
	public Optional<LiveGame> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public List<LiveGame> findAll() {
		final List<LiveGame> list = new ArrayList<>(store.size());
		list.addAll(store.values());
		return list;
	}

	@Override
	public boolean existsById(Long id) {
		return store.containsKey(id);
	}

	@Override
	public LiveGame save(LiveGame liveGame) {
		store.put(liveGame.getId(), liveGame);
		return liveGame;
	}

	@Override
	public void delete(Long id) {
		store.remove(id);
	}

	@Override
	public List<LiveGame> findAll(LiveGameFilter filter) {
		final List<LiveGame> list = new ArrayList<>(store.size());
		for (LiveGame game : store.values()) {
			final UUID whiteId = game.getPlayer(PlayerColor.WHITE);
			final UUID blackId = game.getPlayer(PlayerColor.BLACK);
				
			final boolean isOwnerContains = (whiteId.equals(filter.ownerId()) || blackId.equals(filter.ownerId())) || filter.ownerId() == null;
			final boolean isOponentContains = (whiteId.equals(filter.oponentId()) || blackId.equals(filter.oponentId())) || filter.oponentId() == null;
				
			if(isOponentContains && isOwnerContains) list.add(game);
		}
		
		return list;
	}
	
}
