package com.trymad.litechess_monolith.livegame.api.event;

import com.trymad.litechess_monolith.chessparty.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.shared.event.DomainEvent;

public record GameFinishEvent(ChessPartyDTO chessParty) implements DomainEvent {
	
}
