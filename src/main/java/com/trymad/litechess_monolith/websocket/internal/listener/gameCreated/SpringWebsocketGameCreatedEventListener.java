package com.trymad.litechess_monolith.websocket.internal.listener.gameCreated;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.api.event.GameCreatedEvent;
import com.trymad.litechess_monolith.shared.event.EventListener;
import com.trymad.litechess_monolith.websocket.internal.service.GameMessageSender;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SpringWebsocketGameCreatedEventListener implements EventListener<GameCreatedEvent> {

	private final GameMessageSender gameMessageSender;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(GameCreatedEvent event) {
		System.out.println("game created send");
		gameMessageSender.gameCreate(event);
	}
	
}
