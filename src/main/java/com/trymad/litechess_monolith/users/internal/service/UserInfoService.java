package com.trymad.litechess_monolith.users.internal.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trymad.litechess_monolith.users.api.dto.UserInfoCreateDTO;
import com.trymad.litechess_monolith.users.internal.model.UserInfo;
import com.trymad.litechess_monolith.users.internal.repository.JpaUserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoService {

	private final JpaUserInfoRepository repository;

	public UserInfo get(UUID id) {
		return repository.findById(id).orElseThrow( () -> new RuntimeException("User " + id + " not found"));
	}


	// check if user with same id in token
	public UserInfo create(UserInfoCreateDTO createDto) {
		final UserInfo userInfo = new UserInfo(createDto.id(), createDto.nickname(), LocalDateTime.now());
		return repository.save(userInfo);
	}
	
}
