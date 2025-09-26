package com.trymad.litechess_monolith.chessparty.internal.mapper.impl;

import org.mapstruct.Mapper;

import com.trymad.litechess_monolith.chessparty.internal.mapper.ChessPartyMapper;
import com.trymad.litechess_monolith.chessparty.internal.mapper.TimeControlMapper;

@Mapper(
	componentModel = "spring",
	uses = {
		TimeControlMapper.class
	})
public interface MapstructChessPartyMapper extends ChessPartyMapper {
	

}
