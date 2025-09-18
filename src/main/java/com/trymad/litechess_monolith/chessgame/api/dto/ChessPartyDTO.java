package com.trymad.litechess_monolith.chessgame.api.dto;

import com.trymad.litechess_monolith.chessgame.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.PlayerInfo;

public record ChessPartyDTO(
	Long id,
	PlayerInfo white,
	PlayerInfo black,
	String[] moveUci,
	String fen,
	String initFen,
	ChessGameStatus status
) {
	
}
