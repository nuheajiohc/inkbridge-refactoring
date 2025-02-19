package com.nhnacademy.inkbridge.backend.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: TokenResponse.
 *
 * @author jeongbyeonghun
 * @version 2/28/24
 */
@Setter
@NoArgsConstructor
public class TokenResponse {

    private Access access;


    /**
     * 인증 접근 정보를 포함하는 내부 정적 클래스입니다. 인증 토큰 정보를 포함합니다.
     */
    @Setter
    @NoArgsConstructor
    public static class Access {

        private Token token;

        @Builder
        public Access(Token token) {
            this.token = token;
        }

        /**
         * 인증 토큰 정보를 담고 있는 내부 정적 클래스입니다. 토큰 ID, 만료 시간, 발급 시간을 포함합니다.
         */
        @Setter
        @NoArgsConstructor
        public static class Token {

            private String id;
            private String expires;
            private String issuedAt;

            /**
             * 토큰 정보를 초기화하는 생성자입니다. 이 생성자는 인증 토큰의 고유 식별자, 만료 시간, 그리고 발급 시간을 설정하는 데 사용됩니다.
             * {@link Builder} 어노테이션을 통해 제공되며, 객체 생성 시 편리한 빌더 패턴을 사용할 수 있습니다.
             *
             * @param id       토큰의 고유 식별자. 인증 과정에서 사용되며, 토큰을 구분하는 데 필요한 정보입니다.
             * @param expires  토큰의 만료 시간. 이 시간이 지나면 토큰은 더 이상 유효하지 않습니다.
             * @param issuedAt 토큰이 발급된 시간. 토큰의 생명 주기를 관리하는 데 사용됩니다.
             */
            @Builder
            public Token(String id, String expires, String issuedAt) {
                this.id = id;
                this.expires = expires;
                this.issuedAt = issuedAt;
            }
        }

    }

    @Builder
    public TokenResponse(Access access) {
        this.access = access;
    }

    /**
     * 토큰 ID를 반환하는 메소드입니다.
     *
     * @return 인증 토큰 ID
     */
    public String getTokenId() {
        return this.access.token.id;
    }

}