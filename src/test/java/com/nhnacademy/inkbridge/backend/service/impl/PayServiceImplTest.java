package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.inkbridge.backend.dto.pay.PayCancelRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pay.PayCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pay.PayReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.Pay;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookOrderDetailRepository;
import com.nhnacademy.inkbridge.backend.repository.BookOrderRepository;
import com.nhnacademy.inkbridge.backend.repository.PayRepository;
import com.nhnacademy.inkbridge.backend.service.BookService;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * class: PayServiceImplTest.
 *
 * @author jangjaehun
 * @version 2024/03/22
 */
@ExtendWith(MockitoExtension.class)
class PayServiceImplTest {

    @InjectMocks
    PayServiceImpl payService;

    @Mock
    PayRepository payRepository;

    @Mock
    BookOrderRepository bookOrderRepository;

    @Mock
    BookService bookService;

    @Mock
    BookOrderDetailRepository bookOrderDetailRepository;

    @Test
    @DisplayName("결제 저장 - 주문 정보가 없을 때")
    void testCreatePay_not_found() {
        String orderCode = UUID.randomUUID().toString().replace("-", "");
        PayCreateRequestDto createRequestDto = new PayCreateRequestDto();
        ReflectionTestUtils.setField(createRequestDto, "payKey", "결제 키");
        ReflectionTestUtils.setField(createRequestDto, "orderCode", orderCode);
        ReflectionTestUtils.setField(createRequestDto, "totalAmount", 10000L);
        ReflectionTestUtils.setField(createRequestDto, "balanceAmount", 10000L);
        ReflectionTestUtils.setField(createRequestDto, "approvedAt",
            LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        ReflectionTestUtils.setField(createRequestDto, "requestedAt",
            LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        ReflectionTestUtils.setField(createRequestDto, "vat", 1000L);
        ReflectionTestUtils.setField(createRequestDto, "isPartialCancelable", true);
        ReflectionTestUtils.setField(createRequestDto, "method", "결제 방법");
        ReflectionTestUtils.setField(createRequestDto, "status", "결제 상태");
        ReflectionTestUtils.setField(createRequestDto, "provider", "toss");

        given(bookOrderRepository.findByOrderCode(orderCode)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> payService.createPay(createRequestDto));

        verify(bookOrderRepository, times(1)).findByOrderCode(orderCode);
    }

    @Test
    @DisplayName("결제 저장 - 저장 성공")
    void testCreatePay_success() {
        String orderCode = UUID.randomUUID().toString().replace("-", "");
        PayCreateRequestDto createRequestDto = new PayCreateRequestDto();
        ReflectionTestUtils.setField(createRequestDto, "payKey", "결제 키");
        ReflectionTestUtils.setField(createRequestDto, "orderCode", orderCode);
        ReflectionTestUtils.setField(createRequestDto, "totalAmount", 10000L);
        ReflectionTestUtils.setField(createRequestDto, "balanceAmount", 10000L);
        ReflectionTestUtils.setField(createRequestDto, "approvedAt",
            LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        ReflectionTestUtils.setField(createRequestDto, "requestedAt",
            LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        ReflectionTestUtils.setField(createRequestDto, "vat", 1000L);
        ReflectionTestUtils.setField(createRequestDto, "isPartialCancelable", true);
        ReflectionTestUtils.setField(createRequestDto, "method", "결제 방법");
        ReflectionTestUtils.setField(createRequestDto, "status", "결제 상태");
        ReflectionTestUtils.setField(createRequestDto, "provider", "toss");

        BookOrder bookOrder = BookOrder.builder().build();
        Pay pay = Pay.builder().build();
        ReflectionTestUtils.setField(pay, "payId", 1L);

        given(bookOrderRepository.findByOrderCode(orderCode)).willReturn(Optional.of(bookOrder));
        given(payRepository.save(any())).willReturn(pay);

        Long result = payService.createPay(createRequestDto);

        assertEquals(1L, result);

        verify(bookOrderRepository, times(1)).findByOrderCode(orderCode);
        verify(payRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("결제 조회 - 결제 번호에 맞는 결제 정보가 없을 때")
    void testGetPayByPayId_not_found() {
        given(payRepository.findPayByPayId(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> payService.getPayByPayId(1L));

        verify(payRepository, times(1)).findPayByPayId(1L);
    }

    @Test
    @DisplayName("결제 조회 - 결제 번호로 조회 성공")
    void testGetPayByPayId_success() {
        PayReadResponseDto response = new PayReadResponseDto("결제 키", "결제 방법",
            "결제 상태", LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            10000L, 10000L, 1000L, true, "toss");

        given(payRepository.findPayByPayId(1L)).willReturn(Optional.of(response));

        PayReadResponseDto result = payService.getPayByPayId(1L);

        assertEquals(response, result);

        verify(payRepository, times(1)).findPayByPayId(1L);
    }

    @Test
    @DisplayName("결제 조회 - 결제 번호로 조회 성공")
    void testGetPayByOrderId_success() {
        PayReadResponseDto response = new PayReadResponseDto("결제 키", "결제 방법",
            "결제 상태", LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            10000L, 10000L, 1000L, true, "toss");

        given(payRepository.findPayByOrderId(1L)).willReturn(response);

        PayReadResponseDto result = payService.getPayByOrderId(1L);

        assertEquals(response, result);

        verify(payRepository, times(1)).findPayByOrderId(1L);
    }

    @Test
    @DisplayName("결제 조회 - 결제 코드로 조회 성공")
    void testGetPayByOrderCode_success() {
        PayReadResponseDto response = new PayReadResponseDto("결제 키", "결제 방법",
            "결제 상태", LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            10000L, 10000L, 1000L, true, "toss");

        given(payRepository.findPayByOrderCode("orderCode")).willReturn(response);

        PayReadResponseDto result = payService.getPayByOrderCode("orderCode");

        assertEquals(response, result);

        verify(payRepository, times(1)).findPayByOrderCode("orderCode");
    }

    @Test
    @DisplayName("결제 취소 - 취소할 결제 정보가 존재하지 않는 경우")
    void testCancelPay_order_not_found() {
        given(payRepository.findByOrderCode("orderCode")).willReturn(Optional.empty());
        PayCancelRequestDto requestDto = new PayCancelRequestDto();
        ReflectionTestUtils.setField(requestDto, "orderCode", "orderCode");

        assertThrows(NotFoundException.class, () -> payService.cancelPay(requestDto));

        verify(payRepository, times(1)).findByOrderCode("orderCode");
    }

    @Test
    @DisplayName("결제 취소 - 성공")
    void testCancelPay_success() {
        Pay pay = Pay.builder().build();

        given(payRepository.findByOrderCode("orderCode")).willReturn(Optional.of(pay));

        PayCancelRequestDto requestDto = new PayCancelRequestDto();
        ReflectionTestUtils.setField(requestDto, "orderCode", "orderCode");
        ReflectionTestUtils.setField(requestDto, "status", "CANCELED");
        ReflectionTestUtils.setField(requestDto, "totalAmount", 10000L);
        ReflectionTestUtils.setField(requestDto, "balanceAmount", 0L);
        ReflectionTestUtils.setField(requestDto, "isPartialCancelable", false);

        payService.cancelPay(requestDto);

        assertAll(
            () -> assertEquals(requestDto.getStatus(), pay.getStatus()),
            () -> assertEquals(requestDto.getTotalAmount(), pay.getTotalAmount()),
            () -> assertEquals(requestDto.getBalanceAmount(), pay.getBalanceAmount()),
            () -> assertEquals(requestDto.getIsPartialCancelable(), pay.getIsPartialCancelable())
        );

        verify(payRepository, times(1)).findByOrderCode("orderCode");
    }
}