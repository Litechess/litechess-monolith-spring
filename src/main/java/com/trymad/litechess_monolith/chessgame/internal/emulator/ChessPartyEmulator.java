package com.trymad.litechess_monolith.chessgame.internal.emulator;

import java.util.List;

import com.trymad.litechess_monolith.chessgame.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.api.model.GameMove;
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
