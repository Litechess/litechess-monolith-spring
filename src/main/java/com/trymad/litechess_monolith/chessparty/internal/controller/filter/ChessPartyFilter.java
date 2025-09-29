package com.trymad.litechess_monolith.chessparty.internal.controller.filter;

import java.util.UUID;

public record ChessPartyFilter(UUID ownerId, UUID oponentId) {

	public static ChessPartyFilter empty() {
		return new ChessPartyFilter(null, null);
	}
}
