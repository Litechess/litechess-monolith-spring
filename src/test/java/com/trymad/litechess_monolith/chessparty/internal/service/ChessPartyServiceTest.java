package com.trymad.litechess_monolith.chessparty.internal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trymad.litechess_monolith.chessparty.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.chessparty.api.dto.CreatePartyDTO;
import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.chessparty.api.event.GameCreatedEvent;
import com.trymad.litechess_monolith.chessparty.api.event.GameSource;
import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerInfo;
import com.trymad.litechess_monolith.chessparty.internal.client.UserInfoClient;
import com.trymad.litechess_monolith.chessparty.internal.controller.filter.ChessPartyFilter;
import com.trymad.litechess_monolith.chessparty.internal.mapper.ChessPartyMapper;
import com.trymad.litechess_monolith.chessparty.internal.mapper.CreateChessPartyMapper;
import com.trymad.litechess_monolith.chessparty.internal.mapper.LiveGameMapper;
import com.trymad.litechess_monolith.chessparty.internal.mapper.TimeControlMapper;
import com.trymad.litechess_monolith.chessparty.internal.model.ChessParty;
import com.trymad.litechess_monolith.chessparty.internal.model.TimeControl;
import com.trymad.litechess_monolith.chessparty.internal.repository.ChessPartyRepository;
import com.trymad.litechess_monolith.infrastructure.event.EventPublisher;
import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;
import com.trymad.litechess_monolith.livegame.api.event.GameFinishEvent;
import com.trymad.litechess_monolith.matchmaking.api.dto.ChallengeDTO;
import com.trymad.litechess_monolith.matchmaking.api.event.ChallengeAcceptedEvent;
import com.trymad.litechess_monolith.matchmaking.api.model.ChallengeStatus;
import com.trymad.litechess_monolith.users.api.dto.UserInfoDTO;

@ExtendWith(MockitoExtension.class)
class ChessPartyServiceTest {

    @Mock
    private ChessPartyRepository chessPartyRepository;

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private UserInfoClient userInfoClient;

    @Mock
    private ChessPartyMapper mapper;

    @Mock
    private CreateChessPartyMapper createMapper;

    @Mock
    private LiveGameMapper liveGameMapper;

    @Mock
    private TimeControlMapper timeControlMapper;

    @Test
    void saveRejectsFinishedStatus() {
        ChessPartyService service = createService();

        assertThrows(IllegalArgumentException.class, () -> service.save(createPartyDto(), ChessGameStatus.DRAW));

        verifyNoInteractions(chessPartyRepository, eventPublisher, userInfoClient, mapper, createMapper, liveGameMapper, timeControlMapper);
    }

