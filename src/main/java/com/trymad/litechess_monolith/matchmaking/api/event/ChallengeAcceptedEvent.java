package com.trymad.litechess_monolith.matchmaking.api.event;

import com.trymad.litechess_monolith.matchmaking.api.dto.ChallengeDTO;
import com.trymad.litechess_monolith.shared.event.DomainEvent;

public record ChallengeAcceptedEvent(ChallengeDTO challengeDTO) implements DomainEvent {
	
}
