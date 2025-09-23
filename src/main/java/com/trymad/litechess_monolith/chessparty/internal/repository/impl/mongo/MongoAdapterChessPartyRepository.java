package com.trymad.litechess_monolith.chessparty.internal.repository.impl.mongo;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessparty.internal.model.ChessParty;
import com.trymad.litechess_monolith.chessparty.internal.repository.ChessPartyRepository;

import lombok.RequiredArgsConstructor;

@Component
@Primary
@RequiredArgsConstructor

public class MongoAdapterChessPartyRepository implements ChessPartyRepository {

	private final MongoChessPartyRepository mongoChessPartyRepository;
	private final SequenceGeneratorService sequenceGeneratorService;

	@Override
	public Optional<ChessParty> getById(Long id) {
		return mongoChessPartyRepository.findById(id);
	}

	@Override
	public List<ChessParty> getAll() {
		return mongoChessPartyRepository.findAll();
	}

	@Override
	public boolean existsById(Long id) {
		return mongoChessPartyRepository.existsById(id);
	}

	@Override
	// check if exists 
	public ChessParty save(ChessParty chessParty) {
		if(chessParty.getId() == null || chessParty.getId() < 0) {
			final long id = sequenceGeneratorService.generateSequence("chessparty_seq");
			chessParty.setId(id);
		}

		return mongoChessPartyRepository.save(chessParty);
	}
	
}
