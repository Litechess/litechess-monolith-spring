package com.trymad.litechess_monolith.matchmaking.internal.repository;

import java.util.List;
import java.util.Optional;

import com.trymad.litechess_monolith.matchmaking.internal.model.Challenge;

public interface ChallengeRepository {
	
	Optional<Challenge> findById(String id);

	List<Challenge> findAll();

	Challenge save(Challenge challenge);

	boolean exists(String id);

	void delete(String id);
	
}
