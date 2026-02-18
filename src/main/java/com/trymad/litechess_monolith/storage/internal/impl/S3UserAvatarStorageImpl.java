package com.trymad.litechess_monolith.storage.internal.impl;

import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.storage.internal.config.S3Properties;
import com.trymad.litechess_monolith.users.api.storage.UserAvatarStorage;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class S3UserAvatarStorageImpl implements UserAvatarStorage {

    private final S3Client s3Client;
	private final S3Properties props;

    @Override
    public String storeAvatar(String userId, InputStream content, long contentLength) {

        String key = buildKey(userId);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(props.bucket())
                .key(key)
                .contentType("image/jpeg") 
                .contentLength(contentLength)
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(content, contentLength));

        return key; 
    }

    @Override
    public void deleteAvatar(String userId) {

        String key = buildKey(userId);

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(props.bucket())
                .key(key)
                .build();

        s3Client.deleteObject(request);
    }

    private String buildKey(String userId) {
        return "users/%s/avatar".formatted(userId);
    }
}