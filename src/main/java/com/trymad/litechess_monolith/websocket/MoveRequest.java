package com.trymad.litechess_monolith.websocket;

import com.trymad.litechess_monolith.chessgame.ChessPiece;

public record MoveRequest(
        String color,
        String from,
        String to,
        ChessPiece piece,
        ChessPiece captured,
        ChessPiece promotion,
        String flags,
        String san,
        String lan,
        String before,
        String after
) {}