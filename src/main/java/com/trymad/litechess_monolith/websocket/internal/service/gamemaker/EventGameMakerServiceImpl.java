package com.trymad.litechess_monolith.websocket.internal.service.gamemaker;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.matchmaking.GameMakerService;
import com.trymad.litechess_monolith.websocket.CreateGameRequest;
import com.trymad.litechess_monolith.websocket.QueueRegistryEvent;

import lombok.RequiredArgsConstructor;

@Component("eventGameMakerService")
@RequiredArgsConstructor
public class EventGameMakerServiceImpl implements GameMakerService {

	private final QueueRegistryPublisher sender;

	@Override
	public void addInQueue(CreateGameRequest gameRequest, UUID playerId) {
		final QueueRegistryEvent event = new QueueRegistryEvent(gameRequest, playerId);
		sender.publish(event);
	}
	
}
