package com.trymad.litechess_monolith.livegame.internal.model;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.trymad.litechess_monolith.chessparty.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.chessparty.api.model.GameMove;
import com.trymad.litechess_monolith.chessparty.api.model.PlayerColor;
import com.trymad.litechess_monolith.chessparty.api.model.TimeControlType;

public class LiveGame {

	private final String id;
	private final List<GameMove> moves = new LinkedList<>();
	private final Map<PlayerColor, UUID> playerSides = new EnumMap<>(PlayerColor.class);
	private final GameTimer gameTimer;

	public LiveGame(ChessPartyDTO chessParty, GameTimer gameTimer) {
		this.id = chessParty.id();
		this.moves.addAll(chessParty.moves());

		final boolean isTimeConrolledGame = chessParty.timeControl().type() != TimeControlType.NO_CONTROL;
		if(isTimeConrolledGame && gameTimer == null) {
			throw new IllegalArgumentException("GameTimer must be provided for time controlled games");
		}

		if(isTimeConrolledGame) {
			this.gameTimer = gameTimer;
		} else {
			this.gameTimer = null;
		}

		playerSides.put(PlayerColor.WHITE, chessParty.white().id());
		playerSides.put(PlayerColor.BLACK, chessParty.black().id());
	}

	public void applyMove(GameMove move) {
		moves.add(move);

		if(gameTimer != null) {
			gameTimer.applyMove();
		}
	}

	public PlayerColor getCurrentTurnColor() {
		return moves.size() % 2 == 0 ? PlayerColor.WHITE : PlayerColor.BLACK;
	}

	public UUID getPlayer(PlayerColor color) {
		return playerSides.get(color);
	}

	public GameTimer getTimer() {
		return gameTimer;
	}

	public List<GameMove> getMoves() {
		return moves;
	}

	public TimerHistory getTimerHistory() {
		if(gameTimer == null) return null;
		return gameTimer.getTimerHistory();
	}

	public Map<PlayerColor, UUID> getPlayerSides() {
		return playerSides;
	}

	public String getId() {
		return this.id;
	}
}
