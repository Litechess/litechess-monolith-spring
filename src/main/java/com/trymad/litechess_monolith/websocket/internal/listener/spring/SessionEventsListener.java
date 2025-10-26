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
		final UserOfflineEvent event = new UserOfflineEvent(UUID.fromString(springEvent.getUser().getName()));
		publisher.publish(event);
	}

	@EventListener
	void onConnect(SessionConnectedEvent springEvent) {
		final UserOnlineEvent event = new UserOnlineEvent(UUID.fromString(springEvent.getUser().getName()));
		publisher.publish(event);
	}


	@EventListener
	void onUnsubcribe(SessionUnsubscribeEvent springEvent) {
		final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(springEvent.getMessage());
		final String destination = accessor.getDestination();
		if("/matchmaking/queue".equals(destination)) {
			leaveFromQueue(UUID.fromString(springEvent.getUser().getName()));
		}
	}

	private void leaveFromQueue(UUID userId) {
		final QueueLeaveEvent queueLeaveEvent = new QueueLeaveEvent(userId);
		publisher.publish(queueLeaveEvent);
	}

}
