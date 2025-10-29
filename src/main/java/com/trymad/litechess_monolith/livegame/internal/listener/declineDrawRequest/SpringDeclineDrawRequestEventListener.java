package com.trymad.litechess_monolith.livegame.internal.listener.declineDrawRequest;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.livegame.internal.service.LiveGameService;
import com.trymad.litechess_monolith.shared.event.EventListener;
import com.trymad.litechess_monolith.websocket.api.event.DeclineDrawRequestEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SpringDeclineDrawRequestEventListener implements EventListener<DeclineDrawRequestEvent> {

	private final LiveGameService liveGameService;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(DeclineDrawRequestEvent event) {
		liveGameService.declineDraw(event.gameId(), event.playerId());
	}
	
}
