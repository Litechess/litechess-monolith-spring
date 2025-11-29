package com.trymad.litechess_monolith.userOnline.internal.listener.userLeaveOnline;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.shared.event.EventListener;
import com.trymad.litechess_monolith.userOnline.internal.service.OnlineUserService;
import com.trymad.litechess_monolith.websocket.api.event.UserOfflineEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringUserOnlineUserOfflineEventListener implements EventListener<UserOfflineEvent> {

	private final OnlineUserService onlineUserService;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(UserOfflineEvent event) {
		onlineUserService.removeOnline(event.userId());
	}
	
}
