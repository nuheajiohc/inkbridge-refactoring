package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.pay.PayReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Pay;
import java.util.Optional;

/**
 * class: PayRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
public interface PayRepositoryCustom {

    /**
     * 결제 정보를 조회하는 메소드입니다.
     *
     * @param payId 결제 번호
     * @return 결제 정보
     */
    Optional<PayReadResponseDto> findPayByPayId(Long payId);

    /**
     * 결제 정보를 조회하는 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return 결제 정보
     */
    PayReadResponseDto findPayByOrderId(Long orderId);

    /**
     * 결제 정보를 조회하는 메소드입니다.
     *
     * @param orderCode 주문 코드
     * @return 결제 정보
     */
    PayReadResponseDto findPayByOrderCode(String orderCode);

    /**
     * 주문 코드로 결제 정보를 조회하는 메소드입니다.
     *
     * @param orderCode 주문 코드
     * @return 결제 정보
     */
    Optional<Pay> findByOrderCode(String orderCode);
}
