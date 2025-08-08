package com.trymad.litechess_monolith.websocket;

import com.trymad.litechess_monolith.chessgame.GameCategory;
import com.trymad.litechess_monolith.chessgame.GameVariant;
import com.trymad.litechess_monolith.chessgame.TimeControlVariant;

public record CreateGameRequest(
	GameVariant variant, 
	TimeControlVariant timeControl,
	GameCategory category,
	int secondsPerSide,
	int increment) {}
