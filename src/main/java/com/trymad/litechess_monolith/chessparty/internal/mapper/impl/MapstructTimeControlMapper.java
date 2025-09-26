package com.trymad.litechess_monolith.chessparty.internal.mapper.impl;

import java.time.Duration;

import org.mapstruct.Mapper;

import com.trymad.litechess_monolith.chessparty.internal.mapper.TimeControlMapper;

@Mapper(componentModel = "spring")
public interface MapstructTimeControlMapper extends TimeControlMapper {
	
	default Long map(Duration duration) {
		return duration.toMillis();
	}

	default Duration map(Long millis) {
		return Duration.ofMillis(millis);
	}
}
