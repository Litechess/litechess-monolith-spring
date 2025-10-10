package com.trymad.litechess_monolith.livegame.api.dto;

import java.util.List;

public record MultipleLiveGameResponse(List<LiveGameDTO> games, Long serverNow) {
	
}
