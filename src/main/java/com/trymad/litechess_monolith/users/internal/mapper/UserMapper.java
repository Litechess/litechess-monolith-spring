package com.trymad.litechess_monolith.users.internal.mapper;

import com.trymad.litechess_monolith.infrastructure.mapper.BidirectionalMapper;
import com.trymad.litechess_monolith.users.api.dto.UserInfoDTO;
import com.trymad.litechess_monolith.users.internal.model.UserInfo;

public interface UserMapper extends BidirectionalMapper<UserInfo, UserInfoDTO> {
	
}
