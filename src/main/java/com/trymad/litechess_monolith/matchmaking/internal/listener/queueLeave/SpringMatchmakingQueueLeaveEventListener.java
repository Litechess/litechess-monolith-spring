package com.trymad.litechess_monolith.matchmaking.internal.listener.queueLeave;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.matchmaking.api.event.QueueLeaveEvent;
import com.trymad.litechess_monolith.matchmaking.internal.service.MatchmakingQueueService;
import com.trymad.litechess_monolith.shared.event.EventListener;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SpringMatchmakingQueueLeaveEventListener implements EventListener<QueueLeaveEvent> {
	
	private final MatchmakingQueueService queueService;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(QueueLeaveEvent event) {
		queueService.leave(event.playerId());
	}

}
