package com.trymad.litechess_monolith.chessparty.internal.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.trymad.litechess_monolith.chessparty.internal.mapper.ChessPartyMapper;
import com.trymad.litechess_monolith.chessparty.internal.mapper.TimeControlMapper;

@Mapper(
	componentModel = "spring",
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
	uses = {
		TimeControlMapper.class
	})
public interface MapstructChessPartyMapper extends ChessPartyMapper {
	

}
