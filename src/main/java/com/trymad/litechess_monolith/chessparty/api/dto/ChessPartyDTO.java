package com.trymad.litechess_monolith.chessparty.api.dto;

import java.time.Duration;
import java.util.List;

import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerInfo;
import com.trymad.litechess_monolith.chessparty.api.model.TimeControl;

public record ChessPartyDTO(
	Long id,
	PlayerInfo white,
	PlayerInfo black,
	List<GameMove> moves,
	List<Duration> timerHistory,
	TimeControl timeControl,
	String initFen,
	String currentFen,
	ChessGameStatus status
) {
	
}
