package com.trymad.litechess_monolith.matchmaking.api.event;

import java.util.List;
import java.util.UUID;

import com.trymad.litechess_monolith.shared.event.DomainEvent;
import com.trymad.litechess_monolith.websocket.api.dto.CreateGameRequest;

public record GameFindedEvent(List<UUID> players, CreateGameRequest gameRequest) implements DomainEvent {
	
} 
