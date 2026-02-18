package com.trymad.litechess_monolith.users.internal.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.trymad.litechess_monolith.users.api.dto.UserInfoDTO;
import com.trymad.litechess_monolith.users.internal.mapper.UserMapper;
import com.trymad.litechess_monolith.users.internal.model.UserInfo;

@Mapper(componentModel = "spring")
public interface MapstructUserMapper extends UserMapper {
	
	@Override
	@Mapping(target = "avatarUrl", ignore = true)
	UserInfoDTO toDto(UserInfo entity);

	@Override
	@Mapping(target = "avatarKey", ignore = true)
	UserInfo toEntity(UserInfoDTO dto);
}
