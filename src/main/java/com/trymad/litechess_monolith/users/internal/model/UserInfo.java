package com.trymad.litechess_monolith.users.internal.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class UserInfo {
	
	@Id
	private UUID id;

	@Column(unique = true, nullable = false)
	private String nickname;

	@Column(nullable = true)
	private String avatarKey; 

	@Column(nullable = false)
	private LocalDateTime createdAt;

}
