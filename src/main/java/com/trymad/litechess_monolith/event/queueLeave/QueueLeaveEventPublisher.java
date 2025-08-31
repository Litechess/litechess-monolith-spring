package com.trymad.litechess_monolith.event.queueLeave;

public interface QueueLeaveEventPublisher {
	
	void publish(QueueLeaveEvent event);

}
