package com.nhnacademy.inkbridge.backend.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * * 인증 토큰 요청을 위한 데이터 전송 객체(DTO)입니다. * 인증 시 필요한 사용자 인증 정보를 포함하여 인증 서버에 전달하는 데 사용됩니다. class:
 * TokenRequest.
 *
 * @author jeongbyeonghun
 * @version 2/28/24
 */
@Getter
public class TokenRequest {

    private final Auth auth;

    /**
     * 인증 정보를 포함하는 내부 정적 클래스입니다. 테넌트 ID와 패스워드 자격 증명 정보를 포함합니다.
     */
    @Getter
    public static class Auth {

        private final String tenantId;
        private final PasswordCredentials passwordCredentials;

        @Builder
        public Auth(String tenantId, PasswordCredentials passwordCredentials) {
            this.tenantId = tenantId;
            this.passwordCredentials = passwordCredentials;
        }
    }

    /**
     * 사용자의 패스워드 자격 증명을 포함하는 내부 정적 클래스입니다. 사용자 이름과 패스워드를 포함합니다.
     */
    @Getter
    public static class PasswordCredentials {

        private final String username;
        private final String password;

        @Builder
        public PasswordCredentials(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    /**
     * 인증 토큰 요청 객체를 생성합니다.
     *
     * @param tenantId 사용자가 속한 테넌트의 ID
     * @param username 사용자 이름
     * @param password 사용자의 패스워드
     */
    @Builder
    public TokenRequest(String tenantId, String username, String password) {
        PasswordCredentials passwordCredentials = PasswordCredentials.builder().username(username)
            .password(password).build();
        this.auth = Auth.builder().tenantId(tenantId).passwordCredentials(passwordCredentials)
            .build();
    }
}