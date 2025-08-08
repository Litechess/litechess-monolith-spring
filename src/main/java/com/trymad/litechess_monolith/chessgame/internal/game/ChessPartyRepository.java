package com.trymad.litechess_monolith.chessgame.internal.game;

import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.CreatePartyDTO;

public interface ChessPartyRepository {
	
	ChessParty getById(Long id);

	boolean existsById(Long id);

	ChessParty create(CreatePartyDTO createGameDTO);

	ChessParty update(ChessParty party);

}
