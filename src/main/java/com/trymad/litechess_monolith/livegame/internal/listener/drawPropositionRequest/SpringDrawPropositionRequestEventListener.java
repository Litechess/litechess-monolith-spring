package com.trymad.litechess_monolith.livegame.internal.listener.drawPropositionRequest;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.livegame.internal.service.LiveGameService;
import com.trymad.litechess_monolith.shared.event.EventListener;
import com.trymad.litechess_monolith.websocket.api.event.DrawPropositionRequestEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringDrawPropositionRequestEventListener implements EventListener<DrawPropositionRequestEvent>{

	private final LiveGameService liveGameService;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(DrawPropositionRequestEvent event) {
		liveGameService.drawProposition(event.gameId(), event.playerId());
	}
	
}
