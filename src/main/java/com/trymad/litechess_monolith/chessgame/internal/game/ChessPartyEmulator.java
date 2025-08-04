package com.trymad.litechess_monolith.chessgame.internal.game;

import java.util.List;

import com.trymad.litechess_monolith.chessgame.GameMove;
import com.trymad.litechess_monolith.chessgame.internal.model.PlayerColor;

public interface ChessPartyEmulator {
	
	boolean isLegalMove(GameMove move);

	void move(GameMove move);

	void setPosition(List<GameMove> moveList);

	PlayerColor getCurrentTurnColor();

	List<GameMove> getMoves();

}
