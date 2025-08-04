package com.trymad.litechess_monolith.websocket;

import java.util.UUID;

public record MoveAcceptedEvent(
	MoveRequest moveRequest, Long gameId, UUID playerId
) {}

