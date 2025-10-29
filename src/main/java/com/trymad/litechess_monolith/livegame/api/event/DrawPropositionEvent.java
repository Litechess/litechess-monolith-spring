package com.trymad.litechess_monolith.livegame.api.event;

import java.util.UUID;

import com.trymad.litechess_monolith.shared.event.DomainEvent;
import com.trymad.litechess_monolith.websocket.api.model.GameEventType;

public record DrawPropositionEvent(
	GameEventType type, 
	String gameId, 
	UUID playerId) implements DomainEvent {
	
	public DrawPropositionEvent {
		type = GameEventType.DRAW_PROPOSITION;
	}
}
