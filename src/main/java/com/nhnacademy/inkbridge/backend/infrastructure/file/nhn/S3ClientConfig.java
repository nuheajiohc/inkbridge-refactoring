package com.nhnacademy.inkbridge.backend.infrastructure.file.nhn;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
@RequiredArgsConstructor
public class S3ClientConfig {

	private final NhnCloudObjectStorageProperties properties;

	@Bean
	public S3Client S3Client() {
		return S3Client.builder()
			.endpointOverride(URI.create(properties.getEndpoint()))
			.credentialsProvider(StaticCredentialsProvider.create(
				AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey())))
			.serviceConfiguration(S3Configuration.builder()
				.pathStyleAccessEnabled(true)
				.chunkedEncodingEnabled(false)
				.build())
			.build();
	}

}
