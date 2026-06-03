package com.trymad.litechess_monolith.websocket.internal.listener.challengeCreated;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.infrastructure.event.EventListener;
import com.trymad.litechess_monolith.matchmaking.api.event.ChallengeCreatedEvent;
import com.trymad.litechess_monolith.websocket.internal.service.GameMessageSender;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringChallengeCreatedEventListener implements EventListener<ChallengeCreatedEvent> {
	
	private final GameMessageSender gameMessageSender;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(ChallengeCreatedEvent event) {
		gameMessageSender.challengeCreated(event);
	}
	
}
