package com.trymad.litechess_monolith.users.internal.mapper;

import com.trymad.litechess_monolith.shared.mapper.BidirectionalMapper;
import com.trymad.litechess_monolith.users.api.dto.UserInfoDTO;
import com.trymad.litechess_monolith.users.internal.model.UserInfo;

public interface UserMapper extends BidirectionalMapper<UserInfo, UserInfoDTO> {
	
}
