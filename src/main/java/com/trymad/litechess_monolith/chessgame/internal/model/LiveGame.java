package com.trymad.litechess_monolith.chessgame.internal.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

import com.trymad.litechess_monolith.chessgame.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.GameMove;
import com.trymad.litechess_monolith.chessgame.internal.game.emulator.ChessPartyEmulator;

public class LiveGame {

	private final Long id;
	private final ChessParty chessParty;
	private final ChessPartyEmulator emulator;
	private final Map<PlayerColor, UUID> playerColors = new EnumMap<>(PlayerColor.class);

	public LiveGame(ChessParty chessParty, ChessPartyEmulator emulator) {
		this.chessParty = chessParty;
		this.emulator = emulator;
		this.id = chessParty.getId();
		setPlayers(chessParty);
	}

	public Long getId() {
		return this.id;
	}

	public boolean playMove(GameMove move, UUID playerId) {
		final PlayerColor currentColorTurn = emulator.getCurrentTurnColor();
		if (!playerColors.get(currentColorTurn).equals(playerId)) {
			return false;
		}
		emulator.move(move);
		return true;
	}

	public ChessParty getChessParty() {
		chessParty.setStatus(emulator.gameStatus());
		chessParty.getMoveList().clear();
		chessParty.getMoveList().addAll(emulator.getMoveList());
		return chessParty;
	}

	public ChessGameStatus getStatus() {
		return emulator.gameStatus();
	}

	private void setPlayers(ChessParty chessParty) {
		playerColors.put(PlayerColor.WHITE, chessParty.getWhite());
		playerColors.put(PlayerColor.BLACK, chessParty.getBlack());
	}
}
