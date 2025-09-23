package com.trymad.litechess_monolith.livegame.internal.repository;

import java.util.List;
import java.util.Optional;

import com.trymad.litechess_monolith.livegame.internal.controller.filter.LiveGameFilter;
import com.trymad.litechess_monolith.livegame.internal.model.LiveGame;

public interface LiveGameRepository {

	Optional<LiveGame> findById(Long id);

	List<LiveGame> findAll();

	List<LiveGame> findAll(LiveGameFilter filter);

	boolean existsById(Long id);

	void delete(Long id);

	LiveGame save(LiveGame liveGame);
	
}
