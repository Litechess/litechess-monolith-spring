package com.trymad.litechess_monolith.websocket.internal;

import com.trymad.litechess_monolith.chessgame.ChessGameStatus;

public record GameResultMessage(ChessGameStatus status) {
	
}
