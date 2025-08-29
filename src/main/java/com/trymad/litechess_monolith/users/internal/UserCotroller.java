package com.trymad.litechess_monolith.users.internal;

import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trymad.litechess_monolith.users.UserInfoDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserCotroller {
	// TODO add mapper
	private final UserInfoService userInfoService;

	@GetMapping("/{id}")
	public UserInfoDTO getById(@PathVariable("id") UUID id) {
		final UserInfo info = userInfoService.get(id);
		return new UserInfoDTO(info.getId(), info.getNickname());
	}

}
