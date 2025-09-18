package com.trymad.litechess_monolith.chessgame.internal.client;

import java.util.UUID;

import com.trymad.litechess_monolith.users.UserInfoDTO;

public interface UserInfoClient {
	
	UserInfoDTO get(UUID id);

}
