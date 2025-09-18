package com.trymad.litechess_monolith.matchmaking.internal.listener.queueRegistry;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.matchmaking.internal.MatchmakingQueueService;
import com.trymad.litechess_monolith.shared.event.EventListener;
import com.trymad.litechess_monolith.websocket.api.event.QueueRegistryEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SpringMatchmakingQueueRegistryEventListener implements EventListener<QueueRegistryEvent> {

	private final MatchmakingQueueService matchmakingQueueService;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(QueueRegistryEvent event) {
		matchmakingQueueService.add(event);
	}
	
}
