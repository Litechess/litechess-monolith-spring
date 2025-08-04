package com.trymad.litechess_monolith.chessgame.internal.game;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.internal.model.LiveGame;
import com.trymad.litechess_monolith.chessgame.internal.model.PlayerColor;

@Component
public class InMemoryLiveGameStore implements LiveGameStore {

	private final ConcurrentHashMap<Long, LiveGame> liveGames;

	public InMemoryLiveGameStore() {
		liveGames = new ConcurrentHashMap<>();
	}

	@Override
	public LiveGame createGame(ChessParty chessParty, ChessPartyEmulator partyEmulator) {
		final Map<PlayerColor, UUID> players = 
			Map.of(PlayerColor.WHITE, chessParty.getWhite(),
				   PlayerColor.BLACK, chessParty.getBlack());
		final LiveGame liveGame = new LiveGame(players, partyEmulator);
		return liveGames.put(chessParty.getId(), liveGame);
	}

	@Override
	public LiveGame get(Long id) {
		return liveGames.get(id);
	}

	@Override
	public LiveGame delete(Long id) {
		return liveGames.remove(id);
	}

	@Override
	public boolean contains(Long id) {
		return liveGames.containsKey(id);
	}
	
}
