package com.trymad.litechess_monolith.websocket.api.event;

import java.util.UUID;

import com.trymad.litechess_monolith.shared.event.DomainEvent;
import com.trymad.litechess_monolith.websocket.CreateGameRequest;

public record QueueRegistryEvent(CreateGameRequest request, UUID playerId) implements DomainEvent {
	
}
