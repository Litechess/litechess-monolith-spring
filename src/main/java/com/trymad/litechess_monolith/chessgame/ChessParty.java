package com.trymad.litechess_monolith.chessgame;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Document
public class ChessParty {
	
	@Id
	private Long id;

	private UUID white;

	private UUID black;

	private final List<GameMove> moveList = new LinkedList<>();

	private String initFen;

	private ChessGameStatus status;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ChessParty that = (ChessParty) o;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

}
