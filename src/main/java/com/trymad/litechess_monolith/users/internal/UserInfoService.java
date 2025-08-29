package com.trymad.litechess_monolith.users.internal;

import java.util.UUID;

import org.springframework.stereotype.Service;

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
