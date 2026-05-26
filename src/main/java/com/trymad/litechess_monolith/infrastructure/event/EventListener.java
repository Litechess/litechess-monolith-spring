package com.trymad.litechess_monolith.infrastructure.event;

public interface EventListener<T extends Event> {
	
	void handle(T event);

}
