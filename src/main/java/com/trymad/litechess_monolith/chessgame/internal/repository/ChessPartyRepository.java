package com.trymad.litechess_monolith.chessgame.internal.repository;

import java.util.List;
import java.util.Optional;

import com.trymad.litechess_monolith.chessgame.api.dto.CreatePartyDTO;
import com.trymad.litechess_monolith.chessgame.internal.model.ChessParty;

public interface ChessPartyRepository {
	
	Optional<ChessParty> getById(Long id);

	List<ChessParty> getAll();

	boolean existsById(Long id);

	ChessParty create(CreatePartyDTO createGameDTO);

	ChessParty update(ChessParty party);

}
