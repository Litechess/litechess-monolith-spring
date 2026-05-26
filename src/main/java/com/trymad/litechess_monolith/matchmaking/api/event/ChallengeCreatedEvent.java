package com.trymad.litechess_monolith.matchmaking.api.event;

import com.trymad.litechess_monolith.infrastructure.event.DomainEvent;
import com.trymad.litechess_monolith.matchmaking.api.dto.ChallengeDTO;

public record ChallengeCreatedEvent(ChallengeDTO challengeDTO) implements DomainEvent {
	
}
