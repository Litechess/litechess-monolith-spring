package com.trymad.litechess_monolith.users.internal.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trymad.litechess_monolith.users.internal.model.UserInfo;

public interface JpaUserInfoRepository extends JpaRepository<UserInfo, UUID> {
	
}
