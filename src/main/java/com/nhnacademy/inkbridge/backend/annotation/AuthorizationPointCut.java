package com.nhnacademy.inkbridge.backend.annotation;

import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * class: AuthorizationPointCut.
 *
 * @author devminseo
 * @version 3/23/24
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationPointCut {
    private static final String MEMBER_INFO_HEADER = "Authorization-Id";

    /**
     * 경로로 들어오는 유저 아이디값과 토큰 인증을 통해 들어오는 유저의 아이디값이 일치하는지 검사하는 AOP 입니다.
     *
     * @param pjp joinPoint
     * @return 일치
     * @throws Throwable 예외처리
     */
    @Around(value = "@annotation(com.nhnacademy.inkbridge.backend.annotation.Auth)")
    public Object isAuthorization(ProceedingJoinPoint pjp)throws Throwable {
        HttpServletRequest request = getRequest();
        if (isNotHeader(request)) {
            throw new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
        }
        if (request.getRequestURI().contains(request.getHeader(MEMBER_INFO_HEADER))) {
            return pjp.proceed(pjp.getArgs());
        }
        throw new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
    }

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes
                = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        return servletRequestAttributes.getRequest();
    }

    private static boolean isNotHeader(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION).isEmpty();
    }


}
