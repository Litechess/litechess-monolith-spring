package com.trymad.litechess_monolith.users.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserInfoDTO(UUID id, String nickname, LocalDateTime createdAt) {
	
}
