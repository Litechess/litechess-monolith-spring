package com.trymad.litechess_monolith.chessgame.internal.listener.move;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.internal.service.ChessPartyService;
import com.trymad.litechess_monolith.shared.event.EventListener;
import com.trymad.litechess_monolith.websocket.api.event.MoveEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SpringChessgameMoveEventListener implements EventListener<MoveEvent> {

	private final ChessPartyService chessPartyService;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(MoveEvent event) {
		chessPartyService.doMove(event);
	}
	
}
