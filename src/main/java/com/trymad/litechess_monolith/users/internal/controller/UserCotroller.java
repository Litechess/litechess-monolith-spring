package com.trymad.litechess_monolith.users.internal.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trymad.litechess_monolith.users.api.dto.UserInfoCreateDTO;
import com.trymad.litechess_monolith.users.api.dto.UserInfoDTO;
import com.trymad.litechess_monolith.users.internal.service.UserInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserCotroller {

	private final UserInfoService userInfoService;

	@GetMapping("/{id}")
	public UserInfoDTO getById(@PathVariable("id") UUID id) {
		return userInfoService.getDto(id);
	}

	@PostMapping
	public UserInfoDTO createUser(@RequestBody UserInfoCreateDTO userInfoDto) {
		return userInfoService.getDto(userInfoService.create(userInfoDto));
	}

}
