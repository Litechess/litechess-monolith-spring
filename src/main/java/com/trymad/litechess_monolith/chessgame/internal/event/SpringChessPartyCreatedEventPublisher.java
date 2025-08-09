package com.trymad.litechess_monolith.chessgame.internal.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.websocket.ChessPartyCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringChessPartyCreatedEventPublisher implements ChessPartyCreatedEventPublisher {
	
	private final ApplicationEventPublisher eventPublisher;

	@Override
	public void publish(ChessPartyCreatedEvent event) {
		eventPublisher.publishEvent(event);
	}

}
