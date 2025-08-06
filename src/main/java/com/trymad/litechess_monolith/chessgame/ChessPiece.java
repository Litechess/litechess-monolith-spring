package com.trymad.litechess_monolith.chessgame;

import java.util.Map;

public enum ChessPiece {

    PAWN(""),
    KNIGHT("N"),
    BISHOP("B"),
    ROOK("R"),
    QUEEN("Q"),
    KING("K");

	private final String sanName;

	ChessPiece(String sanName) {
		this.sanName = sanName;
	}

	public String sanName() {
		return sanName;
	}

	public static final Map<String, ChessPiece> fromLetter = Map.of(
		"q", QUEEN,
		"b", BISHOP,
		"r", ROOK,
		"k", KING,
		"p", PAWN,
		"n", KNIGHT,
		"", PAWN
	);
}
