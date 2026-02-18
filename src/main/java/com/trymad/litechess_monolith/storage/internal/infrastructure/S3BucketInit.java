package com.trymad.litechess_monolith.storage.internal.infrastructure;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.storage.internal.config.S3Properties;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

@Component
@RequiredArgsConstructor
public class S3BucketInit {

    private final S3Client s3Client;
    private final S3Properties props;

    @PostConstruct
    public void init() {
        ensureBucketExists(props.bucket());
    }

    private void ensureBucketExists(String bucket) {
        if (!bucketExists(bucket)) {
            s3Client.createBucket(
                CreateBucketRequest.builder()
                    .bucket(bucket)
                    .build()
            );
        }
    }

    private boolean bucketExists(String bucket) {
        try {
            s3Client.headBucket(
                HeadBucketRequest.builder()
                    .bucket(bucket)
                    .build()
            );
            return true;
        } catch (NoSuchBucketException e) {
            return false;
        }
    }
}
