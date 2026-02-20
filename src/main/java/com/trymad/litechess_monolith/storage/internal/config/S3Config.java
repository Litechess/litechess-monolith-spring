package com.trymad.litechess_monolith.storage.internal.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {
	
	@Bean
	S3Client internalS3Client(S3Properties properties) {
		return S3Client.builder()
			.endpointOverride(URI.create(properties.url()))
			.region(Region.of(properties.region()))
			.credentialsProvider(StaticCredentialsProvider.create(
				AwsBasicCredentials.create(
					properties.accessKey(),
					properties.secretKey()
				)
			))
			.serviceConfiguration(
					S3Configuration.builder()
							.pathStyleAccessEnabled(true)
							.build()
			)
			.build();
	}

    @Bean
    public S3Presigner s3Presigner(S3Properties properties) {
        return S3Presigner.builder()
                .endpointOverride(URI.create(properties.url()))
                .region(Region.of(properties.region()))
				.serviceConfiguration(              
						S3Configuration.builder()
								.pathStyleAccessEnabled(true)
								.build()
				)
				.credentialsProvider(StaticCredentialsProvider.create(
					AwsBasicCredentials.create(
						properties.accessKey(),
						properties.secretKey()
					)
				))
                .build();
    }
}
