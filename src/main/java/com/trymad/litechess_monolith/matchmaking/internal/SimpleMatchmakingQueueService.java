package com.trymad.litechess_monolith.matchmaking.internal;

import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.event.queueLeave.QueueLeaveEvent;
import com.trymad.litechess_monolith.matchmaking.api.event.GameFindedEvent;
import com.trymad.litechess_monolith.shared.event.EventPublisher;
import com.trymad.litechess_monolith.websocket.api.event.QueueRegistryEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SimpleMatchmakingQueueService implements MatchmakingQueueService {

	private Queue<UUID> queue = new LinkedBlockingQueue<>();
	private final EventPublisher eventPublisher;

	@Override
	public void add(QueueRegistryEvent event) {
		if(queue.contains(event.playerId())) {
			return;
		}
		
		if(queue.size() == 0) {
			queue.add(event.playerId());
			return;
		}

		final UUID oponent = queue.poll();
		final GameFindedEvent gameFindedEvent = new GameFindedEvent(List.of(oponent, event.playerId()), event.request());
		eventPublisher.publish(gameFindedEvent);
	}

	@Override
	public void leave(UUID playerId) {
		if(!queue.contains(playerId)) {
			return;
		}

		queue.remove(playerId);
	}

	@Async
	@EventListener
	void on(QueueLeaveEvent event) {
		System.out.println("leave");
		this.leave(event.playerId());
	}
	
}
