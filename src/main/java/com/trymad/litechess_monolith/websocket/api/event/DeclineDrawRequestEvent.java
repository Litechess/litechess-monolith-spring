package com.trymad.litechess_monolith.websocket.api.event;

import java.util.UUID;

import com.trymad.litechess_monolith.shared.event.DomainEvent;
import com.trymad.litechess_monolith.websocket.api.model.GameEventType;

public record DeclineDrawRequestEvent(
	GameEventType type, 
	String gameId, 
	UUID playerId) implements DomainEvent {
	
	public DeclineDrawRequestEvent {
		type = GameEventType.DRAW_DECLINE;
	}
}