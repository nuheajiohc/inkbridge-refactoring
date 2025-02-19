package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberPointReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderResponseDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: BookOrderRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/03/12
 */
public interface BookOrderRepositoryCustom {

    /**
     * 주문 결제 정보를 조회하는 메소드입니다.
     *
     * @param orderCode 주문 코드
     * @return 주문 결제 정보
     */
    Optional<OrderPayInfoReadResponseDto> findOrderPayByOrderCode(String orderCode);

    /**
     * 주문 결제 정보를 조회하는 메소드입니다.
     *
     * @param orderId 주문 코드
     * @return 주문 결제 정보
     */
    Optional<OrderPayInfoReadResponseDto> findOrderPayByOrderId(Long orderId);

    /**
     * 주문에 사용한 포인트를 조회하는 메소드입니다.
     *
     * @param orderCode 주문 코드
     * @return 사용한 포인트
     */
    OrderedMemberPointReadResponseDto findUsedPointByOrderCode(String orderCode);

    /**
     * 주문에 사용한 포인트를 조회하는 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return 사용한 포인트
     */
    OrderedMemberReadResponseDto findUsedPointByOrderId(Long orderId);

    /**
     * 회원 주문 목록을 조회하는 메소드입니다.
     *
     * @param memberId 회원 번호
     * @param pageable 페이지 정보
     * @return 회원 주문 목록
     */
    Page<OrderReadResponseDto> findOrderByMemberId(Long memberId, Pageable pageable);

    /**
     * 주문 번호로 주문을 조회하는 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return 주문 내역
     */
    Optional<OrderResponseDto> findOrderByOrderId(Long orderId);

    /**
     * 주문 코드로 주문을 조회하는 메소드입니다.
     *
     * @param orderCode 주문 코드
     * @return 주문 내역
     */
    Optional<OrderResponseDto> findOrderByOrderCode(String orderCode);


    /**
     * 전체 주문 목록을 조회하는 메소드입니다.
     *
     * @param pageable 페이지 정보
     * @return 주문 목록 페이지
     */
    Page<OrderReadResponseDto> findOrderBy(Pageable pageable);
}
