package com.trymad.litechess_monolith.chessgame.internal.event;

import com.trymad.litechess_monolith.chessgame.GameFinishEvent;

public interface GameFinishEventHandler {
	
	void handle(GameFinishEvent event);

}
