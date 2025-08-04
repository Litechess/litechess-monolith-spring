package com.trymad.litechess_monolith.chessgame.internal.model;

import java.util.Map;
import java.util.UUID;

import com.trymad.litechess_monolith.chessgame.internal.game.ChessPartyEmulator;

public record LiveGame(Map<PlayerColor, UUID> players, ChessPartyEmulator emulator) {
	
}
