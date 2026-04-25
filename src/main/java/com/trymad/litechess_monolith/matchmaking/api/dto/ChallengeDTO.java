package com.trymad.litechess_monolith.matchmaking.api.dto;

import java.util.UUID;

import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;
import com.trymad.litechess_monolith.matchmaking.api.model.ChallengeStatus;

public record ChallengeDTO(
	String id,
	
	UUID initiator,

	ChallengeStatus status,

	UUID opponent,

	PlayerColor initiatorSide,

	TimeControlDTO timeControl
) {
	
}
