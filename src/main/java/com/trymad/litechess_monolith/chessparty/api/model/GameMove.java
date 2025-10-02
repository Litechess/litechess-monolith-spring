package com.trymad.litechess_monolith.chessparty.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// san notation


// DTO 
@Getter
@Setter
@AllArgsConstructor
public class GameMove{

	private final String from;
	private final String to;
	private final ChessPiece promotion; 
	private final String san; 
	private Integer plyNumber; 
	
}
