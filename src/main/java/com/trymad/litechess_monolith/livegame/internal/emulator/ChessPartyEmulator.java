package com.trymad.litechess_monolith.livegame.internal.emulator;

import java.util.List;

import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;

public interface ChessPartyEmulator {
	
	boolean isLegalMove(GameMove move);

	GameMove move(GameMove move);

	void setPosition(List<GameMove> moveList);

	PlayerColor getCurrentTurnColor();

	String getSan();

	String getFen();

	List<GameMove> getMoveList();

	ChessGameStatus gameStatus();

}
