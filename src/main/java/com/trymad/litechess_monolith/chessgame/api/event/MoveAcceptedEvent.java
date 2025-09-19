package com.trymad.litechess_monolith.chessgame.api.event;

import java.util.UUID;

import com.trymad.litechess_monolith.shared.event.DomainEvent;
import com.trymad.litechess_monolith.websocket.api.dto.MoveRequest;

public record MoveAcceptedEvent(
	MoveRequest moveRequest, Long gameId, UUID playerId
) implements DomainEvent {}

