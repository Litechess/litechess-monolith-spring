package com.trymad.litechess_monolith.livegame.internal.mapper.impl;

import java.util.List;

import org.mapstruct.Mapper;

import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;
import com.trymad.litechess_monolith.livegame.internal.model.LiveGame;
import com.trymad.litechess_monolith.livegame.internal.model.TimerHistory;

@Mapper(componentModel = "spring")
public interface MapstructLiveGameMapper {
	
	LiveGameDTO toDto(LiveGame entity);

	default List<Long> map(TimerHistory history) {
		return history.getAsList();
	}
}
