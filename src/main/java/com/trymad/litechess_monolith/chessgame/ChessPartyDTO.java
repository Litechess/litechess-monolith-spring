package com.trymad.litechess_monolith.chessgame;

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
