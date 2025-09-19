package com.trymad.litechess_monolith.chessgame.api.event;

import com.trymad.litechess_monolith.chessgame.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.shared.event.DomainEvent;

public record ChessPartyCreatedEvent(ChessPartyDTO chessParty) implements DomainEvent {
	
}
