package com.trymad.litechess_monolith.chessparty.api.dto;

import java.util.List;

import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerInfo;

public record ChessPartyDTO(
	Long id,
	PlayerInfo white,
	PlayerInfo black,
	List<GameMove> moves,
	List<Long> timerHistory,
	TimeControlDTO timeControl,
	String initFen,
	ChessGameStatus status
) {
	
}
