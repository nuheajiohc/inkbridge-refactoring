package com.nhnacademy.inkbridge.backend.infrastructure.old;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "encryption.enabled", havingValue="false", matchIfMissing = true)
public class SimpleKeyReader implements KeyReader {

	@Override
	public String readKey(String key) {
		return key;
	}
}
