package com.trymad.litechess_monolith.chessparty.api.dto;

import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerInfo;

public record ShortChessPartyDTO(
	Long id,
	PlayerInfo white,
	PlayerInfo black,
	ChessGameStatus status) {
	
}
