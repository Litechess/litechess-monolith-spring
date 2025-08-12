package com.trymad.litechess_monolith.chessgame.internal.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.GameFinishEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringGameFinishEventPublisher implements GameFinishEventPublisher {

	private final ApplicationEventPublisher publisher;

	@Override
	public void publish(GameFinishEvent event) {
		publisher.publishEvent(event);
	}
	
}
