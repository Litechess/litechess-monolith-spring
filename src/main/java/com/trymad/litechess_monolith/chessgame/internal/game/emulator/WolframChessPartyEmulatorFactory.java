package com.trymad.litechess_monolith.chessgame.internal.game.emulator;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.ChessParty;

@Component
public class WolframChessPartyEmulatorFactory implements ChessPartyEmulatorFactory {

	@Override
	public ChessPartyEmulator create(ChessParty chessParty) {
		return new WolframChessPartyEmulator(chessParty.getMoveList());
	}
	
}
