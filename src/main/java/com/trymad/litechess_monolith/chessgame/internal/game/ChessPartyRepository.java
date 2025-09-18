package com.trymad.litechess_monolith.chessgame.internal.game;

import java.util.List;
import java.util.Optional;

import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.api.dto.CreatePartyDTO;

public interface ChessPartyRepository {
	
	Optional<ChessParty> getById(Long id);

	List<ChessParty> getAll();

	boolean existsById(Long id);

	ChessParty create(CreatePartyDTO createGameDTO);

	ChessParty update(ChessParty party);

}
