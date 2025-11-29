package com.trymad.litechess_monolith.userOnline.internal.controller;

import org.springframework.web.bind.annotation.RestController;

import com.trymad.litechess_monolith.userOnline.api.dto.OnlineResponse;
import com.trymad.litechess_monolith.userOnline.internal.service.OnlineUserService;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/online")
@CrossOrigin(origins = "http://localhost:5173")
public class OnlineUserController {
	
	private final OnlineUserService onlineUserService;

	@GetMapping("/{id}")
	public OnlineResponse isOnline(@PathVariable UUID id) {
		return new OnlineResponse(onlineUserService.isOnline(id));
	}
}
