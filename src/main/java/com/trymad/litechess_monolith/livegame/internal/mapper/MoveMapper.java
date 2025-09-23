package com.trymad.litechess_monolith.livegame.internal.mapper;

import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.shared.mapper.ToEntityMapper;
import com.trymad.litechess_monolith.websocket.api.dto.MoveRequest;

public interface MoveMapper extends ToEntityMapper<GameMove, MoveRequest> {
	
}
