package com.trymad.litechess_monolith.chessgame.internal.game.emulator;

import java.util.List;

import com.trymad.litechess_monolith.chessgame.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.GameMove;
import com.trymad.litechess_monolith.chessgame.internal.model.PlayerColor;

public interface ChessPartyEmulator {
	
	boolean isLegalMove(GameMove move);

	void move(GameMove move);

	void setPosition(List<GameMove> moveList);

	PlayerColor getCurrentTurnColor();

	String getSan();

	String getFen();

	List<GameMove> getMoveList();

	ChessGameStatus gameStatus();

}
