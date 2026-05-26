package com.trymad.litechess_monolith.infrastructure.event;

public interface EventPublisher {

	void publish(Event event);

}
