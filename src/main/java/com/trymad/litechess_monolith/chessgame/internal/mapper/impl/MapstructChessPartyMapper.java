package com.trymad.litechess_monolith.chessgame.internal.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.trymad.litechess_monolith.chessgame.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.chessgame.internal.mapper.ChessPartyMapper;
import com.trymad.litechess_monolith.chessgame.internal.model.ChessParty;

@Mapper(componentModel = "spring")
public interface MapstructChessPartyMapper extends ChessPartyMapper {
	
	@Override
	void updateEntityFromDto(@MappingTarget ChessParty entity, ChessPartyDTO dto);

}
