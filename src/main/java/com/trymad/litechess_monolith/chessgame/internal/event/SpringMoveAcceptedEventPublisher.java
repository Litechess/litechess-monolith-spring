package com.trymad.litechess_monolith.chessgame.internal.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.websocket.MoveAcceptedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringMoveAcceptedEventPublisher implements MoveAcceptedEventPublisher {

	private final ApplicationEventPublisher events;

	@Override
	public void publish(MoveAcceptedEvent event) {
		events.publishEvent(event);
	}
	
}
