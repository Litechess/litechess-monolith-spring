package com.trymad.litechess_monolith.livegame.internal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trymad.litechess_monolith.chessparty.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.chessparty.api.event.GameSource;
import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerInfo;
import com.trymad.litechess_monolith.infrastructure.event.EventPublisher;
import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;
import com.trymad.litechess_monolith.livegame.api.event.DeclineDrawEvent;
import com.trymad.litechess_monolith.livegame.api.event.GameFinishEvent;
import com.trymad.litechess_monolith.livegame.api.event.LiveGameStartEvent;
import com.trymad.litechess_monolith.livegame.internal.emulator.ChessPartyEmulator;
import com.trymad.litechess_monolith.livegame.internal.mapper.LiveGameMapper;
import com.trymad.litechess_monolith.livegame.internal.mapper.MoveMapper;
import com.trymad.litechess_monolith.livegame.internal.model.GameTimer;
import com.trymad.litechess_monolith.livegame.internal.model.LiveGame;
import com.trymad.litechess_monolith.livegame.internal.model.TimerHistory;
import com.trymad.litechess_monolith.livegame.internal.repository.LiveGameRepository;
import com.trymad.litechess_monolith.websocket.api.dto.MoveRequest;
import com.trymad.litechess_monolith.websocket.api.event.MoveEvent;

@ExtendWith(MockitoExtension.class)
class LiveGameServiceTest {

    @Mock
    private LiveGameRepository liveGameRepository;

    @Mock
    private GameTimeService gameTimeService;

    @Mock
    private ChessPartyEmulatorService emulatorService;

    @Mock
    private MoveMapper moveMapper;

    @Mock
    private LiveGameMapper liveGameMapper;

    @Mock
    private EventPublisher eventPublisher;

    @Test
    void createRejectsFinishedParty() {
        LiveGameService service = createService();
        ChessPartyDTO chessParty = chessParty("game-1", ChessGameStatus.DRAW, noControl(), List.of());

        assertThrows(IllegalStateException.class, () -> service.create(chessParty, GameSource.MATCHMAKING));

        verifyNoInteractions(liveGameRepository, gameTimeService, emulatorService, liveGameMapper, eventPublisher);
    }

