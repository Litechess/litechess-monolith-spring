package com.trymad.litechess_monolith.websocket.internal;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.GameFinishEvent;
import com.trymad.litechess_monolith.websocket.ChessPartyCreatedEvent;
import com.trymad.litechess_monolith.websocket.GameCreatedDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

// TODO delete from queue when unsub /matchmaking/queue
public class WebSocketEventHandler {
	
	private final SimpMessagingTemplate messagingTemplate;

	@EventListener
	@Async
	public void on(ChessPartyCreatedEvent event) {
		final GameCreatedDTO gameCreatedDTO = new GameCreatedDTO(event.chessParty().getId());
		final Message<GameCreatedDTO> createdGame = MessageBuilder
			.withPayload(gameCreatedDTO)
			.setHeader("type", "gameCreated")
			.build();
		
		messagingTemplate.convertAndSendToUser(
			event.chessParty().getWhite().toString(), "/topic/matchmaking/queue", createdGame);
		messagingTemplate.convertAndSendToUser(
			event.chessParty().getBlack().toString(), "/topic/matchmaking/queue", createdGame);
	}

	@EventListener
	@Async
	void on(GameFinishEvent event) {
		final ChessParty chessParty = event.chessParty();
		final Message<GameResultMessage> gameResult = MessageBuilder
			.withPayload(new GameResultMessage(chessParty.getStatus()))
			.setHeader("type", "gameFinish")
			.build();

		messagingTemplate.convertAndSend("/topic/game/" + chessParty.getId(), gameResult);
	}
	
}
