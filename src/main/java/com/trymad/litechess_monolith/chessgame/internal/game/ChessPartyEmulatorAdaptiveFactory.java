package com.trymad.litechess_monolith.chessgame.internal.game;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.ChessParty;

@Component
public class ChessPartyEmulatorAdaptiveFactory implements ChessPartyEmulatorFactory {

	@Override
	public ChessPartyEmulator create(ChessParty chessParty) {
		return new ChessPartyEmulatorAdaptor(chessParty.getMoveList());
	}
	
}
