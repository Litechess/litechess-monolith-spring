package com.trymad.litechess_monolith.websocket.api.event;

import java.util.UUID;

import com.trymad.litechess_monolith.shared.event.DomainEvent;

public record PlayerSurrenderEvent(UUID playerId, String gameId) implements DomainEvent{
	
}
