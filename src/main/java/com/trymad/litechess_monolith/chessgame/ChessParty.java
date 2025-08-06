package com.trymad.litechess_monolith.chessgame;

import java.util.LinkedList;
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
	
	private Long id;

	private UUID white;

	private UUID black;

	private final List<GameMove> moveList = new LinkedList<>();

	private String initFen;

	private ChessGameStatus status;

}
