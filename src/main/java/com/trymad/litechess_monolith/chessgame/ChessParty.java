package com.trymad.litechess_monolith.chessgame;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ChessParty {
	
	private final Long id;

	private final UUID white;

	private final UUID black;

	private final List<GameMove> moveList;

	private final ChessGameStatus status;

}
