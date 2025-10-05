package com.trymad.litechess_monolith.livegame.api.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;

public record LiveGameDTO(
	Long id, 
	List<GameMove> moves, 
	Map<PlayerColor, UUID> 
	playerSides, 
	List<Long> timerHistory,
	Map<PlayerColor, Long> currentTimers) {
}
