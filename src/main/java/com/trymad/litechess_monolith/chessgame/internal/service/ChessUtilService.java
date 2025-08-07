package com.trymad.litechess_monolith.chessgame.internal.service;

import java.util.List;

import com.trymad.litechess_monolith.chessgame.GameMove;

public interface ChessUtilService {
	
	String getSan(String fen, List<GameMove> moves);

	String getFen(String fen, List<GameMove> moves);

}
