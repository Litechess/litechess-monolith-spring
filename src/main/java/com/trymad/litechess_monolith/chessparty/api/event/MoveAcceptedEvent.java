package com.trymad.litechess_monolith.chessparty.api.event;

import java.util.Map;

import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;
import com.trymad.litechess_monolith.shared.event.DomainEvent;

public record MoveAcceptedEvent(
	GameMove move, String gameId, Map<PlayerColor, Long> timers
) implements DomainEvent {}

