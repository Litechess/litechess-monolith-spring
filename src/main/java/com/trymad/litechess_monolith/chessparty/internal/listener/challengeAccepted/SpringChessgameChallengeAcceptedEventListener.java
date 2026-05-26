package com.trymad.litechess_monolith.chessparty.internal.listener.challengeAccepted;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.internal.service.ChessPartyService;
import com.trymad.litechess_monolith.infrastructure.event.EventListener;
import com.trymad.litechess_monolith.matchmaking.api.event.ChallengeAcceptedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringChessgameChallengeAcceptedEventListener implements EventListener<ChallengeAcceptedEvent> {

	private final ChessPartyService chessPartyService;


	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(ChallengeAcceptedEvent event) {
		chessPartyService.createGame(event);
	}

}
