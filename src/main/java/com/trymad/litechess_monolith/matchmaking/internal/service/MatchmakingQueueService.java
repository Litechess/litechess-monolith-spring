package com.trymad.litechess_monolith.matchmaking.internal.service;

import java.util.UUID;

import com.trymad.litechess_monolith.websocket.api.event.QueueRegistryEvent;

public interface MatchmakingQueueService {
	
	void add(QueueRegistryEvent event);

	void leave(UUID playerId);

}
