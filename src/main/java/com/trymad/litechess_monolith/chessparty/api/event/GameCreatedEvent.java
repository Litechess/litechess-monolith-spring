package com.trymad.litechess_monolith.chessparty.api.event;

import com.trymad.litechess_monolith.chessparty.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.shared.event.DomainEvent;

public record GameCreatedEvent(ChessPartyDTO chessParty) implements DomainEvent {
	
}
