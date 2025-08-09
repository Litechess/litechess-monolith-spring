package com.trymad.litechess_monolith.matchmaking.internal;

import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.websocket.CreateGameRequest;
import com.trymad.litechess_monolith.websocket.GameFindedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SimpleMatchmakingQueueService implements MatchmakingQueueService {

	private Queue<UUID> queue = new LinkedBlockingQueue<>();
	private final GameFindedEventPublisher gameFindedEventPublisher;

	@Override
	public void add(CreateGameRequest createGameRequest, UUID playerId) {
		if(queue.contains(playerId)) {
			System.out.println("ALREADY IN QUEUE");
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
	
}
