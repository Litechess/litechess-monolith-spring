package com.trymad.litechess_monolith.websocket.api.dto;

import com.trymad.litechess_monolith.chessparty.api.model.GameCategory;
import com.trymad.litechess_monolith.chessparty.api.model.GameVariant;
import com.trymad.litechess_monolith.chessparty.api.model.TimeControlVariant;

public record CreateGameRequest(
	GameVariant variant, 
	TimeControlVariant timeControl,
	GameCategory category,
	int secondsPerSide,
	int increment) {}
