package com.trymad.litechess_monolith.matchmaking.api.dto;

import java.util.UUID;

import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;

public record CreateChallengeDTO(
	UUID initiator,
	UUID opponent,
	PlayerColor initiatorSide,
	TimeControlDTO timeControl
) {}
