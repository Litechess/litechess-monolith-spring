package com.trymad.litechess_monolith.chessparty.internal.controller.filter;

import java.util.UUID;

public record ChessPartyFilter(UUID ownerId, UUID oponentId, boolean live, boolean finish) {

	public static ChessPartyFilter empty() {
		return new ChessPartyFilter(null, null, false, true);
	}
}
