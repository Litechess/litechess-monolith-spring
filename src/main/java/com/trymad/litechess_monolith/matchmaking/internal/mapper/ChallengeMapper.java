package com.trymad.litechess_monolith.matchmaking.internal.mapper;

import com.trymad.litechess_monolith.matchmaking.api.dto.ChallengeDTO;
import com.trymad.litechess_monolith.matchmaking.api.dto.CreateChallengeDTO;
import com.trymad.litechess_monolith.matchmaking.internal.model.Challenge;
import com.trymad.litechess_monolith.shared.mapper.BidirectionalMapper;

public interface ChallengeMapper extends BidirectionalMapper<Challenge, ChallengeDTO> {
	
	Challenge toEntity(CreateChallengeDTO dto);

}
