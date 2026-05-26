package com.trymad.litechess_monolith.livegame.internal.mapper;

import com.trymad.litechess_monolith.infrastructure.mapper.ToDtoMapper;
import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;
import com.trymad.litechess_monolith.livegame.internal.model.LiveGame;

public interface LiveGameMapper extends ToDtoMapper<LiveGame, LiveGameDTO> {
	
}
