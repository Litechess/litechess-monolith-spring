package com.trymad.litechess_monolith.livegame.api.event;

import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;
import com.trymad.litechess_monolith.shared.event.DomainEvent;

public record LiveGameStartEvent(LiveGameDTO dto) implements DomainEvent {
	
}
