package com.trymad.litechess_monolith.websocket.api.event;

import java.util.UUID;

import com.trymad.litechess_monolith.infrastructure.event.DomainEvent;

public record UserOnlineEvent(UUID id) implements DomainEvent {
	
}
