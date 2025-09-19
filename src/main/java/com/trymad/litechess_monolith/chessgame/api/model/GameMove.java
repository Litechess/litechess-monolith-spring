package com.trymad.litechess_monolith.chessgame.api.model;

// san notation
public record GameMove(String from, String to, ChessPiece promotion, String san) {}
