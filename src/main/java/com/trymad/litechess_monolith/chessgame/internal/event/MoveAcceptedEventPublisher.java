package com.trymad.litechess_monolith.chessgame.internal.event;

import com.trymad.litechess_monolith.websocket.MoveAcceptedEvent;

public interface MoveAcceptedEventPublisher {
	
	void publish(MoveAcceptedEvent event);

}
