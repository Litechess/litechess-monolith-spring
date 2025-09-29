package com.trymad.litechess_monolith.websocket.internal.listener.spring;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import com.trymad.litechess_monolith.matchmaking.api.event.QueueLeaveEvent;
import com.trymad.litechess_monolith.shared.event.EventPublisher;
import com.trymad.litechess_monolith.websocket.api.event.UserOfflineEvent;
import com.trymad.litechess_monolith.websocket.api.event.UserOnlineEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SessionEventsListener {
	
	private final EventPublisher publisher;

	@EventListener
	void onDisconnect(SessionDisconnectEvent springEvent) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(springEvent.getMessage());
		final UserOfflineEvent event = new UserOfflineEvent(UUID.fromString(accessor.getUser().getName()));
		publisher.publish(event);
	}

	@EventListener
	void onConnect(SessionConnectedEvent springEvent) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(springEvent.getMessage());
		final UserOnlineEvent event = new UserOnlineEvent(UUID.fromString(accessor.getUser().getName()));
		publisher.publish(event);
	}


	// TODO !!!!! destination check
	void onUnsubcribe(SessionUnsubscribeEvent springEvent) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(springEvent.getMessage());
		final QueueLeaveEvent queueLeaveEvent = new QueueLeaveEvent(accessor.getId());
		publisher.publish(queueLeaveEvent);
	}

}
