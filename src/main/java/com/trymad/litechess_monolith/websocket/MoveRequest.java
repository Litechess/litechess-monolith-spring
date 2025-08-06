package com.trymad.litechess_monolith.websocket;

import com.trymad.litechess_monolith.chessgame.ChessPiece;

public record MoveRequest(
        String from,
        String to,
        ChessPiece promotion,
        String san
) {}