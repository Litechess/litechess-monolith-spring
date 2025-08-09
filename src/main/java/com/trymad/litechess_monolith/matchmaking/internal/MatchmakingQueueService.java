package com.trymad.litechess_monolith.matchmaking.internal;

import java.util.UUID;

import com.trymad.litechess_monolith.websocket.CreateGameRequest;

public interface MatchmakingQueueService {
	
	void add(CreateGameRequest createGameRequest, UUID playerId);

}
