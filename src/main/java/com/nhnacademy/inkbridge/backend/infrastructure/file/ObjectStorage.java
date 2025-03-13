package com.nhnacademy.inkbridge.backend.infrastructure.file;

import org.springframework.web.multipart.MultipartFile;

public interface ObjectStorage {
	String uploadObject(MultipartFile file);

	void deleteObject(String objectKey);
}

