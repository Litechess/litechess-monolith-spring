package com.trymad.litechess_monolith.websocket;

import java.util.UUID;

public record MoveEvent(
	MoveRequest moveRequest, Long gameId, UUID playerId
) {}
