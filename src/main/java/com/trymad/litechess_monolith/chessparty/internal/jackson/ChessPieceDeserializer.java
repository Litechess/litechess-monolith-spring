package com.trymad.litechess_monolith.chessparty.internal.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.trymad.litechess_monolith.chessparty.api.model.ChessPiece;

import java.io.IOException;

public class ChessPieceDeserializer extends JsonDeserializer<ChessPiece> {
    @Override
    public ChessPiece deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String letter = p.getText();
        return ChessPiece.fromLetter.get(letter);
    }
}