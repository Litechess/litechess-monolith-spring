package com.trymad.litechess_monolith.matchmaking.internal.listener.userOffline;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.matchmaking.internal.service.MatchmakingQueueService;
import com.trymad.litechess_monolith.shared.event.EventListener;
import com.trymad.litechess_monolith.websocket.api.event.UserOfflineEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SpringMatchmakingUserOfflineEventListener implements EventListener<UserOfflineEvent> {

	private final MatchmakingQueueService service;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(UserOfflineEvent event) {
		service.leave(event.userId());
	}
	
}
