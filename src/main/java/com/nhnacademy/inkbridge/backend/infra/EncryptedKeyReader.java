package com.nhnacademy.inkbridge.backend.infra;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "encryption.enabled", havingValue = "true")
public class EncryptedKeyReader implements KeyReader {

	private final KeyDecryptor keyDecryptor;

	@Override
	public String readKey(String key) {
		return keyDecryptor.decrypt(key);
	}
}
