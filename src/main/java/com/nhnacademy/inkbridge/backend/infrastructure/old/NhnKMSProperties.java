package com.nhnacademy.inkbridge.backend.infrastructure.old;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("secure-key-manager")
public class NhnKMSProperties {

	private final String appKey;
	private final String password;
	private final String url;
	private final String path;

}
