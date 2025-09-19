package com.trymad.litechess_monolith.chessgame.internal.service;

import java.util.List;

import com.trymad.litechess_monolith.chessgame.api.model.GameMove;
import com.trymad.litechess_monolith.websocket.api.dto.MoveRequest;

public interface ChessUtilService {
	
	String getSan(String fen, List<GameMove> moves);

	String getFen(String fen, List<GameMove> moves);

	GameMove toGameMove(MoveRequest request);

}
