package com.trymad.litechess_monolith.matchmaking.internal.listener.gameCreated;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.api.event.GameCreatedEvent;
import com.trymad.litechess_monolith.chessparty.api.event.GameSource;
import com.trymad.litechess_monolith.infrastructure.event.EventListener;
import com.trymad.litechess_monolith.matchmaking.internal.service.ChallengeService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringMatchmakingGameCreatedEventListener implements EventListener<GameCreatedEvent> {
	
	private final ChallengeService challengeService;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(GameCreatedEvent event) {
		if(event.source().equals(GameSource.CHALLENGE)) {
			challengeService.confirmCreateChallenge(event.chessParty().id());
		}
	}
	
}
