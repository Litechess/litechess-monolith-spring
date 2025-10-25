package com.trymad.litechess_monolith.livegame.internal.repository;

import java.util.List;
import java.util.Optional;

import com.trymad.litechess_monolith.livegame.internal.controller.filter.LiveGameFilter;
import com.trymad.litechess_monolith.livegame.internal.model.LiveGame;

public interface LiveGameRepository {

	Optional<LiveGame> findById(String id);

	List<LiveGame> findAll();

	List<LiveGame> findAll(LiveGameFilter filter);

	boolean existsById(String id);

	void delete(String id);

	LiveGame save(LiveGame liveGame);
	
}
