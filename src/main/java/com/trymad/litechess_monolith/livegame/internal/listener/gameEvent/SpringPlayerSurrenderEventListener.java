package com.trymad.litechess_monolith.livegame.internal.listener.gameEvent;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.livegame.internal.service.LiveGameService;
import com.trymad.litechess_monolith.shared.event.EventListener;
import com.trymad.litechess_monolith.websocket.api.event.PlayerSurrenderEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringPlayerSurrenderEventListener implements EventListener<PlayerSurrenderEvent>{

	private final LiveGameService liveGameService;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(PlayerSurrenderEvent event) {
		liveGameService.surrender(event.gameId(), event.playerId());
	}
}
