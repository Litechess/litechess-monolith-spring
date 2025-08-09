package com.trymad.litechess_monolith.chessgame.internal.event;

import com.trymad.litechess_monolith.websocket.ChessPartyCreatedEvent;

public interface ChessPartyCreatedEventPublisher {
	
	void publish(ChessPartyCreatedEvent event);

}
