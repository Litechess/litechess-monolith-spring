package com.trymad.litechess_monolith.websocket.internal;

import java.security.Principal;
import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

	public void publishMoveRequest(MoveRequest moveRequest, Long gameId, Principal principal) {
		final MoveEvent moveEvent = new MoveEvent(moveRequest, gameId, UUID.fromString(principal.getName()));
		moveSender.publish(moveEvent);
	}

	@EventListener
	@Async
	void on(MoveAcceptedEvent event) {
		messagingTemplate.convertAndSend("/topic/game/" + event.gameId(),event. moveRequest());
	}

}
