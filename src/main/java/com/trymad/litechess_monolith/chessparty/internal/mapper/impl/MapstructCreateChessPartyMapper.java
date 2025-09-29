package com.trymad.litechess_monolith.chessparty.internal.mapper.impl;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.trymad.litechess_monolith.chessparty.internal.mapper.CreateChessPartyMapper;
import com.trymad.litechess_monolith.chessparty.internal.mapper.TimeControlMapper;

@Mapper(
	componentModel = "spring", 
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
	collectionMappingStrategy = CollectionMappingStrategy.ACCESSOR_ONLY,
	uses = {
		TimeControlMapper.class
	})
public interface MapstructCreateChessPartyMapper extends CreateChessPartyMapper {
	
}
