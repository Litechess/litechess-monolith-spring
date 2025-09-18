package com.trymad.litechess_monolith.chessgame.api.dto;

import com.trymad.litechess_monolith.chessgame.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.PlayerInfo;

public record ShortChessPartyDTO(
	Long id,
	PlayerInfo white,
	PlayerInfo black,
	ChessGameStatus status) {
	
}
