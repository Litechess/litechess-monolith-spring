package com.trymad.litechess_monolith.matchmaking.internal.model;

import java.util.UUID;

import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;
import com.trymad.litechess_monolith.matchmaking.api.model.ChallengeStatus;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Challenge {

	String id;

	UUID initiator;

	@Nullable
	UUID opponent;

	@Nullable
	PlayerColor initiatorSide;

	ChallengeStatus status;

	TimeControlDTO timeControl;

}
