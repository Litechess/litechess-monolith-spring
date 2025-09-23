package com.trymad.litechess_monolith.chessparty.internal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trymad.litechess_monolith.chessparty.api.dto.ChessPartyDTO;
import com.trymad.litechess_monolith.chessparty.internal.controller.filter.ChessPartyFilter;
import com.trymad.litechess_monolith.chessparty.internal.mapper.ChessPartyMapper;
import com.trymad.litechess_monolith.chessparty.internal.service.ChessPartyService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class GamePartyController {
	
	private final ChessPartyService chessPartyService;
	private final ChessPartyMapper mapper;

	@GetMapping("/{id}")
	public ChessPartyDTO getChessParty(@PathVariable Long id) {
		System.out.println("PERFORM");
		return mapper.toDto(chessPartyService.get(id));
	}


	// TODO pagination
	@GetMapping
	public List<ChessPartyDTO> getAll(
		@RequestParam(required = false) UUID ownerId,
		@RequestParam(required = false) UUID oponentId) {
		final ChessPartyFilter filter = new ChessPartyFilter(ownerId, oponentId);

		return mapper.toDto(chessPartyService.get(filter));
	}
}
