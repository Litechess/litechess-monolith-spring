package com.trymad.litechess_monolith.chessgame.internal.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.ChessPartyService;
import com.trymad.litechess_monolith.chessgame.GameFinishEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringGameFinishEventHandler implements GameFinishEventHandler {
	
	private final ChessPartyService chessPartyService;

	@EventListener
	@Async
	@Override
	public void handle(GameFinishEvent event) {
		chessPartyService.save(event.chessParty());
		chessPartyService.stopActiveGame(event.chessParty().getId());
	}

}
