package com.trymad.litechess_monolith.websocket.internal;

import java.security.Principal;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.trymad.litechess_monolith.websocket.MoveAcceptedEvent;
import com.trymad.litechess_monolith.websocket.MoveEvent;
import com.trymad.litechess_monolith.websocket.MoveRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoveService {
	
	private final MoveEventPublisher moveSender;
	private final SimpMessagingTemplate messagingTemplate;
	private final Logger logger = Logger.getLogger("moveService");

	public void publishMoveRequest(MoveRequest moveRequest, Long gameId, Principal principal) {
		final MoveEvent moveEvent = new MoveEvent(moveRequest, gameId, UUID.fromString(principal.getName()));
		logger.info(moveRequest.toString());
		moveSender.publish(moveEvent);
	}

	@EventListener
	@Async
	void on(MoveAcceptedEvent event) {
		final Message<MoveRequest> message = MessageBuilder
			.withPayload(event.moveRequest())
			.setHeader("type", "move")
			.build();
		messagingTemplate.convertAndSend("/topic/game/" + event.gameId(), message);
	}

}
