package com.trymad.litechess_monolith.matchmaking.internal;

import com.trymad.litechess_monolith.websocket.GameFindedEvent;

public interface GameFindedEventPublisher {
	
	void publish(GameFindedEvent event);

}
