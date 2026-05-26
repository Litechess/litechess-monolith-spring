package com.trymad.litechess_monolith.matchmaking.internal.service;

import java.util.List;
import java.util.UUID;

import com.trymad.litechess_monolith.matchmaking.api.dto.CreateChallengeDTO;
import com.trymad.litechess_monolith.matchmaking.internal.model.Challenge;

public interface ChallengeService {
	
	Challenge get(String id);

	List<Challenge> getAll();

	Challenge createChallenge(CreateChallengeDTO dto);

	Challenge acceptChallenge(String challengeId, UUID acceptedUser);

	Challenge confirmCreateChallenge(String challengeId);

}
