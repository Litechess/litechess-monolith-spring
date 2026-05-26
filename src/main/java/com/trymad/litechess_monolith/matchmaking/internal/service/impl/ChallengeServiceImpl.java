package com.trymad.litechess_monolith.matchmaking.internal.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.infrastructure.event.EventPublisher;
import com.trymad.litechess_monolith.matchmaking.api.dto.CreateChallengeDTO;
import com.trymad.litechess_monolith.matchmaking.api.event.ChallengeAcceptedEvent;
import com.trymad.litechess_monolith.matchmaking.api.model.ChallengeStatus;
import com.trymad.litechess_monolith.matchmaking.internal.client.ChessPartyClient;
import com.trymad.litechess_monolith.matchmaking.internal.mapper.ChallengeMapper;
import com.trymad.litechess_monolith.matchmaking.internal.model.Challenge;
import com.trymad.litechess_monolith.matchmaking.internal.repository.ChallengeRepository;
import com.trymad.litechess_monolith.matchmaking.internal.service.ChallengeService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

	private final ChallengeRepository challengeRepository;
	private final ChallengeMapper mapper;
	private final ChessPartyClient chessPartyClient;
	private final EventPublisher publisher;

	@Override
	public Challenge get(String id) {
		return challengeRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("Challenge with id " + id + " didn`t found"));
	}

	@Override
	public Challenge createChallenge(CreateChallengeDTO dto) {
		final Challenge challenge = mapper.toEntity(dto);
		challenge.setStatus(ChallengeStatus.WAITING);
		challenge.setId(chessPartyClient.getUniqueId());
		return challengeRepository.save(challenge);
	}

	@Override
	public Challenge acceptChallenge(String challengeId, UUID acceptedUser) {
		final Challenge challenge = this.get(challengeId);

		if(challenge.getOpponent() != null && !challenge.getOpponent().equals(acceptedUser)) {
			throw new AccessDeniedException("User with id " + acceptedUser + " is not opponent for challenge " + challengeId);
		}

		if(!challenge.getStatus().equals(ChallengeStatus.WAITING)) {
			throw new IllegalArgumentException("Challenge with id " + challengeId + "already accepted");
		}

		challenge.setOpponent(acceptedUser);
		challenge.setStatus(ChallengeStatus.ACCEPTED);

		publisher.publish(new ChallengeAcceptedEvent(mapper.toDto(challenge)));

		return challenge;
	}

	@Override
	public Challenge confirmCreateChallenge(String challengeId) {
		final Challenge challenge = this.get(challengeId);

		if(!challenge.getStatus().equals(ChallengeStatus.ACCEPTED)) {
			throw new RuntimeException("Challenge with id " + challengeId + "is not accepted while confirming");
		}

		challenge.setStatus(ChallengeStatus.CREATED);
		return challenge;
	}

	@Override
	public List<Challenge> getAll() {
		return challengeRepository.findAll();
	}
	
}
