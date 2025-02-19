package com.nhnacademy.inkbridge.backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: ObjectService.
 *
 * @author jeongbyeonghun
 * @version 2/28/24
 */
public interface ObjectService {

    ResponseEntity<byte[]> downloadObject(String objectName);

    String uploadObject(MultipartFile multipartFile);
}
