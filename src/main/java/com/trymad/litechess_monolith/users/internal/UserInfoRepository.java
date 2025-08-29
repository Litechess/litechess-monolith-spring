package com.trymad.litechess_monolith.users.internal;

import java.util.Optional;
import java.util.UUID;

public interface UserInfoRepository {
	
	Optional<UserInfo> getById(UUID id);

	UserInfo save(UserInfoCreateDTO info);

}
