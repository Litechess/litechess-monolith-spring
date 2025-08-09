package com.trymad.litechess_monolith.websocket.internal.service.gamemaker;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.websocket.QueueRegistryEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringQueueRegistryPublisher implements QueueRegistryPublisher {

	private final ApplicationEventPublisher events;

	@Override
	public void publish(QueueRegistryEvent event) {
		events.publishEvent(event);
	}
	
}
