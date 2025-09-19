package com.trymad.litechess_monolith.chessgame.internal.repository.impl.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trymad.litechess_monolith.chessgame.internal.model.ChessParty;

public interface MongoChessPartyRepository extends MongoRepository<ChessParty, Long> {
	
}
