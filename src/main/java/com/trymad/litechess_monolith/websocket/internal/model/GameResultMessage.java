package com.trymad.litechess_monolith.websocket.internal.model;

import com.trymad.litechess_monolith.chessgame.api.model.ChessGameStatus;

public record GameResultMessage(ChessGameStatus status) {
	
}
