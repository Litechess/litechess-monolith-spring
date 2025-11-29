package com.trymad.litechess_monolith.userOnline.internal.listener.userEnterOnline;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.shared.event.EventListener;
import com.trymad.litechess_monolith.userOnline.internal.service.OnlineUserService;
import com.trymad.litechess_monolith.websocket.api.event.UserOnlineEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringUserOnlineUserOnlineListener implements EventListener<UserOnlineEvent> {

	private final OnlineUserService onlineUserService;

	@Override
	@org.springframework.context.event.EventListener
	@Async
	public void handle(UserOnlineEvent event) {
		onlineUserService.addOnline(event.id());
	}
	
}
