package com.trymad.litechess_monolith.event.queueLeave.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.event.queueLeave.QueueLeaveEvent;
import com.trymad.litechess_monolith.event.queueLeave.QueueLeaveEventPublisher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringQueueLeaveEventPublisher implements QueueLeaveEventPublisher {

	private final ApplicationEventPublisher events;

	@Override
	public void publish(QueueLeaveEvent event) {
		events.publishEvent(event);
	}
	
}
