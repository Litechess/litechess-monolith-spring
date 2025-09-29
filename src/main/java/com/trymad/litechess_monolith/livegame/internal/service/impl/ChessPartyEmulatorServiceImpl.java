package com.trymad.litechess_monolith.livegame.internal.service.impl;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.livegame.internal.emulator.ChessPartyEmulator;
import com.trymad.litechess_monolith.livegame.internal.emulator.ChessPartyEmulatorFactory;
import com.trymad.litechess_monolith.livegame.internal.repository.ChessPartyEmulatorRepository;
import com.trymad.litechess_monolith.livegame.internal.service.ChessPartyEmulatorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class ChessPartyEmulatorServiceImpl implements ChessPartyEmulatorService {

	final ChessPartyEmulatorRepository chessPartyEmulatorRepository;
	final ChessPartyEmulatorFactory factory;

	@Override
	public ChessPartyEmulator getEmulator(Long gameId) {
		return chessPartyEmulatorRepository.findById(gameId).orElseThrow();
	}

	@Override
	public ChessPartyEmulator createEmulator(Long gameId) {
		final ChessPartyEmulator emulator = factory.create();
		return chessPartyEmulatorRepository.save(gameId, emulator);
	}

	@Override
	public void deleteEmulator(Long gameId) {
		chessPartyEmulatorRepository.delete(gameId);
	}
}
