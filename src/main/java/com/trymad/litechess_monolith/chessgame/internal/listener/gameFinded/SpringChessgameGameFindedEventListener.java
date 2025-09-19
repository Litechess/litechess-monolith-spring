package com.trymad.litechess_monolith.chessgame.internal.listener.gameFinded;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.internal.service.ChessPartyService;
import com.trymad.litechess_monolith.matchmaking.api.event.GameFindedEvent;
import com.trymad.litechess_monolith.shared.event.EventListener;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SpringChessgameGameFindedEventListener implements EventListener<GameFindedEvent> {

	private final ChessPartyService chessPartyService;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(GameFindedEvent event) {
		chessPartyService.createGame(event);
	}
	
}
