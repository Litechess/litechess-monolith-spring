package com.trymad.litechess_monolith.websocket.api.dto;

import java.util.Map;

import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;

public record MoveResponse(GameMove move, Map<PlayerColor, Long> timers) {
	
}
