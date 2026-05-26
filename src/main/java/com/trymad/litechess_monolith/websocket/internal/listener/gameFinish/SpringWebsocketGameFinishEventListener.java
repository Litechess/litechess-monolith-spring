package com.trymad.litechess_monolith.websocket.internal.listener.gameFinish;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.infrastructure.event.EventListener;
import com.trymad.litechess_monolith.livegame.api.event.GameFinishEvent;
import com.trymad.litechess_monolith.websocket.internal.service.GameMessageSender;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SpringWebsocketGameFinishEventListener implements EventListener<GameFinishEvent> {

	private final GameMessageSender gameMessageSender;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(GameFinishEvent event) {
		gameMessageSender.gameFinish(event);
	}
	
}
