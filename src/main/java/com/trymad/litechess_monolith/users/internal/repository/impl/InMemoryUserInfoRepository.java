package com.trymad.litechess_monolith.users.internal.repository.impl;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.trymad.litechess_monolith.users.api.dto.UserInfoCreateDTO;
import com.trymad.litechess_monolith.users.internal.model.UserInfo;
import com.trymad.litechess_monolith.users.internal.repository.UserInfoRepository;

@Repository
public class InMemoryUserInfoRepository implements UserInfoRepository {

	private final ConcurrentHashMap<UUID, UserInfo> db = new ConcurrentHashMap<>();

	public InMemoryUserInfoRepository() {
		final UserInfo first = new UserInfo(
			UUID.fromString("8e455873-e706-4b0d-b1f4-a19291fd99d6"),
			"KalorNickname");
		final UserInfo second = new UserInfo(
			UUID.fromString("ecccb4d8-4fad-45c4-b073-e77b4ec1ddbe"),
			"OlegNickname");
		final UserInfo third = new UserInfo(
			UUID.fromString("e46b3462-1636-4666-893b-c108ad816426"),
			"VladNickname");
		db.put(first.getId(), first);
		db.put(second.getId(), second);
		db.put(third.getId(), third);
	}

	@Override
	public Optional<UserInfo> getById(UUID id) {
		return Optional.ofNullable(db.get(id));
	}

	@Override
	public UserInfo save(UserInfoCreateDTO info) {
		final UUID id = UUID.randomUUID();
		final UserInfo infoEntity = new UserInfo(id, info.nickname());
		return db.put(id, infoEntity);
	}
	
}
