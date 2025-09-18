package com.trymad.litechess_monolith.websocket.internal.listener.moveAccepted;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.api.event.MoveAcceptedEvent;
import com.trymad.litechess_monolith.shared.event.EventListener;
import com.trymad.litechess_monolith.websocket.internal.GameMessageSender;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SpringWebsocketMoveAcceptedEventListener implements EventListener<MoveAcceptedEvent> {

	private final GameMessageSender gameMessageSender;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(MoveAcceptedEvent event) {
		gameMessageSender.move(event);
	}
	
}
