package com.trymad.litechess_monolith.matchmaking.internal.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebChessPartyClient implements ChessPartyClient {
		// TODO change to env url
	private final WebClient client = WebClient.create("http://localhost:8080");

	@Override
	public String getUniqueId() {
		return client.get()
			.uri("/api/v1/games/uniqueId")
			.retrieve()
			.bodyToMono(String.class)
			.block();
	}

}
