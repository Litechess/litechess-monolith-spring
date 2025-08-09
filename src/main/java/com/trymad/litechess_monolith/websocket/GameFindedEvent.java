package com.trymad.litechess_monolith.websocket;

import java.util.List;
import java.util.UUID;

public record GameFindedEvent(List<UUID> players, CreateGameRequest gameRequest) {
	
}
