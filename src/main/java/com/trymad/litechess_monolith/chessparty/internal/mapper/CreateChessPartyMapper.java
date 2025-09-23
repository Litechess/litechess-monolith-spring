package com.trymad.litechess_monolith.chessparty.internal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.trymad.litechess_monolith.chessparty.api.dto.CreatePartyDTO;
import com.trymad.litechess_monolith.chessparty.internal.model.ChessParty;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateChessPartyMapper {
	
	ChessParty toEntity(CreatePartyDTO dto);

}
