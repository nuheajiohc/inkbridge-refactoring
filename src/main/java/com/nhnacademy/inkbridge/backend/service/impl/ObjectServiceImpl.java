package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.config.ObjectStorageConfig;
import com.nhnacademy.inkbridge.backend.enums.FileMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.AuthService;
import com.nhnacademy.inkbridge.backend.service.ObjectService;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: ObjectService.
 *
 * @author jeongbyeonghun
 * @version 2/27/24
 */

@Service
public class ObjectServiceImpl implements ObjectService {


    private final AuthService authService;

    public ObjectServiceImpl(ObjectStorageConfig objectStorageConfig, AuthService authService) {
        this.authService = authService;
        storageUrl = objectStorageConfig.getStorageUrl();
        containerName = objectStorageConfig.getContainerName();
    }

    private String tokenId;
    private final String storageUrl;
    private final String containerName;


    @Override
    public ResponseEntity<byte[]> downloadObject(String objectName) {
        setTokenId();
        RestTemplate restTemplate = new RestTemplate();

        String url = this.getUrl(objectName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", tokenId);
        headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(null, headers);
        return restTemplate.exchange(url, HttpMethod.GET, requestHttpEntity, byte[].class);
    }

    @Override
    public String uploadObject(MultipartFile multipartFile) {
        setTokenId();
        String fileName = (LocalDateTime.now() + multipartFile.getOriginalFilename()).trim();
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new ValidationException(FileMessageEnum.FILE_VALID_FAIL.getMessage());
        }
        String url = this.getUrl(fileName);
        final RequestCallback requestCallback = request -> {
            request.getHeaders().add("X-Auth-Token", tokenId);
            IOUtils.copy(inputStream, request.getBody());
        };

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpMessageConverterExtractor<String> responseExtractor
            = new HttpMessageConverterExtractor<>(String.class,
            restTemplate.getMessageConverters());
        restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);

        return fileName;
    }

    /**
     * 새토큰을 가져와 세팅하는 메소드.
     */
    void setTokenId() {
        tokenId = authService.requestToken();
    }

    private String getUrl(String objectName) {
        return storageUrl + "/" + containerName + "/" + objectName;
    }
}
