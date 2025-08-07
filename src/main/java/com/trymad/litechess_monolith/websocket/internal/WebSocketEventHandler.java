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

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketEventHandler {
	
	private final SimpMessagingTemplate messagingTemplate;
	private final ChessPartyService chessPartyService;

	@EventListener
	@Async
	public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
		System.out.println("HANDLE SUB");
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
	
}
