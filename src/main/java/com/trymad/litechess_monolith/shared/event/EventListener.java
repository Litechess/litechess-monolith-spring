package com.trymad.litechess_monolith.shared.event;

public interface EventListener<T extends Event> {
	
	void handle(T event);

}
