package com.nhnacademy.inkbridge.backend.infrastructure.file.nhn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.inkbridge.backend.infrastructure.file.ObjectStorage;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class NhnCloudObjectStorage implements ObjectStorage {

	private final S3Client client;
	private final NhnCloudObjectStorageProperties properties;

	@Override
	public String uploadObject(MultipartFile file) {
		String uniqueFileName = generateUniqueName();
		String contentType = getContentType(file);
		uploadFileToObjectStorage(uniqueFileName, contentType, file);

		return resolveObjectAccessUrl(uniqueFileName);
	}

	@Override
	public void deleteObject(String objectKey) {
		DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
			.bucket(properties.getBucketName())
			.key(objectKey)
			.build();

		client.deleteObject(deleteRequest);
	}

	private String generateUniqueName() {
		return UUID.randomUUID().toString();
	}

	private String getContentType(MultipartFile file) {
		try {
			return Files.probeContentType(Paths.get(file.getOriginalFilename()));
		} catch (IOException e) {
			throw new RuntimeException("contentType 감지 실패", e);
		}
	}

	private void uploadFileToObjectStorage(String fileName, String contentType, MultipartFile file) {
		try {
			client.putObject(PutObjectRequest.builder()
					.bucket(properties.getBucketName())
					.key(fileName)
					.contentType(contentType != null ? contentType : "application/octet-stream") // ✅ Content-Type 설정
					.build(),
				RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
		} catch (IOException e) {
			throw new RuntimeException("파일 업로드 실패", e);
		}
	}

	private String resolveObjectAccessUrl(String fileName) {
		return String.join("/",
			properties.getEndpoint(),
			properties.getVersion(),
			properties.getAuth(),
			properties.getBucketName(),
			fileName);
	}

}
