package com.trymad.litechess_monolith.websocket.internal.listener.challengeAccepted;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.infrastructure.event.EventListener;
import com.trymad.litechess_monolith.matchmaking.api.event.ChallengeAcceptedEvent;
import com.trymad.litechess_monolith.websocket.internal.service.GameMessageSender;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringChallengeAcceptedEventListener implements EventListener<ChallengeAcceptedEvent> {

	private final GameMessageSender gameMessageSender;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(ChallengeAcceptedEvent event) {
		gameMessageSender.challengeAccepted(event);
	}

}
