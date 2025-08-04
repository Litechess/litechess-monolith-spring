package com.trymad.litechess_monolith.chessgame;

public interface ChessPartyRepository {
	
	ChessParty getById(Long id);

	boolean existsById(Long id);

	ChessParty save(ChessParty party);

}
