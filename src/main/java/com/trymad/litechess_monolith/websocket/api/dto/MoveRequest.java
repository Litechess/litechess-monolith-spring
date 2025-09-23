package com.trymad.litechess_monolith.websocket.api.dto;

import com.trymad.litechess_monolith.chessparty.api.model.ChessPiece;

public record MoveRequest(
        String from,
        String to,
        ChessPiece promotion,
        String san
) {}