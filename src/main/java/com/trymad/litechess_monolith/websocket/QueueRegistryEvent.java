package com.trymad.litechess_monolith.websocket;

import java.util.UUID;

public record QueueRegistryEvent(CreateGameRequest request, UUID playerId) {
	
}
