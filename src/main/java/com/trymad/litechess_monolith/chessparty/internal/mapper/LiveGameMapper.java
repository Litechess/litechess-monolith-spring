package com.trymad.litechess_monolith.chessparty.internal.mapper;

import com.trymad.litechess_monolith.chessparty.internal.model.ChessParty;
import com.trymad.litechess_monolith.infrastructure.mapper.EntityUpdateMapper;
import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;

public interface LiveGameMapper extends EntityUpdateMapper<ChessParty, LiveGameDTO> {
	
}
