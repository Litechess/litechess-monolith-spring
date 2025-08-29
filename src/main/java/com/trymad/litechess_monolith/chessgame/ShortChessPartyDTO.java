package com.trymad.litechess_monolith.chessgame;

public record ShortChessPartyDTO(
	Long id,
	PlayerInfo white,
	PlayerInfo black,
	ChessGameStatus status) {
	
}
