package com.trymad.litechess_monolith.websocket.api.event;

import java.util.UUID;

import com.trymad.litechess_monolith.shared.event.DomainEvent;
import com.trymad.litechess_monolith.websocket.api.model.GameEventType;

public record DrawPropositionRequestEvent(
	GameEventType type, 
	String gameId, 
	UUID playerId) implements DomainEvent {
	
	public DrawPropositionRequestEvent {
		type = GameEventType.DRAW_PROPOSITION;
	}
}
