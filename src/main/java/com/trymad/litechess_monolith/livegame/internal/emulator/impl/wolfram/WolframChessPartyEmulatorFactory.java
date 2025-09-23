package com.trymad.litechess_monolith.livegame.internal.emulator.impl.wolfram;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.livegame.internal.emulator.ChessPartyEmulator;
import com.trymad.litechess_monolith.livegame.internal.emulator.ChessPartyEmulatorFactory;

@Component
public class WolframChessPartyEmulatorFactory implements ChessPartyEmulatorFactory {

	@Override
	public ChessPartyEmulator create() {
		return new WolframChessPartyEmulator();
	}
	
}
