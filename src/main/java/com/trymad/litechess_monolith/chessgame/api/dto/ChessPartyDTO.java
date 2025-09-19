package com.trymad.litechess_monolith.chessgame.api.dto;

import java.util.List;

import com.trymad.litechess_monolith.chessgame.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.api.model.GameMove;
import com.trymad.litechess_monolith.chessgame.api.model.PlayerInfo;

public record ChessPartyDTO(
	Long id,
	PlayerInfo white,
	PlayerInfo black,
	List<GameMove> moves,
	String fen,
	String initFen,
	ChessGameStatus status
) {
	
}
