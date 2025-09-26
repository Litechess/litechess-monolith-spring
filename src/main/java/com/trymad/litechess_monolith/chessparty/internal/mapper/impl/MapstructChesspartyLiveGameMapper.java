package com.trymad.litechess_monolith.chessparty.internal.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.trymad.litechess_monolith.chessparty.internal.mapper.LiveGameMapper;
import com.trymad.litechess_monolith.chessparty.internal.model.ChessParty;
import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;

@Mapper(
	componentModel = "spring",    
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapstructChesspartyLiveGameMapper extends LiveGameMapper {
	
	@Override
	void updateFromDto(@MappingTarget ChessParty entity, LiveGameDTO dto);

}
