package com.trymad.litechess_monolith.matchmaking.internal.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.trymad.litechess_monolith.matchmaking.internal.mapper.ChallengeMapper;

@Mapper(
	componentModel = "spring",    
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapstructChallengeMapperImpl extends ChallengeMapper {
	
}
