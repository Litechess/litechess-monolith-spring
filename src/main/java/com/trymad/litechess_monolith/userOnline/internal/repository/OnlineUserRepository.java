package com.trymad.litechess_monolith.userOnline.internal.repository;

import java.util.UUID;

public interface OnlineUserRepository {
	
	boolean isOnline(UUID userId);

	void setOnline(UUID userId);

	void setOffline(UUID userId);

}
