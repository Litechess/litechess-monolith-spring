package com.trymad.litechess_monolith.chessparty.api.model;

// san notation
public record GameMove(String from, String to, ChessPiece promotion, String san) {}
