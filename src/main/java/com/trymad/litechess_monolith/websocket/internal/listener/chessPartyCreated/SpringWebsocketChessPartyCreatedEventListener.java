package com.trymad.litechess_monolith.websocket.internal.listener.chessPartyCreated;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.api.event.ChessPartyCreatedEvent;
import com.trymad.litechess_monolith.shared.event.EventListener;
import com.trymad.litechess_monolith.websocket.internal.GameMessageSender;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SpringWebsocketChessPartyCreatedEventListener implements EventListener<ChessPartyCreatedEvent> {

	private final GameMessageSender gameMessageSender;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(ChessPartyCreatedEvent event) {
		gameMessageSender.gameCreate(event);
	}
	
}
