package com.trymad.litechess_monolith.chessgame.api.event;

import com.trymad.litechess_monolith.chessgame.ChessParty;

import com.trymad.litechess_monolith.shared.event.DomainEvent;

public record ChessPartyCreatedEvent(ChessParty chessParty) implements DomainEvent {
	
}
