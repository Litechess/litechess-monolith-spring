package com.trymad.litechess_monolith.users.internal.repository;

import java.util.Optional;
import java.util.UUID;

import com.trymad.litechess_monolith.users.api.dto.UserInfoCreateDTO;
import com.trymad.litechess_monolith.users.internal.model.UserInfo;

public interface UserInfoRepository {
	
	Optional<UserInfo> getById(UUID id);

	UserInfo save(UserInfoCreateDTO info);

}
