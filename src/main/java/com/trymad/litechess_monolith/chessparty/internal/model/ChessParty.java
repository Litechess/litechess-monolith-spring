package com.trymad.litechess_monolith.chessparty.internal.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class ChessParty {

@PersistenceCreator
public ChessParty(Long id,
                  PlayerInfo white,
                  PlayerInfo black,
                  List<GameMove> moves,
                  List<Long> timerHistory,
                  TimeControl timeControl,
                  String initFen,
                  ChessGameStatus status) {
    this.id = id;
    this.white = white;
    this.black = black;
    if(moves != null) {
		this.moves.addAll(moves);
	}

	if(timerHistory != null) {
		this.timerHistory.addAll(timerHistory);
	}

    this.timeControl = timeControl;
    this.initFen = initFen;
    this.status = status;
}

	@Id
	private Long id;

	private PlayerInfo white;

	private PlayerInfo black;

	private final List<GameMove> moves = new LinkedList<>();

	private final List<Long> timerHistory = new ArrayList<>();

	private TimeControl timeControl;

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
