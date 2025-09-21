package com.trymad.litechess_monolith.chessgame.internal.service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.trymad.litechess_monolith.chessgame.api.dto.CreatePartyDTO;
import com.trymad.litechess_monolith.chessgame.api.event.ChessPartyCreatedEvent;
import com.trymad.litechess_monolith.chessgame.api.event.GameFinishEvent;
import com.trymad.litechess_monolith.chessgame.api.event.MoveAcceptedEvent;
import com.trymad.litechess_monolith.chessgame.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.api.model.PlayerInfo;
import com.trymad.litechess_monolith.chessgame.internal.client.UserInfoClient;
import com.trymad.litechess_monolith.chessgame.internal.controller.filter.ChessPartyFilter;
import com.trymad.litechess_monolith.chessgame.internal.mapper.ChessPartyMapper;
import com.trymad.litechess_monolith.chessgame.internal.mapper.CreateChessPartyMapper;
import com.trymad.litechess_monolith.chessgame.internal.model.ChessParty;
import com.trymad.litechess_monolith.chessgame.internal.model.LiveGame;
import com.trymad.litechess_monolith.chessgame.internal.repository.ChessPartyRepository;
import com.trymad.litechess_monolith.matchmaking.api.event.GameFindedEvent;
import com.trymad.litechess_monolith.shared.event.EventPublisher;
import com.trymad.litechess_monolith.users.api.dto.UserInfoDTO;
import com.trymad.litechess_monolith.websocket.api.event.MoveEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

// TODO logic when game end
public class ChessPartyService {
	
	private final ChessPartyRepository chessPartyRepository;
	private final LiveGameService liveGameStore;
	private final EventPublisher eventPublisher;
	private final UserInfoClient userInfoClient;
	private final ChessPartyMapper mapper;
	private final CreateChessPartyMapper createMapper;

	private final static String DEFAULT_INIT_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	
	public boolean doMove(MoveEvent moveEvent) {
		final Long gameId = moveEvent.gameId();

		if(!liveGameStore.contains(gameId)) {
			ChessParty chessParty = chessPartyRepository.getById(gameId).get();
			if(chessParty.getStatus() != ChessGameStatus.NOT_FINISHED) {
				throw new IllegalStateException("Game is already finished: " + gameId);
			}
			
			liveGameStore.create(chessParty);
		}

		if(liveGameStore.doMove(moveEvent) == false) {
			return false;
		};
		
		final MoveAcceptedEvent acceptedMoveEvent = 
			new MoveAcceptedEvent(moveEvent.moveRequest(), moveEvent.gameId(), moveEvent.playerId());
		eventPublisher.publish(acceptedMoveEvent);

		return true;
	}

	public ChessParty get(Long id) {
		if(liveGameStore.contains(id)) return liveGameStore.get(id).getChessParty();
		return chessPartyRepository.getById(id).get();
	}

	public boolean exists(Long id) {
		return chessPartyRepository.existsById(id);
	}
	
	public ChessParty update(ChessParty chessParty) {
		return chessPartyRepository.save(chessParty);
	}

	public ChessParty create(CreatePartyDTO createGameDTO) {
		final ChessParty chessParty = createMapper.toEntity(createGameDTO);

		chessParty.setMoves(new LinkedList<>());
		chessParty.setInitFen(DEFAULT_INIT_FEN);
		chessParty.setCurrentFen(DEFAULT_INIT_FEN);
		chessParty.setStatus(ChessGameStatus.NOT_FINISHED);

		return chessPartyRepository.save(chessParty);
	}

	private final PlayerInfo getPlayerInfo(UUID id) {
		final UserInfoDTO infoDto = userInfoClient.get(id);
		return new PlayerInfo(infoDto.id(), infoDto.nickname());
	}

	public void createGame(GameFindedEvent event) {
		final int whiteIndex = ThreadLocalRandom.current().nextInt(2);
		final int blackIndex = whiteIndex == 0 ? 1 : 0;
		
		final PlayerInfo white = this.getPlayerInfo(event.players().get(whiteIndex));
		final PlayerInfo black = this.getPlayerInfo(event.players().get(blackIndex));
		

		final ChessParty chessParty = this.create(new CreatePartyDTO(
			white,
			black));
			
		eventPublisher.publish(new ChessPartyCreatedEvent(mapper.toDto(chessParty))); 
	}

	public List<ChessParty> get(ChessPartyFilter filter) {
		final List<ChessParty> dbGames = chessPartyRepository.getAll();

        List<ChessParty> result = Stream.concat(liveGameStore.getAll().stream()
			.map(LiveGame::getChessParty), dbGames.stream())
            .distinct()
			.filter(chessParty -> {
				final UUID whiteId = chessParty.getWhite().id();
				final UUID blackId = chessParty.getBlack().id();
				
				final boolean isOwnerContains = (whiteId.equals(filter.ownerId()) || blackId.equals(filter.ownerId())) || filter.ownerId() == null;
				final boolean isOponentContains = (whiteId.equals(filter.oponentId()) || blackId.equals(filter.oponentId())) || filter.oponentId() == null;
				
				return isOponentContains && isOwnerContains;
			})
            .collect(Collectors.toList());
		
		return result;
	}

	public boolean stopActiveGame(Long gameId) {
		return liveGameStore.delete(gameId) == null ? false : true;
	}

	public void finishGame(GameFinishEvent event) {
		this.update(mapper.toEntity(event.chessParty()));
		this.stopActiveGame(event.chessParty().id());
	
	}
}
