package com.trymad.litechess_monolith.websocket.internal;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.websocket.MoveEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringMovePublisher implements MoveEventPublisher {

	private final ApplicationEventPublisher events;

	@Override
	public void publish(MoveEvent event) {
		events.publishEvent(event);
	}
	
}
