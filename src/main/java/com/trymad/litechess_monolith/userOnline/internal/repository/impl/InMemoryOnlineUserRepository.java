package com.trymad.litechess_monolith.userOnline.internal.repository.impl;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.userOnline.internal.repository.OnlineUserRepository;

@Component
public class InMemoryOnlineUserRepository implements OnlineUserRepository {

	final Set<UUID> store = ConcurrentHashMap.newKeySet();

	@Override
	public boolean isOnline(UUID userId) {
		return store.contains(userId);
	}

	@Override
	public void setOnline(UUID userId) {
		store.add(userId);
	}

	@Override
	public void setOffline(UUID userId) {
		store.remove(userId);
	}
	
}
