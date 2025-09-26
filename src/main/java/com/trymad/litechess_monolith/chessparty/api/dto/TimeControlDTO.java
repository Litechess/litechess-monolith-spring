package com.trymad.litechess_monolith.chessparty.api.dto;

import com.trymad.litechess_monolith.chessparty.api.model.TimeControlType;

// add models for other module whoh use time control (livegame, matchmaking)
public record TimeControlDTO(Long initTime, Long increment) {
	
	public TimeControlType type() {
		if(initTime == null || initTime <= 0) {
			return TimeControlType.NO_CONTROL;
		} else if(increment != null && increment > 0) {
			return TimeControlType.WITH_INCREMENT;
		} else {
			return TimeControlType.WITHOUT_INCREMENT;
		}
	}
}
