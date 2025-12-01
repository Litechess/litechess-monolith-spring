package com.trymad.litechess_monolith.users.internal.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trymad.litechess_monolith.users.api.dto.UserInfoCreateDTO;
import com.trymad.litechess_monolith.users.api.dto.UserInfoDTO;
import com.trymad.litechess_monolith.users.internal.mapper.UserMapper;
import com.trymad.litechess_monolith.users.internal.model.UserInfo;
import com.trymad.litechess_monolith.users.internal.service.UserInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserCotroller {

	private final UserInfoService userInfoService;
	private final UserMapper mapper;

	@GetMapping("/{id}")
	public UserInfoDTO getById(@PathVariable("id") UUID id) {
		final UserInfo info = userInfoService.get(id);
		return mapper.toDto(info);
	}

	@PostMapping
	public UserInfoDTO createUser(@RequestBody UserInfoCreateDTO userInfoDto) {
		return mapper.toDto(userInfoService.create(userInfoDto));
	}

}
