package com.trymad.litechess_monolith;

import java.util.LinkedList;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.trymad.litechess_monolith.chessgame.ChessGameStatus;
import com.trymad.litechess_monolith.chessgame.ChessParty;
import com.trymad.litechess_monolith.chessgame.ChessPartyRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class LitechessMonolithApplication implements CommandLineRunner {
 
	private final ChessPartyRepository chessPartyRepository;
	public static void main(String[] args) {
		SpringApplication.run(LitechessMonolithApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		final ChessParty chessParty = ChessParty.builder()
			.id(123l)
			.white(UUID.fromString("ecccb4d8-4fad-45c4-b073-e77b4ec1ddbe"))
			.black(UUID.fromString("8e455873-e706-4b0d-b1f4-a19291fd99d6"))
			.moveList(new LinkedList<>())
			.status(ChessGameStatus.NOT_FINISHED)
			.build();
		chessPartyRepository.save(chessParty);
	}

}
