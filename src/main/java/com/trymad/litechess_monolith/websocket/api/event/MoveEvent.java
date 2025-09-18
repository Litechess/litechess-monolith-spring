package com.trymad.litechess_monolith.websocket.api.event;

import java.util.UUID;

import com.trymad.litechess_monolith.shared.event.DomainEvent;
import com.trymad.litechess_monolith.websocket.MoveRequest;

public record MoveEvent(
	MoveRequest moveRequest, Long gameId, UUID playerId
) implements DomainEvent {}
