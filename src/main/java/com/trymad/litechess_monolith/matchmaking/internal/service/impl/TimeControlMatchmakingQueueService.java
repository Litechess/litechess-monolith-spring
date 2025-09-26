package com.trymad.litechess_monolith.matchmaking.internal.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.matchmaking.api.event.GameFindedEvent;
import com.trymad.litechess_monolith.matchmaking.internal.service.MatchmakingQueueService;
import com.trymad.litechess_monolith.shared.event.EventPublisher;
import com.trymad.litechess_monolith.websocket.api.event.QueueRegistryEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
@Primary
public class TimeControlMatchmakingQueueService implements MatchmakingQueueService {

	private final Map<TimeControlDTO, LinkedBlockingDeque<UUID>> queuePull = new ConcurrentHashMap<>();
	private final Map<UUID, TimeControlDTO> playerTimeControl = new ConcurrentHashMap<>();
	private final EventPublisher eventPublisher;


	@Override
	public void add(QueueRegistryEvent event) {
		if(playerTimeControl.containsKey(event.playerId())) {
			return;
		}

		final TimeControlDTO timeControl = event.request().timeControl();
		playerTimeControl.put(event.playerId(), timeControl);

		if(!queuePull.containsKey(timeControl)) {
			queuePull.put(timeControl, new LinkedBlockingDeque<>());
		}

		final LinkedBlockingDeque<UUID> queue = queuePull.get(timeControl);
		if(queue.size() == 0) {
			queue.add(event.playerId());
			return;
		}

		final UUID oponent = queue.poll();
		playerTimeControl.remove(event.playerId());
		playerTimeControl.remove(oponent);
		
		final GameFindedEvent gameFindedEvent = new GameFindedEvent(
			List.of(oponent, event.playerId()), event.request());
		
		eventPublisher.publish(gameFindedEvent);
	}

	@Override
	public void leave(UUID playerId) {
		if(!playerTimeControl.containsKey(playerId)) {
			return;
		}

		final TimeControlDTO timeControl = playerTimeControl.get(playerId);
		if(!queuePull.containsKey(timeControl)) {
			return;
		}

		final LinkedBlockingDeque<UUID> queue = queuePull.get(timeControl);
		if(!queue.contains(playerId)) {
			return;
		}

		queue.remove(playerId);
		playerTimeControl.remove(playerId);
	}
}
