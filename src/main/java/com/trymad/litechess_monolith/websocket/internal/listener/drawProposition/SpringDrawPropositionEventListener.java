package com.trymad.litechess_monolith.websocket.internal.listener.drawProposition;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.livegame.api.event.DrawPropositionEvent;
import com.trymad.litechess_monolith.shared.event.EventListener;
import com.trymad.litechess_monolith.websocket.internal.service.GameMessageSender;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringDrawPropositionEventListener implements EventListener<DrawPropositionEvent> {

	private final GameMessageSender sender;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(DrawPropositionEvent event) {
		sender.drawProposition(event);
	}
	
}
