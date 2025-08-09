package com.trymad.litechess_monolith.websocket.internal;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.ChessPartyDTO;
import com.trymad.litechess_monolith.chessgame.ChessPartyService;
import com.trymad.litechess_monolith.websocket.ChessPartyCreatedEvent;
import com.trymad.litechess_monolith.websocket.GameCreatedDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

// TODO delete from queue when unsub /matchmaking/queue
public class WebSocketEventHandler {
	
	private final SimpMessagingTemplate messagingTemplate;
	private final ChessPartyService chessPartyService;

	@EventListener
	@Async
	public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
		String destination = event.getMessage().getHeaders().get("simpDestination", String.class);
		if (destination != null && destination.startsWith("/topic/game/")) {
			Long gameId = Long.parseLong(destination.substring("/topic/game/".length()));
			final ChessParty party = chessPartyService.get(gameId);
			final ChessPartyDTO gameInfoDTO = chessPartyService.getDto(party);

			final Message<ChessPartyDTO> message = MessageBuilder
				.withPayload(gameInfoDTO)
				.setHeader("type", "gameInfo")
				.build();
			messagingTemplate.convertAndSend(destination, message);
		}
	}

	@EventListener
	@Async
	public void on(ChessPartyCreatedEvent event) {
		final GameCreatedDTO gameCreatedDTO = new GameCreatedDTO(event.chessParty().getId());
		final Message<GameCreatedDTO> createdGame = MessageBuilder
			.withPayload(gameCreatedDTO)
			.setHeader("type", "gameFinded")
			.build();
		
		messagingTemplate.convertAndSendToUser(
			event.chessParty().getWhite().toString(), "/topic/matchmaking/queue", createdGame);
		messagingTemplate.convertAndSendToUser(
			event.chessParty().getBlack().toString(), "/topic/matchmaking/queue", createdGame);
	}
	
}
