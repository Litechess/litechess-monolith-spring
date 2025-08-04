package com.trymad.litechess_monolith.chessgame.internal.event;

import com.trymad.litechess_monolith.websocket.MoveEvent;

public interface MoveHandler {
	
	void on(MoveEvent moveEvent);

}
