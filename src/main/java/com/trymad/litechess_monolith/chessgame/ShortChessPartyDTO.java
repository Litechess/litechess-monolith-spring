package com.trymad.litechess_monolith.chessgame;

import java.util.UUID;

public record ShortChessPartyDTO(
	Long id,
	UUID white,
	UUID black,
	ChessGameStatus status) {
	
}
