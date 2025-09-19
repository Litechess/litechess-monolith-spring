package com.trymad.litechess_monolith.users.internal.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trymad.litechess_monolith.users.api.dto.UserInfoCreateDTO;
import com.trymad.litechess_monolith.users.internal.model.UserInfo;
import com.trymad.litechess_monolith.users.internal.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoService {

	private final UserInfoRepository userRepository;

	public UserInfo get(UUID id) {
		return userRepository.getById(id).orElseThrow( () -> new RuntimeException("User " + id + " not found"));
	}

	public UserInfo create(UserInfoCreateDTO createDto) {
		return userRepository.save(createDto);
	}
	
}
