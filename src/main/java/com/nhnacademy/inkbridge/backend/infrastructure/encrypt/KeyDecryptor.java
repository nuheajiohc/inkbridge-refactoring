package com.nhnacademy.inkbridge.backend.infrastructure.encrypt;

public interface KeyDecryptor {

	String decrypt(String encryptedKey);
}
