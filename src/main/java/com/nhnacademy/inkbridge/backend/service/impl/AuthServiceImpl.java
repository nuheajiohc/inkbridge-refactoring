package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.config.ObjectStorageConfig;
import com.nhnacademy.inkbridge.backend.dto.TokenRequest;
import com.nhnacademy.inkbridge.backend.dto.TokenResponse;
import com.nhnacademy.inkbridge.backend.service.AuthService;
import java.util.Objects;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * class: AuthService.
 *
 * @author jeongbyeonghun
 * @version 2/28/24
 */

@Service
public class AuthServiceImpl implements AuthService {

    public AuthServiceImpl(ObjectStorageConfig objectStorageConfig) {
        authUrl = objectStorageConfig.getAuthUrl();
        tenantId = objectStorageConfig.getTenantId();
        username = objectStorageConfig.getUsername();
        password = objectStorageConfig.getPassword();
    }

    private final String authUrl;
    private final String tenantId;
    private final String username;
    private final String password;

    @Override
    public String requestToken() {
        TokenRequest tokenRequest = TokenRequest.builder().tenantId(tenantId).username(username)
            .password(password).build();

        RestTemplate restTemplate = new RestTemplate();
        String identityUrl = authUrl + "/tokens";

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<TokenRequest> httpEntity
            = new HttpEntity<>(tokenRequest, headers);

        // 토큰 요청
        ResponseEntity<TokenResponse> response
            = restTemplate.exchange(identityUrl, HttpMethod.POST, httpEntity, TokenResponse.class);

        return Objects.requireNonNull(response.getBody()).getTokenId();
    }
}
