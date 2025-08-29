package com.trymad.litechess_monolith.chessgame.internal.client;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.users.UserInfoDTO;

public interface UserInfoClient {
	
	UserInfoDTO get(UUID id);

}
