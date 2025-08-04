package com.trymad.litechess_monolith.chessgame.internal.model;

public enum PlayerColor {
	BLACK, WHITE;

    public PlayerColor flip() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
