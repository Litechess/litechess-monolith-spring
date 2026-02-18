package com.trymad.litechess_monolith.storage.internal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "s3")
public record S3Properties(
	String endpoint,
	String accessKey,
	String secretKey,
	String region,
	String bucket
) {}
