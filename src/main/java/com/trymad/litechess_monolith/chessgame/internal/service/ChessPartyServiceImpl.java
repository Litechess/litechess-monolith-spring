package com.trymad.litechess_monolith.chessgame.internal.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.trymad.litechess_monolith.chessgame.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.ChessPartyDTO;
import com.trymad.litechess_monolith.chessgame.ChessPartyService;
import com.trymad.litechess_monolith.chessgame.CreatePartyDTO;
import com.trymad.litechess_monolith.chessgame.PlayerInfo;
import com.trymad.litechess_monolith.chessgame.internal.client.UserInfoClient;
import com.trymad.litechess_monolith.chessgame.internal.event.ChessPartyCreatedEventPublisher;
import com.trymad.litechess_monolith.chessgame.internal.game.ChessPartyRepository;
import com.trymad.litechess_monolith.chessgame.internal.model.LiveGame;
import com.trymad.litechess_monolith.users.UserInfoDTO;
import com.trymad.litechess_monolith.websocket.ChessPartyCreatedEvent;
import com.trymad.litechess_monolith.websocket.GameFindedEvent;
import com.trymad.litechess_monolith.websocket.MoveEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

// TODO logic when game end
public class ChessPartyServiceImpl implements ChessPartyService {
	
	private final ChessPartyRepository chessPartyRepository;
	private final LiveGameStore liveGameStore;
	private final ChessUtilService chessUtilService;
	private final ChessPartyCreatedEventPublisher createdEventPublisher;
	private final UserInfoClient userInfoClient;

	
	public boolean doMove(MoveEvent moveEvent) {
		final Long gameId = moveEvent.gameId();

		if(!liveGameStore.contains(gameId)) {
			ChessParty chessParty = chessPartyRepository.getById(gameId).get();
			if(chessParty.getStatus() != ChessGameStatus.NOT_FINISHED) {
				throw new IllegalStateException("Game is already finished: " + gameId);
			}
			
			liveGameStore.create(chessParty);
		}

		return liveGameStore.doMove(moveEvent);
	}

	public ChessParty get(Long id) {
		if(liveGameStore.contains(id)) return liveGameStore.get(id).getChessParty();
		return chessPartyRepository.getById(id).get();
	}

	public boolean exists(Long id) {
		return chessPartyRepository.existsById(id);
	}
	
	public ChessParty save(ChessParty chessParty) {
		return chessPartyRepository.update(chessParty);
	}

	@Override
	public ChessParty create(CreatePartyDTO createGameDTO) {
		return chessPartyRepository.create(createGameDTO);
	}

	@Override
	public ChessPartyDTO getDto(ChessParty chessParty) {
		return new ChessPartyDTO(
			chessParty.getId(),
			chessParty.getWhite(),
			chessParty.getBlack(),
			chessParty.getMoveList().stream()
			.map(move -> {
				 return move.from() + move.to() + (move.promotion() == null ? "" : move.promotion().sanName().toLowerCase());
			}).toArray(String[]::new),
			chessUtilService.getFen(chessParty.getInitFen(), chessParty.getMoveList()),
			chessParty.getInitFen(),
			chessParty.getStatus());
	}

	private final PlayerInfo getPlayerInfo(UUID id) {
		final UserInfoDTO infoDto = userInfoClient.get(id);
		return new PlayerInfo(infoDto.id(), infoDto.nickname());
	}

	@EventListener
	@Async
	void on(GameFindedEvent event) {
		final int whiteIndex = ThreadLocalRandom.current().nextInt(2);
		final int blackIndex = whiteIndex == 0 ? 1 : 0;
		
		final PlayerInfo white = this.getPlayerInfo(event.players().get(whiteIndex));
		final PlayerInfo black = this.getPlayerInfo(event.players().get(blackIndex));
		

		final ChessParty chessParty = this.create(new CreatePartyDTO(
			white,
			black));
			
		createdEventPublisher.publish(new ChessPartyCreatedEvent(chessParty));
	}

	@Override
	public List<ChessParty> getAll(boolean activeGames) {
		final List<ChessParty> dbGames = chessPartyRepository.getAll();
        List<ChessParty> result = activeGames ? 
		Stream.concat(liveGameStore.getAll().stream().map(LiveGame::getChessParty), dbGames.stream())
            .distinct()
            .collect(Collectors.toList()) 
		: dbGames;
		
		return result;
	}

	@Override
	public boolean stopActiveGame(Long gameId) {
		return liveGameStore.delete(gameId) == null ? false : true;
	}
}
