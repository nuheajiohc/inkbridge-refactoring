package com.nhnacademy.inkbridge.backend.infrastructure.file.nhn;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "nhn.cloud.object-storage")
public class NhnCloudObjectStorageProperties {

	private final String endpoint;

	private final String version;

	private final String auth;

	private final String accessKey;

	private final String secretKey;

	private final String bucketName;

}