    @Test
    void createGameSavesPartyWithDefaultsAndPublishesEvent() {
        ChessPartyService service = createService();
        CreatePartyDTO createPartyDTO = createPartyDto();
        ChessParty mappedParty = new ChessParty(null, createPartyDTO.white(), createPartyDTO.black(), null, null, null, null, null);
        TimeControl mappedTimeControl = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
        ChessPartyDTO chessPartyDto = chessPartyDto("game-1", createPartyDTO.white(), createPartyDTO.black(), ChessGameStatus.NOT_FINISHED, createPartyDTO.timeControl());

        when(createMapper.toEntity(createPartyDTO)).thenReturn(mappedParty);
        when(timeControlMapper.toEntity(createPartyDTO.timeControl())).thenReturn(mappedTimeControl);
        when(chessPartyRepository.save(any(ChessParty.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toDto(any(ChessParty.class))).thenReturn(chessPartyDto);

        ChessParty result = service.createGame(createPartyDTO, GameSource.MATCHMAKING, "game-1");

        assertSame(mappedParty, result);
        assertEquals("game-1", result.getId());
        assertEquals(ChessGameStatus.NOT_FINISHED, result.getStatus());
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", result.getInitFen());
        assertSame(mappedTimeControl, result.getTimeControl());
        verify(eventPublisher).publish(argThat(event ->
            event instanceof GameCreatedEvent gameCreatedEvent
                && gameCreatedEvent.chessParty().equals(chessPartyDto)
                && gameCreatedEvent.source() == GameSource.MATCHMAKING));
    }

    @Test
    void updateAppliesFinishedGameStateAndPersistsParty() {
        ChessPartyService service = createService();
        ChessParty chessParty = new ChessParty("game-1", player("white"), player("black"), null, null, null, null, ChessGameStatus.NOT_FINISHED);
        LiveGameDTO finishedGame = new LiveGameDTO("game-1", List.of(), java.util.Map.of(), List.of(), java.util.Map.of());
        GameFinishEvent event = new GameFinishEvent(null, finishedGame, ChessGameStatus.DRAW);

        when(chessPartyRepository.getById("game-1")).thenReturn(Optional.of(chessParty));
        doNothing().when(liveGameMapper).updateFromDto(chessParty, finishedGame);
        when(chessPartyRepository.save(chessParty)).thenReturn(chessParty);

        ChessParty result = service.update(event);

        assertSame(chessParty, result);
        assertEquals(ChessGameStatus.DRAW, chessParty.getStatus());
        verify(liveGameMapper).updateFromDto(chessParty, finishedGame);
        verify(chessPartyRepository).save(chessParty);
    }

    @Test
    void createGameFromAcceptedChallengeUsesRequestedColorsAndChallengeId() {
        ChessPartyService service = createService();
        UUID initiatorId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID opponentId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        TimeControlDTO timeControl = new TimeControlDTO(180_000L, 2_000L);
        ChallengeDTO challengeDTO = new ChallengeDTO("challenge-1", initiatorId, ChallengeStatus.ACCEPTED, opponentId, PlayerColor.WHITE, timeControl);
        ChessPartyDTO publishedDto = chessPartyDto("challenge-1", new PlayerInfo(initiatorId, "initiator"), new PlayerInfo(opponentId, "opponent"), ChessGameStatus.NOT_FINISHED, timeControl);

        when(userInfoClient.get(initiatorId)).thenReturn(new UserInfoDTO(initiatorId, "initiator", LocalDateTime.now(), null));
        when(userInfoClient.get(opponentId)).thenReturn(new UserInfoDTO(opponentId, "opponent", LocalDateTime.now(), null));
        when(createMapper.toEntity(any(CreatePartyDTO.class))).thenAnswer(invocation -> {
            CreatePartyDTO dto = invocation.getArgument(0);
            return new ChessParty(null, dto.white(), dto.black(), null, null, null, null, null);
        });
        when(timeControlMapper.toEntity(timeControl)).thenReturn(new TimeControl(Duration.ofMinutes(3), Duration.ofSeconds(2)));
        when(chessPartyRepository.save(any(ChessParty.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toDto(any(ChessParty.class))).thenReturn(publishedDto);

        ChessParty result = service.createGame(new ChallengeAcceptedEvent(challengeDTO));

        ArgumentCaptor<ChessParty> captor = ArgumentCaptor.forClass(ChessParty.class);
        verify(chessPartyRepository).save(captor.capture());
        ChessParty savedParty = captor.getValue();
        assertSame(savedParty, result);
        assertEquals("challenge-1", savedParty.getId());
        assertEquals(initiatorId, savedParty.getWhite().id());
        assertEquals(opponentId, savedParty.getBlack().id());
        verify(eventPublisher).publish(argThat(event ->
            event instanceof GameCreatedEvent gameCreatedEvent
                && gameCreatedEvent.chessParty().equals(publishedDto)
                && gameCreatedEvent.source() == GameSource.CHALLENGE));
    }

    @Test
    void getFiltersPartiesByOwnerAndLiveFlag() {
        ChessPartyService service = createService();
        UUID ownerId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID otherId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        ChessParty liveOwnedParty = new ChessParty("game-1", new PlayerInfo(ownerId, "owner"), new PlayerInfo(otherId, "other"), null, null, null, null, ChessGameStatus.NOT_FINISHED);
        ChessParty finishedOwnedParty = new ChessParty("game-2", new PlayerInfo(ownerId, "owner"), new PlayerInfo(otherId, "other"), null, null, null, null, ChessGameStatus.DRAW);
        ChessParty unrelatedParty = new ChessParty("game-3", new PlayerInfo(otherId, "other"), player("third"), null, null, null, null, ChessGameStatus.NOT_FINISHED);

        when(chessPartyRepository.getAll()).thenReturn(List.of(liveOwnedParty, finishedOwnedParty, unrelatedParty));

        List<ChessParty> result = service.get(new ChessPartyFilter(ownerId, null, true, false));

        assertEquals(List.of(liveOwnedParty), result);
    }

    private ChessPartyService createService() {
        return new ChessPartyService(
            chessPartyRepository,
            eventPublisher,
            userInfoClient,
            mapper,
            createMapper,
            liveGameMapper,
            timeControlMapper
        );
    }

    private CreatePartyDTO createPartyDto() {
        return new CreatePartyDTO(player("white"), player("black"), new TimeControlDTO(300_000L, 0L));
    }

    private ChessPartyDTO chessPartyDto(String id, PlayerInfo white, PlayerInfo black, ChessGameStatus status, TimeControlDTO timeControl) {
        return new ChessPartyDTO(id, white, black, List.of(), List.of(), timeControl, "fen", status);
    }

    private PlayerInfo player(String name) {
        return new PlayerInfo(UUID.nameUUIDFromBytes(name.getBytes()), name);
    }
}
