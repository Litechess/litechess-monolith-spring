package com.trymad.litechess_monolith.livegame.internal.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trymad.litechess_monolith.livegame.api.dto.LiveGameDTO;
import com.trymad.litechess_monolith.livegame.internal.controller.filter.LiveGameFilter;
import com.trymad.litechess_monolith.livegame.internal.mapper.LiveGameDtoMapper;
import com.trymad.litechess_monolith.livegame.internal.service.LiveGameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/livegames")
public class LiveGameController {
	
	private final LiveGameDtoMapper mapper;
	private final LiveGameService liveGameService;

	@GetMapping("/{id}")
	LiveGameDTO getById(Long id) {
		return mapper.toDto(liveGameService.get(id));
	}

	@GetMapping
	public List<LiveGameDTO> getAll(
		@RequestParam(required = false) UUID ownerId,
		@RequestParam(required = false) UUID oponentId) {
		final LiveGameFilter filter = new LiveGameFilter(ownerId, oponentId);

		return mapper.toDto(liveGameService.get(filter));
	}
}
