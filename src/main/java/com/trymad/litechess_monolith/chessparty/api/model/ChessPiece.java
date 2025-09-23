package com.trymad.litechess_monolith.chessparty.api.model;

import java.util.Map;

public enum ChessPiece {

    PAWN("", "p"),
    KNIGHT("N", "n"),
    BISHOP("B", "b"),
    ROOK("R", "r"),
    QUEEN("Q", "q"),
    KING("K", "k");

	private final String sanName;
	private final String letter;

	ChessPiece(String sanName, String letter) {
		this.sanName = sanName;
		this.letter = letter;
	}

	public String sanName() {
		return sanName;
	}

	public String letter() {
		return letter;
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
