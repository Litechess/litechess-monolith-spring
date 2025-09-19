package com.trymad.litechess_monolith.chessgame.internal.emulator.impl.wolfram;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.internal.emulator.ChessPartyEmulator;
import com.trymad.litechess_monolith.chessgame.internal.emulator.ChessPartyEmulatorFactory;
import com.trymad.litechess_monolith.chessgame.internal.model.ChessParty;

@Component
public class WolframChessPartyEmulatorFactory implements ChessPartyEmulatorFactory {

	@Override
	public ChessPartyEmulator create(ChessParty chessParty) {
		return new WolframChessPartyEmulator(chessParty.getMoveList());
	}
	
}
