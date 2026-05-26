package com.trymad.litechess_monolith.livegame.internal.listener.gameCreated;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trymad.litechess_monolith.chessparty.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.chessparty.api.event.GameCreatedEvent;
import com.trymad.litechess_monolith.chessparty.api.event.GameSource;
import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerInfo;
import com.trymad.litechess_monolith.livegame.internal.service.LiveGameService;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class SpringLivegameGameCreatedEventListenerTest {

    @Mock
    private LiveGameService liveGameService;

    @Test
    void handleDelegatesGameCreationToService() {
        SpringLivegameGameCreatedEventListener listener = new SpringLivegameGameCreatedEventListener(liveGameService);
        ChessPartyDTO chessParty = new ChessPartyDTO(
            "game-1",
            new PlayerInfo(UUID.fromString("11111111-1111-1111-1111-111111111111"), "white"),
            new PlayerInfo(UUID.fromString("22222222-2222-2222-2222-222222222222"), "black"),
            List.of(),
            List.of(),
            new TimeControlDTO(0L, 0L),
            "fen",
            ChessGameStatus.NOT_FINISHED
        );
        GameCreatedEvent event = new GameCreatedEvent(chessParty, GameSource.MATCHMAKING);

        listener.handle(event);

        verify(liveGameService).create(chessParty, GameSource.MATCHMAKING);
    }
}
