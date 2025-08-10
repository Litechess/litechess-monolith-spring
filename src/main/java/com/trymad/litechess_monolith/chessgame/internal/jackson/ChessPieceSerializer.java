package com.trymad.litechess_monolith.chessgame.internal.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.trymad.litechess_monolith.chessgame.ChessPiece;

public class ChessPieceSerializer extends JsonSerializer<ChessPiece> {

	@Override
	public void serialize(ChessPiece value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value == null ? null : value.letter());
	}
	
}
