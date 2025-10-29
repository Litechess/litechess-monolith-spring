package com.trymad.litechess_monolith.livegame.api.event;

import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;
import com.trymad.litechess_monolith.shared.event.DomainEvent;
import com.trymad.litechess_monolith.websocket.api.model.GameEventType;

public record GameFinishEvent(
    GameEventType type,
    LiveGameDTO finishedGame,
    ChessGameStatus status
) implements DomainEvent {

    public GameFinishEvent {
        type = GameEventType.GAME_FINISH;
    }
}