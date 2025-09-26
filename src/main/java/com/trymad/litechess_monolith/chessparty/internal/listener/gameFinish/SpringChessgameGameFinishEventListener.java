package com.trymad.litechess_monolith.chessparty.internal.listener.gameFinish;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.internal.service.ChessPartyService;
import com.trymad.litechess_monolith.livegame.api.event.GameFinishEvent;
import com.trymad.litechess_monolith.shared.event.EventListener;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SpringChessgameGameFinishEventListener implements EventListener<GameFinishEvent> {

	private final ChessPartyService chessPartyService;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(GameFinishEvent event) {
		chessPartyService.update(event);
	}
	
}
