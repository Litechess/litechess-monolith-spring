package com.trymad.litechess_monolith.userOnline.internal.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trymad.litechess_monolith.userOnline.internal.repository.OnlineUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OnlineUserService {
	
	private final OnlineUserRepository repository;
	

	public boolean isOnline(UUID userId) {
		return repository.isOnline(userId);
	}

	public void addOnline(UUID userId) {
		repository.setOnline(userId);
	}

	public void removeOnline(UUID userId) {
		repository.setOffline(userId);
	}
}
