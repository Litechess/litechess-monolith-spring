package com.trymad.litechess_monolith.matchmaking.internal;

import java.util.UUID;

import com.trymad.litechess_monolith.websocket.api.event.QueueRegistryEvent;

public interface MatchmakingQueueService {
	
	void add(QueueRegistryEvent event);

	void leave(UUID playerId);

}
