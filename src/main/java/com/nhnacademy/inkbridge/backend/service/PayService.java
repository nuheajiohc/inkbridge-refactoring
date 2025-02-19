package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.pay.PayCancelRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pay.PayCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pay.PayReadResponseDto;

/**
 * class: PayService.
 *
 * @author jangjaehun
 * @version 2024/03/16
 */
public interface PayService {

    /**
     * 결제 정보를 저장하는 메소드입니다.
     *
     * @param requestDto 결제 정보
     */
    Long createPay(PayCreateRequestDto requestDto);

    /**
     * payId로 결제를 조회하는 메소드입니다.
     *
     * @param payId 결제 번호
     * @return 결제 정보
     */
    PayReadResponseDto getPayByPayId(Long payId);

    /**
     * orderId로 결제를 조회하는 메소드입니다.
     *
     * @param orderId 주문번호
     * @return 결제 정보
     */
    PayReadResponseDto getPayByOrderId(Long orderId);

    PayReadResponseDto getPayByOrderCode(String orderCode);

    /**
     * 결제를 취소하는 메소드입니다.
     *
     * @param requestDto
     */
    void cancelPay(PayCancelRequestDto requestDto);
}
