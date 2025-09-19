package com.trymad.litechess_monolith.chessgame.internal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trymad.litechess_monolith.chessgame.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.chessgame.internal.service.ChessPartyService;

import lombok.RequiredArgsConstructor;

import java.util.List;

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
	public ChessPartyDTO getChessParty(@PathVariable Long id) {
		System.out.println("PERFORM");
		return chessPartyService.getDto(chessPartyService.get(id));
	}


	// TODO pagination + add mapper
	@GetMapping("/shortParty")
	public List<ChessPartyDTO> getAll(@RequestParam(name = "activeGames", defaultValue = "true") boolean activeGames) {
		return chessPartyService.getAll(activeGames).stream().map(chessPartyService::getDto).toList();
	}
}
