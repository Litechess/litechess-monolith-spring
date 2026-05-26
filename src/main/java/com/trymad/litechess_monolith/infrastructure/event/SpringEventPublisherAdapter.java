package com.trymad.litechess_monolith.infrastructure.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SpringEventPublisherAdapter implements EventPublisher {

	private final ApplicationEventPublisher publisher;

	@Override
	public void publish(Event event) {
		publisher.publishEvent(event);
	}
	
}
