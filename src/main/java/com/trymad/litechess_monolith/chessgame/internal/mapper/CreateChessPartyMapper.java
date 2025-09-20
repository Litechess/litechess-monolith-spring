package com.trymad.litechess_monolith.chessgame.internal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.trymad.litechess_monolith.chessgame.api.dto.CreatePartyDTO;
import com.trymad.litechess_monolith.chessgame.internal.model.ChessParty;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateChessPartyMapper {
	
	ChessParty toEntity(CreatePartyDTO dto);

}
