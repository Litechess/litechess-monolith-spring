package com.trymad.litechess_monolith.chessparty.internal.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trymad.litechess_monolith.chessparty.api.dto.CreatePartyDTO;
import com.trymad.litechess_monolith.chessparty.api.event.GameCreatedEvent;
import com.trymad.litechess_monolith.chessparty.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerInfo;
import com.trymad.litechess_monolith.chessparty.internal.client.UserInfoClient;
import com.trymad.litechess_monolith.chessparty.internal.controller.filter.ChessPartyFilter;
import com.trymad.litechess_monolith.chessparty.internal.mapper.ChessPartyMapper;
import com.trymad.litechess_monolith.chessparty.internal.mapper.CreateChessPartyMapper;
import com.trymad.litechess_monolith.chessparty.internal.model.ChessParty;
import com.trymad.litechess_monolith.chessparty.internal.repository.ChessPartyRepository;
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

	private final static String DEFAULT_INIT_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";


	// todo optional return from repo
	public ChessParty get(Long id) {
		return chessPartyRepository.getById(id).get();
	}

	public boolean exists(Long id) {
		return chessPartyRepository.existsById(id);
	}
	
	public ChessParty update(ChessParty chessParty) {
		return chessPartyRepository.save(chessParty);
	}


	// time control ?
	public ChessParty create(CreatePartyDTO createGameDTO) {
		final ChessParty chessParty = createMapper.toEntity(createGameDTO);

		chessParty.setInitFen(DEFAULT_INIT_FEN);
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
				
				return isOponentContains && isOwnerContains;
			})
            .collect(Collectors.toList());
		
		return result;
	}
}
