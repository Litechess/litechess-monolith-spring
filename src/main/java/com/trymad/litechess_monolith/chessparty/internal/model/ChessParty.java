package com.trymad.litechess_monolith.chessparty.internal.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerInfo;
import com.trymad.litechess_monolith.chessparty.api.model.TimeControl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Document
public class ChessParty {

	@Id
	private Long id;

	private PlayerInfo white;

	private PlayerInfo black;

	private List<GameMove> moves = new LinkedList<>();

	private List<Duration> timerHistory = new ArrayList<>();

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
