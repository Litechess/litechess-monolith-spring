package com.trymad.litechess_monolith.chessgame.internal.game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.internal.game.emulator.ChessPartyEmulatorFactory;
import com.trymad.litechess_monolith.chessgame.internal.model.LiveGame;
import com.trymad.litechess_monolith.chessgame.internal.service.LiveGameStore;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InMemoryLiveGameStore implements LiveGameStore {

	private final Map<Long, LiveGame> liveGames = new ConcurrentHashMap<>();
	private final ChessPartyEmulatorFactory chessPartyEmulatorFactory;

	@Override
	public LiveGame create(ChessParty chessParty) {
		final LiveGame liveGame = new LiveGame(chessParty, chessPartyEmulatorFactory.create(chessParty));
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
