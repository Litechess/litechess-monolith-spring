package com.trymad.litechess_monolith.chessparty.internal.repository;

import java.util.List;
import java.util.Optional;

import com.trymad.litechess_monolith.chessparty.internal.model.ChessParty;

public interface ChessPartyRepository {
	
	Optional<ChessParty> getById(String id);

	List<ChessParty> getAll();

	boolean existsById(String id);

	ChessParty save(ChessParty chessParty);

	void delete(String id);

	void deleteAll(List<ChessParty> parties);

}
