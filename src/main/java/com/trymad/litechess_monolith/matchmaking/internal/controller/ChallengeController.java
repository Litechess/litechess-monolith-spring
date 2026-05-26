package com.trymad.litechess_monolith.matchmaking.internal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trymad.litechess_monolith.matchmaking.api.dto.ChallengeDTO;
import com.trymad.litechess_monolith.matchmaking.api.dto.CreateChallengeDTO;
import com.trymad.litechess_monolith.matchmaking.internal.mapper.ChallengeMapper;
import com.trymad.litechess_monolith.matchmaking.internal.service.ChallengeService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/challenges")
@RequiredArgsConstructor
public class ChallengeController {
	
	private final ChallengeService challengeService;
	private final ChallengeMapper challengeMapper;

	@GetMapping("/{id}")
	public ChallengeDTO getById(@PathVariable String id) {
		System.out.println("TRY GET");
		return challengeMapper.toDto(challengeService.get(id));
	}

	@GetMapping
	public List<ChallengeDTO> getAll() {
		return challengeMapper.toDto(challengeService.getAll());
	}

	@PostMapping
	public ChallengeDTO createChallenge(@RequestBody CreateChallengeDTO dto) {
		System.out.println("TRY CREATE");
		return challengeMapper.toDto(challengeService.createChallenge(dto));
	}

	@PutMapping("/{id}")
	public ChallengeDTO acceptChallenge(@PathVariable String id, UUID acceptedPlayer) {
		System.out.println("TRY ACCEPT");
		return challengeMapper.toDto(challengeService.acceptChallenge(id, acceptedPlayer));
	}
}
