package com.trymad.litechess_monolith.chessparty.internal.infrastructure;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.internal.controller.filter.ChessPartyFilter;
import com.trymad.litechess_monolith.chessparty.internal.model.ChessParty;
import com.trymad.litechess_monolith.chessparty.internal.service.ChessPartyService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor


public class GameCleanerOnLaunch {
	
	private final ChessPartyService chessPartyService;

	@EventListener
	void handle(ApplicationReadyEvent event) {
		final List<ChessParty> parties = chessPartyService.get(new ChessPartyFilter(null, null, true, false));
		chessPartyService.deleteAll(parties);
	}
	
}