    @Test
    void createStartsTimerAndPublishesStartEventForTimedGame() {
        LiveGameService service = createService();
        ChessPartyDTO chessParty = chessParty("game-1", ChessGameStatus.NOT_FINISHED, timeControl(300_000L, 0L), List.of());
        GameTimer gameTimer = mock(GameTimer.class);
        ChessPartyEmulator emulator = mock(ChessPartyEmulator.class);
        LiveGameDTO liveGameDto = liveGameDto(chessParty);

        when(gameTimeService.createTimer(eq(chessParty.timeControl()), any(TimerHistory.class))).thenReturn(gameTimer);
        when(emulatorService.createEmulator(chessParty.id())).thenReturn(emulator);
        when(liveGameRepository.save(any(LiveGame.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(liveGameMapper.toDto(any(LiveGame.class))).thenReturn(liveGameDto);

        LiveGame result = service.create(chessParty, GameSource.MATCHMAKING);

        assertNotNull(result);
        assertEquals(chessParty.id(), result.getId());
        verify(emulator).setPosition(chessParty.moves());
        verify(gameTimer).start();
        verify(gameTimeService).startTimer(eq(chessParty.id()), same(gameTimer), any(Runnable.class));
        verify(eventPublisher).publish(argThat(event ->
            event instanceof LiveGameStartEvent startEvent
                && startEvent.dto().equals(liveGameDto)
                && startEvent.source() == GameSource.MATCHMAKING));
    }

    @Test
    void playMoveRejectsMoveWhenItIsNotPlayersTurn() {
        LiveGameService service = createService();
        ChessPartyDTO chessParty = chessParty("game-1", ChessGameStatus.NOT_FINISHED, noControl(), List.of());
        LiveGame liveGame = new LiveGame(chessParty, null);
        UUID blackPlayerId = chessParty.black().id();
        MoveEvent moveEvent = new MoveEvent(new MoveRequest("e2", "e4", null, "e4", 1), liveGame.getId(), blackPlayerId);

        when(liveGameRepository.findById(liveGame.getId())).thenReturn(Optional.of(liveGame));

        assertThrows(IllegalStateException.class, () -> service.playMove(moveEvent));
    }

    @Test
    void playMoveDeclinesDrawBeforePublishingAcceptedMove() {
        LiveGameService service = createService();
        ChessPartyDTO chessParty = chessParty("game-1", ChessGameStatus.NOT_FINISHED, noControl(), List.of());
        LiveGame liveGame = new LiveGame(chessParty, null);
        liveGame.proposeDraw(chessParty.black().id());

        MoveRequest moveRequest = new MoveRequest("e2", "e4", null, "e4", 1);
        MoveEvent moveEvent = new MoveEvent(moveRequest, liveGame.getId(), chessParty.white().id());
        GameMove mappedMove = new GameMove("e2", "e4", null, "e4", 1);
        ChessPartyEmulator emulator = mock(ChessPartyEmulator.class);

        when(liveGameRepository.findById(liveGame.getId())).thenReturn(Optional.of(liveGame));
        when(emulatorService.getEmulator(liveGame.getId())).thenReturn(emulator);
        when(emulator.gameStatus()).thenReturn(ChessGameStatus.NOT_FINISHED, ChessGameStatus.NOT_FINISHED);
        when(moveMapper.toEntity(moveRequest)).thenReturn(mappedMove);
        when(emulator.move(mappedMove)).thenReturn(mappedMove);
        when(liveGameRepository.save(liveGame)).thenReturn(liveGame);

        service.playMove(moveEvent);

        InOrder inOrder = inOrder(eventPublisher, liveGameRepository);
        inOrder.verify(eventPublisher).publish(argThat(event ->
            event instanceof DeclineDrawEvent declineDrawEvent
                && declineDrawEvent.gameId().equals(liveGame.getId())
                && declineDrawEvent.playerId().equals(chessParty.white().id())));
        inOrder.verify(eventPublisher).publish(argThat(event ->
            event instanceof com.trymad.litechess_monolith.chessparty.api.event.MoveAcceptedEvent acceptedEvent
                && acceptedEvent.gameId().equals(liveGame.getId())
                && acceptedEvent.move().equals(mappedMove)
                && acceptedEvent.timers() == null));
        inOrder.verify(liveGameRepository).save(liveGame);
        assertEquals(1, liveGame.getMoves().size());
        assertFalse(liveGame.isDrawProposed());
    }

    @Test
    void drawPropositionFinishesGameWhenOpponentAcceptsByRepeatingProposal() {
        LiveGameService service = createService();
        ChessPartyDTO chessParty = chessParty("game-1", ChessGameStatus.NOT_FINISHED, noControl(), List.of());
        LiveGame liveGame = new LiveGame(chessParty, null);
        LiveGameDTO liveGameDto = liveGameDto(chessParty);
        liveGame.proposeDraw(chessParty.white().id());

        when(liveGameRepository.findById(liveGame.getId())).thenReturn(Optional.of(liveGame));
        when(liveGameMapper.toDto(liveGame)).thenReturn(liveGameDto);

        service.drawProposition(liveGame, chessParty.black().id());

        verify(gameTimeService).stopTimer(liveGame.getId());
        verify(eventPublisher).publish(argThat(event ->
            event instanceof GameFinishEvent finishEvent
                && finishEvent.finishedGame().equals(liveGameDto)
                && finishEvent.status() == ChessGameStatus.DRAW));
        verify(liveGameRepository).delete(liveGame.getId());
        verify(emulatorService).deleteEmulator(liveGame.getId());
    }

    @Test
    void finishGameAddsZeroTimeOnTimeoutAndDeletesResources() {
        LiveGameService service = createService();
        ChessPartyDTO chessParty = chessParty("game-1", ChessGameStatus.NOT_FINISHED, timeControl(60_000L, 0L), List.of());
        GameTimer timer = new GameTimer(new TimerHistory(), chessParty.timeControl());
        LiveGame liveGame = new LiveGame(chessParty, timer);
        LiveGameDTO liveGameDto = liveGameDto(chessParty);

        when(liveGameMapper.toDto(liveGame)).thenReturn(liveGameDto);

        service.finishGame(liveGame, ChessGameStatus.TIMEOUT_WIN_BLACK);

        assertEquals(List.of(0L), liveGame.getTimerHistory().getAsList());
        verify(gameTimeService).stopTimer(liveGame.getId());
        verify(eventPublisher).publish(argThat(event ->
            event instanceof GameFinishEvent finishEvent
                && finishEvent.finishedGame().equals(liveGameDto)
                && finishEvent.status() == ChessGameStatus.TIMEOUT_WIN_BLACK));
        verify(liveGameRepository).delete(liveGame.getId());
        verify(emulatorService).deleteEmulator(liveGame.getId());
    }

    private LiveGameService createService() {
        return new LiveGameService(
            liveGameRepository,
            gameTimeService,
            emulatorService,
            moveMapper,
            liveGameMapper,
            eventPublisher
        );
    }

    private ChessPartyDTO chessParty(String id, ChessGameStatus status, TimeControlDTO timeControl, List<GameMove> moves) {
        UUID whiteId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID blackId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        return new ChessPartyDTO(
            id,
            new PlayerInfo(whiteId, "white"),
            new PlayerInfo(blackId, "black"),
            moves,
            List.of(),
            timeControl,
            "fen",
            status
        );
    }

    private LiveGameDTO liveGameDto(ChessPartyDTO chessParty) {
        return new LiveGameDTO(
            chessParty.id(),
            chessParty.moves(),
            Map.of(PlayerColor.WHITE, chessParty.white().id(), PlayerColor.BLACK, chessParty.black().id()),
            chessParty.timerHistory(),
            Map.of()
        );
    }

    private TimeControlDTO noControl() {
        return new TimeControlDTO(0L, 0L);
    }

    private TimeControlDTO timeControl(Long initTime, Long increment) {
        return new TimeControlDTO(initTime, increment);
    }
}
