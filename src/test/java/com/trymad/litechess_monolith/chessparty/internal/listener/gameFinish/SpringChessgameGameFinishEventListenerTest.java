package com.trymad.litechess_monolith.chessparty.internal.listener.gameFinish;

import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.internal.service.ChessPartyService;
import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;
import com.trymad.litechess_monolith.livegame.api.event.GameFinishEvent;

@ExtendWith(MockitoExtension.class)
class SpringChessgameGameFinishEventListenerTest {

    @Mock
    private ChessPartyService chessPartyService;

    @Test
    void handleDelegatesPartyUpdateToService() {
        SpringChessgameGameFinishEventListener listener = new SpringChessgameGameFinishEventListener(chessPartyService);
        GameFinishEvent event = new GameFinishEvent(null, new LiveGameDTO("game-1", List.of(), Map.of(), List.of(), Map.of()), ChessGameStatus.DRAW);

        listener.handle(event);

        verify(chessPartyService).update(event);
    }
}
