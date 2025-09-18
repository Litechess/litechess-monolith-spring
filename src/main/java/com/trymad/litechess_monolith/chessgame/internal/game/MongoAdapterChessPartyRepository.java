package com.trymad.litechess_monolith.chessgame.internal.game;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.api.dto.CreatePartyDTO;
import com.trymad.litechess_monolith.chessgame.internal.mongo.SequenceGeneratorService;

import lombok.RequiredArgsConstructor;

@Component
@Primary
@RequiredArgsConstructor
public class MongoAdapterChessPartyRepository implements ChessPartyRepository {

	private final static String DEFAULT_INIT_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

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
	public ChessParty create(CreatePartyDTO createGameDTO) {
		final long id = sequenceGeneratorService.generateSequence("chessparty_seq");
		final ChessParty chessParty = ChessParty.builder()
			.id(id)
			.white(createGameDTO.white())
			.black(createGameDTO.black())
			.initFen(DEFAULT_INIT_FEN)
			.status(ChessGameStatus.NOT_FINISHED)
			.build();
		return mongoChessPartyRepository.save(chessParty);
	}

	@Override
	public ChessParty update(ChessParty party) {
		return mongoChessPartyRepository.save(party);
	}
	
}
