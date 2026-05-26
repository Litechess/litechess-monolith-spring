package com.trymad.litechess_monolith.matchmaking.internal.mapper;

import com.trymad.litechess_monolith.infrastructure.mapper.BidirectionalMapper;
import com.trymad.litechess_monolith.matchmaking.api.dto.ChallengeDTO;
import com.trymad.litechess_monolith.matchmaking.api.dto.CreateChallengeDTO;
import com.trymad.litechess_monolith.matchmaking.internal.model.Challenge;

public interface ChallengeMapper extends BidirectionalMapper<Challenge, ChallengeDTO> {
	
	Challenge toEntity(CreateChallengeDTO dto);

}
