package com.trymad.litechess_monolith.chessgame.internal.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

import com.trymad.litechess_monolith.chessgame.api.model.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.api.model.GameMove;
import com.trymad.litechess_monolith.chessgame.internal.emulator.ChessPartyEmulator;

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
		chessParty.getMoves().clear();
		chessParty.getMoves().addAll(emulator.getMoveList());
		chessParty.setCurrentFen(emulator.getFen());
		return chessParty;
	}

	public ChessGameStatus getStatus() {
		return emulator.gameStatus();
	}

	private void setPlayers(ChessParty chessParty) {
		playerColors.put(PlayerColor.WHITE, chessParty.getWhite().id());
		playerColors.put(PlayerColor.BLACK, chessParty.getBlack().id());
	}
}
