package com.trymad.litechess_monolith.chessparty.api.event;

import java.util.UUID;

import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.shared.event.DomainEvent;

public record MoveAcceptedEvent(
	GameMove move, Long gameId, UUID playerId
) implements DomainEvent {}

