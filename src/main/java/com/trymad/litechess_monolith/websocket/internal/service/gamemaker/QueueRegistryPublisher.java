package com.trymad.litechess_monolith.websocket.internal.service.gamemaker;

import com.trymad.litechess_monolith.websocket.QueueRegistryEvent;

public interface QueueRegistryPublisher {
	
	public void publish(QueueRegistryEvent event);

}
