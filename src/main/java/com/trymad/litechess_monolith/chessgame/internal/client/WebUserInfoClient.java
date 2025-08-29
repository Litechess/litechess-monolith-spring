package com.trymad.litechess_monolith.chessgame.internal.client;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.trymad.litechess_monolith.users.UserInfoDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class WebUserInfoClient implements UserInfoClient {

	private final WebClient client = WebClient.create("http://localhost:8080");

	@Override
	public UserInfoDTO get(UUID uuid) {
		return client.get()
			.uri("/api/v1/users/{uuid}", uuid.toString())
			.retrieve()
			.bodyToMono(UserInfoDTO.class)
			.block();
	}
	
}
