package com.trymad.litechess_monolith.websocket.internal.service.gamemaker;

import com.trymad.litechess_monolith.websocket.api.event.QueueRegistryEvent;

public interface QueueRegistryPublisher {
	
	public void publish(QueueRegistryEvent event);

}
