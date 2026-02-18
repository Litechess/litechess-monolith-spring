package com.trymad.litechess_monolith.users.internal.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.trymad.litechess_monolith.users.api.dto.UserInfoCreateDTO;
import com.trymad.litechess_monolith.users.api.storage.UserAvatarStorage;
import com.trymad.litechess_monolith.users.internal.model.UserInfo;
import com.trymad.litechess_monolith.users.internal.repository.JpaUserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoService {

	private final JpaUserInfoRepository repository;
	private final UserAvatarStorage avatarStorage;

	@Transactional
	public UserInfo get(UUID id) {
		return repository.findById(id).orElseThrow( () -> new RuntimeException("User " + id + " not found"));
	}

	
	// TODO check if user with same id in token
	@Transactional
	public UserInfo create(UserInfoCreateDTO createDto) {
		final UserInfo userInfo = new UserInfo(createDto.id(), createDto.nickname(), null, LocalDateTime.now());
		return repository.save(userInfo);
	}
	
    @Transactional
    public void uploadAvatar(UUID userId, MultipartFile file) {

        UserInfo user = get(userId);

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Avatar file is empty");
        }

        try (InputStream is = file.getInputStream()) {

            if (user.getAvatarKey() != null) {
                avatarStorage.deleteAvatar(userId.toString());
            }

            String key = avatarStorage.storeAvatar(
                    userId.toString(),
                    is,
                    file.getSize()
            );

            user.setAvatarKey(key);
            repository.save(user);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload avatar", e);
        }
    }

    @Transactional
    public void removeAvatar(UUID userId) {

        UserInfo user = get(userId);

        if (user.getAvatarKey() != null) {
            avatarStorage.deleteAvatar(userId.toString());
            user.setAvatarKey(null);
            repository.save(user);
        }
    }
}
