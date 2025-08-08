package com.trymad.litechess_monolith;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.trymad.litechess_monolith.chessgame.ChessPartyService;
import com.trymad.litechess_monolith.chessgame.CreatePartyDTO;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class LitechessMonolithApplication implements CommandLineRunner {
 
	private final ChessPartyService chessPartyService;
	public static void main(String[] args) {
		SpringApplication.run(LitechessMonolithApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		final UUID white = UUID.fromString("ecccb4d8-4fad-45c4-b073-e77b4ec1ddbe");
		final UUID black = UUID.fromString("8e455873-e706-4b0d-b1f4-a19291fd99d6");
		chessPartyService.create(new CreatePartyDTO(white, black));
	}

}
