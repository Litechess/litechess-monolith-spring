package com.trymad.litechess_monolith.websocket.internal.listener.declineDraw;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.livegame.api.event.DeclineDrawEvent;
import com.trymad.litechess_monolith.shared.event.EventListener;
import com.trymad.litechess_monolith.websocket.internal.service.GameMessageSender;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringDeclineDrawEventListener implements EventListener<DeclineDrawEvent> {
	
	private final GameMessageSender sender;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(DeclineDrawEvent event) {
		sender.drawDecline(event);
	}
	
}
