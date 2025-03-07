package com.nhnacademy.inkbridge.backend.infrastructure;

import com.nhnacademy.inkbridge.backend.dto.keymanager.KeyResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class NhnKeyResponse {
	private KeyResponseDto.Header header;
	private KeyResponseDto.Body body;

	@Getter
	@NoArgsConstructor
	public static class Body {
		private String secret;
	}

	@Getter
	@NoArgsConstructor
	public static class Header {
		private Integer resultCode;
		private String resultMessage;
		private boolean isSuccessful;
	}
}
