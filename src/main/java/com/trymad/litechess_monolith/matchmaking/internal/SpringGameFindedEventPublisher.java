package com.trymad.litechess_monolith.matchmaking.internal;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.websocket.GameFindedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringGameFindedEventPublisher implements GameFindedEventPublisher {

	private final ApplicationEventPublisher publisher;

	@Override
	public void publish(GameFindedEvent event) {
		publisher.publishEvent(event);
	}
	
}
