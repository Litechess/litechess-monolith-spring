package com.trymad.litechess_monolith.chessgame.internal.game.emulator;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.ChessParty;

@Component
@Primary
public class ChesslibChessPartyEmulatorFactory implements ChessPartyEmulatorFactory {

	@Override
	public ChessPartyEmulator create(ChessParty chessParty) {
		return new ChesslibPartyEmulator(chessParty.getMoveList());
	}
	
}
