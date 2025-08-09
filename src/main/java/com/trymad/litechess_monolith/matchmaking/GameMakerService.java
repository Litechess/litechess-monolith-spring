package com.trymad.litechess_monolith.matchmaking;

import java.util.UUID;

import com.trymad.litechess_monolith.websocket.CreateGameRequest;

public interface GameMakerService {
	
	public void addInQueue(CreateGameRequest gameRequest, UUID playerId);

}
