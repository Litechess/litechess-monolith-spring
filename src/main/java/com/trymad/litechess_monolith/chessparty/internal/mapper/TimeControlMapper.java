package com.trymad.litechess_monolith.chessparty.internal.mapper;

import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.chessparty.internal.model.TimeControl;
import com.trymad.litechess_monolith.infrastructure.mapper.BidirectionalMapper;

public interface TimeControlMapper extends BidirectionalMapper<TimeControl, TimeControlDTO> {
	
}
