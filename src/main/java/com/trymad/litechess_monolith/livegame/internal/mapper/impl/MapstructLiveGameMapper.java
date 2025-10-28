package com.trymad.litechess_monolith.livegame.internal.mapper.impl;

import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;
import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;
import com.trymad.litechess_monolith.livegame.internal.mapper.LiveGameMapper;
import com.trymad.litechess_monolith.livegame.internal.model.LiveGame;
import com.trymad.litechess_monolith.livegame.internal.model.TimerHistory;

@Mapper(componentModel = "spring")
public interface MapstructLiveGameMapper  extends LiveGameMapper {

    @Mapping(target = "currentTimers", expression = "java(getCurrentTimers(entity))")
    LiveGameDTO toDto(LiveGame entity);

	default List<Long> map(TimerHistory history) {
		if(history == null) return null;
		return history.getAsList();
	}

	default Map<PlayerColor, Long> getCurrentTimers(LiveGame entity) {
		if(entity.getTimer() == null) {
			return null;
		}

		return Map.of(
			PlayerColor.WHITE, entity.getTimer().getRemainingTime(PlayerColor.WHITE).toEpochMilli(),
			PlayerColor.BLACK, entity.getTimer().getRemainingTime(PlayerColor.BLACK).toEpochMilli()
		);
	}
}
