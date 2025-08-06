package com.trymad.litechess_monolith.chessgame;

import java.util.UUID;

public record GameInfoDTO(
	UUID white,
	UUID black,
	String initFen,
	String fen,
	ChessGameStatus status,
	String moveSan
) {
	
}
