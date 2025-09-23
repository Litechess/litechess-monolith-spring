package com.trymad.litechess_monolith.livegame.internal.listener.gameCreated;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.api.event.GameCreatedEvent;
import com.trymad.litechess_monolith.livegame.internal.service.LiveGameService;
import com.trymad.litechess_monolith.shared.event.EventListener;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringLivegameGameCreatedEventListener implements EventListener<GameCreatedEvent> {

	private final LiveGameService liveGameService;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(GameCreatedEvent event) {
		liveGameService.create(event.chessParty());
	}
	
}
