package com.trymad.litechess_monolith.websocket.internal;

import com.trymad.litechess_monolith.chessgame.api.model.ChessGameStatus;

public record GameResultMessage(ChessGameStatus status) {
	
}
