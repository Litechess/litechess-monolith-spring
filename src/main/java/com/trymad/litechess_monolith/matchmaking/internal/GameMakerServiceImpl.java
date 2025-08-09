package com.trymad.litechess_monolith.matchmaking.internal;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.matchmaking.GameMakerService;
import com.trymad.litechess_monolith.websocket.CreateGameRequest;
import com.trymad.litechess_monolith.websocket.QueueRegistryEvent;

import lombok.RequiredArgsConstructor;

@Component("gameMakerService")
@RequiredArgsConstructor
public class GameMakerServiceImpl implements GameMakerService {
	
	private final MatchmakingQueueService queueService;

	@EventListener
	@Async
	void on(QueueRegistryEvent event) {
		addInQueue(event.request(), event.playerId());
	}	

	@Override
	public void addInQueue(CreateGameRequest gameRequest, UUID playerId) {
		queueService.add(gameRequest, playerId);
	}
	
}
