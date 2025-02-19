package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.pay.PayCancelRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pay.PayCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookStockUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pay.PayReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.Pay;
import com.nhnacademy.inkbridge.backend.enums.OrderMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.PayMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookOrderDetailRepository;
import com.nhnacademy.inkbridge.backend.repository.BookOrderRepository;
import com.nhnacademy.inkbridge.backend.repository.PayRepository;
import com.nhnacademy.inkbridge.backend.service.BookService;
import com.nhnacademy.inkbridge.backend.service.PayService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: PayServiceImpl.
 *
 * @author jangjaehun
 * @version 2024/03/16
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PayServiceImpl implements PayService {

    private final PayRepository payRepository;
    private final BookOrderRepository bookOrderRepository;
    private final BookService bookService;
    private final BookOrderDetailRepository bookOrderDetailRepository;

    /**
     * {@inheritDoc}
     *
     * @param requestDto 결제정보
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long createPay(PayCreateRequestDto requestDto) {

        List<BookStockUpdateRequestDto> bookStock = bookOrderDetailRepository.findBookStockByOrderCode(
            requestDto.getOrderCode());

        if (Boolean.TRUE.equals(bookService.validateStock(bookStock))) {
            bookService.updateStock(bookStock);
        }

        BookOrder bookOrder = bookOrderRepository.findByOrderCode(requestDto.getOrderCode())
            .orElseThrow(
                () -> new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage()));

        Pay pay = Pay.builder()
            .paymentKey(requestDto.getPayKey())
            .order(bookOrder)
            .totalAmount(requestDto.getTotalAmount())
            .balanceAmount(requestDto.getBalanceAmount())
            .approvedAt(requestDto.getApprovedAt())
            .requestedAt(requestDto.getRequestedAt())
            .vat(requestDto.getVat())
            .method(requestDto.getMethod())
            .isPartialCancelable(requestDto.getIsPartialCancelable())
            .status(requestDto.getStatus())
            .provider(requestDto.getProvider())
            .build();

        pay = payRepository.save(pay);

        return pay.getPayId();
    }

    /**
     * {@inheritDoc}
     *
     * @param payId 결제 번호
     * @return 결제 정보
     */
    @Transactional(readOnly = true)
    @Override
    public PayReadResponseDto getPayByPayId(Long payId) {
        return payRepository.findPayByPayId(payId)
            .orElseThrow(() -> new NotFoundException(PayMessageEnum.PAY_NOT_FOUND.getMessage()));
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문번호
     * @return 결제 정보
     */
    @Transactional(readOnly = true)
    @Override
    public PayReadResponseDto getPayByOrderId(Long orderId) {
        return payRepository.findPayByOrderId(orderId);
    }

    /**
     * 주문 코드로 결제를 조회하는 메소드입니다.
     *
     * @param orderCode 주문 코드
     * @return 결제 정보
     */
    @Transactional(readOnly = true)
    @Override
    public PayReadResponseDto getPayByOrderCode(String orderCode) {
        return payRepository.findPayByOrderCode(orderCode);
    }

    /**
     * {@inheritDoc}
     *
     * @param requestDto 요청 정보
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelPay(PayCancelRequestDto requestDto) {
        Pay pay = payRepository.findByOrderCode(requestDto.getOrderCode())
            .orElseThrow(() -> new NotFoundException(PayMessageEnum.PAY_NOT_FOUND.getMessage()));

        pay.updatePay(requestDto.getStatus(), requestDto.getTotalAmount(),
            requestDto.getBalanceAmount(), requestDto.getIsPartialCancelable());
    }
}
