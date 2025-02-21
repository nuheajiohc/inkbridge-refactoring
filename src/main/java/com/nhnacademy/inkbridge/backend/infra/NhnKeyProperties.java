package com.nhnacademy.inkbridge.backend.infra;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("secure-key-manager")
public class NhnKeyProperties {

	private final String appKey;
	private final String password;
	private final String url;
	private final String path;

}
