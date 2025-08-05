package com.trymad.litechess_monolith.chessgame;

import java.util.Map;

public enum ChessPiece {
    PAWN,
    KNIGHT,
    BISHOP,
    ROOK,
    QUEEN,
    KING;

	public static final Map<String, ChessPiece> fromLetter = Map.of(
		"q", QUEEN,
		"b", BISHOP,
		"r", ROOK,
		"k", KING,
		"p", PAWN,
		"n", KNIGHT
	);
}
