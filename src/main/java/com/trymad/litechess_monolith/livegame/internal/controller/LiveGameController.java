package com.trymad.litechess_monolith.livegame.internal.controller;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trymad.litechess_monolith.livegame.api.dto.LiveGameResponse;
import com.trymad.litechess_monolith.livegame.api.dto.MultipleLiveGameResponse;
import com.trymad.litechess_monolith.livegame.api.dto.ServerNowResponse;
import com.trymad.litechess_monolith.livegame.internal.controller.filter.LiveGameFilter;
import com.trymad.litechess_monolith.livegame.internal.mapper.LiveGameMapper;
import com.trymad.litechess_monolith.livegame.internal.service.LiveGameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/livegames")
@CrossOrigin(origins = "http://localhost:5173")
public class LiveGameController {
	
	private final LiveGameMapper mapper;
	private final LiveGameService liveGameService;

	@GetMapping("/{id}")
	LiveGameResponse getById(@PathVariable Long id) {
		return new LiveGameResponse(mapper.toDto(liveGameService.get(id)), Instant.now().toEpochMilli());
	}

	@GetMapping
	public MultipleLiveGameResponse getAll(
		@RequestParam(required = false) UUID ownerId,
		@RequestParam(required = false) UUID oponentId) {
		final LiveGameFilter filter = new LiveGameFilter(ownerId, oponentId);

		return new MultipleLiveGameResponse(mapper.toDto(liveGameService.get(filter)), Instant.now().toEpochMilli());
	}

	@GetMapping("/serverNow")
	public ServerNowResponse serverNow() {
		return new ServerNowResponse(Instant.now().toEpochMilli());
	}
}
