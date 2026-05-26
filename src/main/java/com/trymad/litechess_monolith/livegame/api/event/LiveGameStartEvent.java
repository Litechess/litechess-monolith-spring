package com.trymad.litechess_monolith.livegame.api.event;

import com.trymad.litechess_monolith.chessparty.api.event.GameSource;
import com.trymad.litechess_monolith.infrastructure.event.DomainEvent;
import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;

public record LiveGameStartEvent(LiveGameDTO dto, GameSource source) implements DomainEvent {
	
}
