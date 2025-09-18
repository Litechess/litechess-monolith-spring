package com.trymad.litechess_monolith.matchmaking.api.event;

import java.util.UUID;

import com.trymad.litechess_monolith.shared.event.DomainEvent;

public record QueueLeaveEvent(UUID playerId) implements DomainEvent {
	
}
