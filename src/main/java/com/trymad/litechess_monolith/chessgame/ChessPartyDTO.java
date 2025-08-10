package com.trymad.litechess_monolith.chessgame;

import java.util.UUID;

public record ChessPartyDTO(
	Long id,
	UUID white,
	UUID black,
	String[] moveUci,
	String fen,
	String initFen,
	ChessGameStatus status
) {
	
}
