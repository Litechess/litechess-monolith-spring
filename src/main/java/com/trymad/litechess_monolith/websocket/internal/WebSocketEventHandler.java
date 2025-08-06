package com.trymad.litechess_monolith.websocket.internal;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketEventHandler {
	
	private final SimpMessagingTemplate messagingTemplate;

	@EventListener
	public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {

	}
	
}
