package com.trymad.litechess_monolith.chessparty.api.model;

public enum PlayerColor {
	BLACK, WHITE;

    public PlayerColor flip() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
