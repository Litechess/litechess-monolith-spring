package com.trymad.litechess_monolith.chessgame.internal.game;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trymad.litechess_monolith.chessgame.ChessParty;

public interface MongoChessPartyRepository extends MongoRepository<ChessParty, Long> {
	
}
