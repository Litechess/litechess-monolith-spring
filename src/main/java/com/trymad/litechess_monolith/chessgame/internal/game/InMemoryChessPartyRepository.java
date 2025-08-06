package com.trymad.litechess_monolith.chessgame.internal.game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.chessgame.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.CreateGameDTO;


@Component
public class InMemoryChessPartyRepository implements ChessPartyRepository {
	private final String DEFAULT_INIT_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	private final Map<Long, ChessParty> store = new ConcurrentHashMap<>();
	private Long idSequence = 1l;

	@Override
	public ChessParty getById(Long id) {
		return store.get(id);
	}

	@Override
	public boolean existsById(Long id) {
		return store.containsKey(id);
	}

	@Override
	public ChessParty update(ChessParty party) {
		return store.put(party.getId(), party);
	}

	@Override
	public ChessParty create(CreateGameDTO createGameDTO) {
		final Long gameId = idSequence++;
		final ChessParty chessParty = ChessParty.builder()
				.id(gameId)
				.white(createGameDTO.white())
				.black(createGameDTO.black())
				.initFen(DEFAULT_INIT_FEN)
				.status(ChessGameStatus.NOT_FINISHED)
				.build();
		store.put(gameId, chessParty);

		return chessParty;
	}
	
}
