package com.trymad.litechess_monolith.users.internal.controller;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.trymad.litechess_monolith.users.internal.service.UserInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/users/{userId}/avatar")

@RequiredArgsConstructor
public class AvatarController {

    private final UserInfoService userInfoService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upload(
            @PathVariable UUID userId,
            @RequestParam MultipartFile file
    ) {
        userInfoService.uploadAvatar(userId, file);
    }

    @DeleteMapping
    public void delete(@PathVariable UUID userId) {
        userInfoService.removeAvatar(userId);
    }
}
