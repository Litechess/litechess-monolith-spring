package com.trymad.litechess_monolith.websocket.api.dto;

import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.chessparty.api.model.GameCategory;

public record CreateGameRequest(
	TimeControlDTO timeControl,
	GameCategory category) {}
