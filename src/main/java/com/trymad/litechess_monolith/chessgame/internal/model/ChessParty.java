package com.trymad.litechess_monolith.chessgame.internal.model;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trymad.litechess_monolith.chessgame.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.api.model.GameMove;
import com.trymad.litechess_monolith.chessgame.api.model.PlayerInfo;

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

	@PersistenceCreator
	public ChessParty(Long id, PlayerInfo white, PlayerInfo black, List<GameMove> moveList, String initFen, ChessGameStatus status) {
		this.id = id;
		this.white = white;
		this.black = black;
		this.moveList.addAll(moveList != null ? moveList : List.of());
		this.initFen = initFen;
		this.status = status;
	}

	@Id
	private Long id;

	private PlayerInfo white;

	private PlayerInfo black;

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
