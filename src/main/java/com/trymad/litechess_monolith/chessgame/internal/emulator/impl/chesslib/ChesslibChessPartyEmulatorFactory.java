package com.trymad.litechess_monolith.chessgame.internal.emulator.impl.chesslib;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.internal.emulator.ChessPartyEmulator;
import com.trymad.litechess_monolith.chessgame.internal.emulator.ChessPartyEmulatorFactory;
import com.trymad.litechess_monolith.chessgame.internal.model.ChessParty;

@Component
@Primary
public class ChesslibChessPartyEmulatorFactory implements ChessPartyEmulatorFactory {

	@Override
	public ChessPartyEmulator create(ChessParty chessParty) {
		return new ChesslibPartyEmulator(chessParty.getMoveList());
	}
	
}
