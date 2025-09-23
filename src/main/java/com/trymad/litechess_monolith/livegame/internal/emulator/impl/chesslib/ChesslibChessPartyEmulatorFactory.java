package com.trymad.litechess_monolith.livegame.internal.emulator.impl.chesslib;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.livegame.internal.emulator.ChessPartyEmulator;
import com.trymad.litechess_monolith.livegame.internal.emulator.ChessPartyEmulatorFactory;

@Component
@Primary
public class ChesslibChessPartyEmulatorFactory implements ChessPartyEmulatorFactory {

	@Override
	public ChessPartyEmulator create() {
		return new ChesslibPartyEmulator();
	}
	
}
