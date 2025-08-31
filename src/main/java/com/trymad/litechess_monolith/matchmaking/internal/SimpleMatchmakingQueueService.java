package com.trymad.litechess_monolith.matchmaking.internal;

import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.event.queueLeave.QueueLeaveEvent;
import com.trymad.litechess_monolith.event.queueLeave.QueueLeaveEventPublisher;
import com.trymad.litechess_monolith.websocket.CreateGameRequest;
import com.trymad.litechess_monolith.websocket.GameFindedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SimpleMatchmakingQueueService implements MatchmakingQueueService {

	private Queue<UUID> queue = new LinkedBlockingQueue<>();
	private final GameFindedEventPublisher gameFindedEventPublisher;
	private final QueueLeaveEventPublisher publisher;

	@Override
	public void add(CreateGameRequest createGameRequest, UUID playerId) {
		if(queue.contains(playerId)) {
			return;
		}
		
		if(queue.size() == 0) {
			queue.add(playerId);
			return;
		}

		final UUID oponent = queue.poll();
		final GameFindedEvent event = new GameFindedEvent(List.of(oponent, playerId), createGameRequest);
		gameFindedEventPublisher.publish(event);
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
