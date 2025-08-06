package com.trymad.litechess_monolith.chessgame.internal.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.internal.game.ChessPartyServiceImpl;
import com.trymad.litechess_monolith.websocket.MoveAcceptedEvent;
import com.trymad.litechess_monolith.websocket.MoveEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringMoveHandler implements MoveHandler {

	private final MoveAcceptedEventPublisher events;
	private final ChessPartyServiceImpl chessPartyService;

	@Override
	@EventListener
	@Async
	public void on(MoveEvent event) {
		final boolean isAccepted = chessPartyService.doMove(event);
		if(isAccepted) {
			final MoveAcceptedEvent acceptedMoveEvent = 
				new MoveAcceptedEvent(event.moveRequest(), event.gameId(), event.playerId());
			events.publish(acceptedMoveEvent);
		}
	}

}
