package com.trymad.litechess_monolith.chessparty.api.event;

import com.trymad.litechess_monolith.chessparty.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.infrastructure.event.DomainEvent;

public record GameCreatedEvent(
	ChessPartyDTO chessParty, GameSource source
) implements DomainEvent {
	
}
