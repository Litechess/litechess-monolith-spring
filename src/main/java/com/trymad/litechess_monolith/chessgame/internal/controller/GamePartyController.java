package com.trymad.litechess_monolith.chessgame.internal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.ChessPartyService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class GamePartyController {
	
	private final ChessPartyService chessPartyService;

	@GetMapping("/{id}")
	public ChessParty getChessParty(@PathVariable Long id) {
		System.out.println("PERFORM");
		return chessPartyService.get(id);
	}
	

}
