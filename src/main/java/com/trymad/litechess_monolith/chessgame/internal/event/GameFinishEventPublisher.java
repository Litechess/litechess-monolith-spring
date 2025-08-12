package com.trymad.litechess_monolith.chessgame.internal.event;

import com.trymad.litechess_monolith.chessgame.GameFinishEvent;

public interface GameFinishEventPublisher {
	
	void publish(GameFinishEvent event);

}
