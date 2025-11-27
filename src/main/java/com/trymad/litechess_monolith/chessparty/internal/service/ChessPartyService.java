package com.trymad.litechess_monolith.chessparty.internal.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trymad.litechess_monolith.chessparty.api.dto.CreatePartyDTO;
import com.trymad.litechess_monolith.chessparty.api.dto.TimeControlDTO;
import com.trymad.litechess_monolith.chessparty.api.event.GameCreatedEvent;
import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerInfo;
import com.trymad.litechess_monolith.chessparty.internal.client.UserInfoClient;
import com.trymad.litechess_monolith.chessparty.internal.controller.filter.ChessPartyFilter;
import com.trymad.litechess_monolith.chessparty.internal.mapper.ChessPartyMapper;
import com.trymad.litechess_monolith.chessparty.internal.mapper.CreateChessPartyMapper;
import com.trymad.litechess_monolith.chessparty.internal.mapper.LiveGameMapper;
import com.trymad.litechess_monolith.chessparty.internal.mapper.TimeControlMapper;
import com.trymad.litechess_monolith.chessparty.internal.model.ChessParty;
import com.trymad.litechess_monolith.chessparty.internal.repository.ChessPartyRepository;
import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;
import com.trymad.litechess_monolith.livegame.api.event.GameFinishEvent;
import com.trymad.litechess_monolith.matchmaking.api.event.GameFindedEvent;
import com.trymad.litechess_monolith.shared.event.EventPublisher;
import com.trymad.litechess_monolith.users.api.dto.UserInfoDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

// TODO logic when game end
public class ChessPartyService {
	
	private final ChessPartyRepository chessPartyRepository;
	private final EventPublisher eventPublisher;
	private final UserInfoClient userInfoClient;

	private final ChessPartyMapper mapper;
	private final CreateChessPartyMapper createMapper;
	private final LiveGameMapper liveGameMapper;
	private final TimeControlMapper timeControlMapper;

	private final static String DEFAULT_INIT_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";


	// todo optional return from repo
	public ChessParty get(String id) {
		return chessPartyRepository.getById(id).get();
	}

	public boolean exists(String id) {
		return chessPartyRepository.existsById(id);
	}
	
	public ChessParty update(ChessParty chessParty) {
		return chessPartyRepository.save(chessParty);
	}

	public ChessParty update(GameFinishEvent event) {
		final LiveGameDTO finishedGame = event.finishedGame();
		final ChessParty chessParty = this.get(finishedGame.id());

		liveGameMapper.updateFromDto(chessParty, finishedGame);
		chessParty.setStatus(event.status());

		return this.update(chessParty);
	}

	public void delete(String id) {
		chessPartyRepository.delete(id);
	}

	public void deleteAll(List<ChessParty> parties) {
		chessPartyRepository.deleteAll(parties);
	}

	public ChessParty save(CreatePartyDTO createGameDTO) {
		final ChessParty chessParty = createMapper.toEntity(createGameDTO);

		chessParty.setInitFen(DEFAULT_INIT_FEN);
		chessParty.setStatus(ChessGameStatus.NOT_FINISHED);
		chessParty.setTimeControl(timeControlMapper.toEntity(createGameDTO.timeControl()));

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
		final TimeControlDTO timeControl = event.gameRequest().timeControl();
		

		final ChessParty chessParty = this.save(new CreatePartyDTO(
			white,
			black,
			timeControl));
			
		eventPublisher.publish(new GameCreatedEvent(mapper.toDto(chessParty))); 
	}

	public List<ChessParty> get(ChessPartyFilter filter) {
		final List<ChessParty> dbGames = chessPartyRepository.getAll();

        List<ChessParty> result = dbGames.stream()
			.filter(chessParty -> {
				final UUID whiteId = chessParty.getWhite().id();
				final UUID blackId = chessParty.getBlack().id();
				
				final boolean isOwnerContains = (whiteId.equals(filter.ownerId()) || blackId.equals(filter.ownerId())) || filter.ownerId() == null;
				final boolean isOponentContains = (whiteId.equals(filter.oponentId()) || blackId.equals(filter.oponentId())) || filter.oponentId() == null;
				final boolean isLive = (chessParty.getStatus().equals(ChessGameStatus.NOT_FINISHED) && filter.live());
				final boolean isFinish = (chessParty.getStatus().equals(ChessGameStatus.NOT_FINISHED) == false && filter.finish());

				return isOponentContains && isOwnerContains && (isLive || isFinish);
			})
            .collect(Collectors.toList());
		
		return result;
	}
}
