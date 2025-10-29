package com.trymad.litechess_monolith.livegame.api.event;

import java.util.UUID;

import com.trymad.litechess_monolith.shared.event.DomainEvent;
import com.trymad.litechess_monolith.websocket.api.model.GameEventType;

public record DeclineDrawEvent(
	GameEventType type, 
	String gameId, 
	UUID playerId) implements DomainEvent {
	
	public DeclineDrawEvent {
		type = GameEventType.DRAW_DECLINE;
	}
	
}
