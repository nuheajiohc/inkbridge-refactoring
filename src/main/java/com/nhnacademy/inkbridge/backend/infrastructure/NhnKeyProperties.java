package com.nhnacademy.inkbridge.backend.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NhnKeyProperties {
	private final String appKey;
	private final String password;
	private final String url;
	private final String path;
}
