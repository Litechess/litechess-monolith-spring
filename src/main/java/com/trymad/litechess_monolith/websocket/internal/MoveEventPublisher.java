package com.trymad.litechess_monolith.websocket.internal;

import com.trymad.litechess_monolith.websocket.MoveEvent;

public interface MoveEventPublisher {
	
	void publish(MoveEvent event);

}
