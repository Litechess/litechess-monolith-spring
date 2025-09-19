package com.trymad.litechess_monolith.websocket;

import com.trymad.litechess_monolith.chessgame.api.model.GameCategory;
import com.trymad.litechess_monolith.chessgame.api.model.GameVariant;
import com.trymad.litechess_monolith.chessgame.api.model.TimeControlVariant;

public record CreateGameRequest(
	GameVariant variant, 
	TimeControlVariant timeControl,
	GameCategory category,
	int secondsPerSide,
	int increment) {}
