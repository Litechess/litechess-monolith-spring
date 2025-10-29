package com.trymad.litechess_monolith.websocket.api.dto;

import com.trymad.litechess_monolith.websocket.api.model.GameEventType;

public record GameEventRequest(GameEventType event) {
	
}
