package com.trymad.litechess_monolith.chessgame.internal.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.GameFinishEvent;
import com.trymad.litechess_monolith.chessgame.internal.event.GameFinishEventPublisher;
import com.trymad.litechess_monolith.chessgame.internal.game.emulator.ChessPartyEmulatorFactory;
import com.trymad.litechess_monolith.chessgame.internal.model.LiveGame;
import com.trymad.litechess_monolith.chessgame.internal.service.ChessUtilService;
import com.trymad.litechess_monolith.chessgame.internal.service.LiveGameStore;
import com.trymad.litechess_monolith.websocket.MoveEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InMemoryLiveGameStore implements LiveGameStore {

	private final Map<Long, LiveGame> liveGames = new ConcurrentHashMap<>();
	private final ChessPartyEmulatorFactory chessPartyEmulatorFactory;
	private final ChessUtilService chessUtilService;
	private final GameFinishEventPublisher gameFinishEventPublisher;

	@Override
	public LiveGame create(ChessParty chessParty) {
		if(this.contains(chessParty.getId())) return liveGames.get(chessParty.getId());
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

	@Override
	public List<LiveGame> getAll() {
		final List<LiveGame> list = new ArrayList<>(liveGames.size());
		list.addAll(liveGames.values());
		return list;
	}

	@Override
	public boolean doMove(MoveEvent event) {
		if(!liveGames.containsKey(event.gameId())) throw new IllegalStateException("live game dont exists");
		final LiveGame liveGame = liveGames.get(event.gameId());
		if(liveGame.getStatus() != ChessGameStatus.NOT_FINISHED) return false;
		
		boolean isMoveDone = liveGame.playMove(chessUtilService.toGameMove(event.moveRequest()), event.playerId());
		if(liveGame.getStatus() != ChessGameStatus.NOT_FINISHED) {
			gameFinishEventPublisher.publish(new GameFinishEvent(liveGame.getChessParty()));
		}

		return isMoveDone;

	}
}
