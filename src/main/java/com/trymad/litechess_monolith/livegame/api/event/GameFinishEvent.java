package com.trymad.litechess_monolith.livegame.api.event;

import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;
import com.trymad.litechess_monolith.shared.event.DomainEvent;

public record GameFinishEvent(LiveGameDTO finishedGame, ChessGameStatus status) implements DomainEvent {
	
}
