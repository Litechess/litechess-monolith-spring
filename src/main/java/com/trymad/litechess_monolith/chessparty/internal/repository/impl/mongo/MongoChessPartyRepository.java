package com.trymad.litechess_monolith.chessparty.internal.repository.impl.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trymad.litechess_monolith.chessparty.internal.model.ChessParty;

public interface MongoChessPartyRepository extends MongoRepository<ChessParty, String> {
	
}
