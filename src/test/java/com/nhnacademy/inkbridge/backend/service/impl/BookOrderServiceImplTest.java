package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberPointReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookOrderRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * class: BookOrderServiceImplTest.
 *
 * @author jangjaehun
 * @version 2024/03/13
 */
@ExtendWith(MockitoExtension.class)
class BookOrderServiceImplTest {

    @InjectMocks
    BookOrderServiceImpl bookOrderService;

    @Mock
    BookOrderRepository bookOrderRepository;

    @Mock
    MemberRepository memberRepository;

    BookOrderCreateRequestDto requestDto;

    @BeforeEach
    void setup() {
        requestDto = new BookOrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "orderName", "orderName");
        ReflectionTestUtils.setField(requestDto, "receiverName", "receiverName");
        ReflectionTestUtils.setField(requestDto, "receiverPhoneNumber", "01011112222");
        ReflectionTestUtils.setField(requestDto, "zipCode", "00000");
        ReflectionTestUtils.setField(requestDto, "address", "address");
        ReflectionTestUtils.setField(requestDto, "detailAddress", "detailAddress");
        ReflectionTestUtils.setField(requestDto, "senderName", "senderName");
        ReflectionTestUtils.setField(requestDto, "senderPhoneNumber", "01033334444");
        ReflectionTestUtils.setField(requestDto, "senderEmail", "sender@inkbridge.store");
        ReflectionTestUtils.setField(requestDto, "deliveryDate", LocalDate.of(2024, 1, 1));
        ReflectionTestUtils.setField(requestDto, "usePoint", 0L);
        ReflectionTestUtils.setField(requestDto, "payAmount", 30000L);
        ReflectionTestUtils.setField(requestDto, "memberId", 1L);
        ReflectionTestUtils.setField(requestDto, "deliveryPrice", 3000L);

    }

    @Test
    @DisplayName("주문 생성 - 멤버 번호에 맞는 멤버가 없는 경우")
    void testCreateBookOrder_member_not_found() {
        given(memberRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookOrderService.createBookOrder(requestDto));
    }

    @Test
    @DisplayName("주문 생성 - 회원 성공")
    void testCreateBookOrder_member_success() {
        BookOrder bookOrder = BookOrder.builder()
            .orderCode("UUID")
            .build();
        Member member = Member.create().build();
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));
        given(bookOrderRepository.save(any())).willReturn(bookOrder);

        OrderCreateResponseDto result = bookOrderService.createBookOrder(requestDto);

        assertEquals(bookOrder.getOrderCode(), result.getOrderCode());

        verify(memberRepository, times(1)).findById(1L);
        verify(bookOrderRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("주문 생성 - 비회원 성공")
    void testCreateBookOrder_anonymous_success() {
        ReflectionTestUtils.setField(requestDto, "memberId", null);
        BookOrder bookOrder = BookOrder.builder()
            .orderCode("UUID")
            .build();

        given(bookOrderRepository.save(any())).willReturn(bookOrder);

        OrderCreateResponseDto result = bookOrderService.createBookOrder(requestDto);

        assertEquals(bookOrder.getOrderCode(), result.getOrderCode());

        verify(bookOrderRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("주문 결제 정보 조회 - 주문코드에 맞는 도서번호가 없는 경우")
    void testGetOrderPaymentInfoByOrderCode_not_found() {
        given(bookOrderRepository.findOrderPayByOrderCode("orderId")).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookOrderService.getOrderPaymentInfoByOrderCode("orderId"));

        verify(bookOrderRepository, times(1)).findOrderPayByOrderCode("orderId");
    }

    @Test
    @DisplayName("주문 결제 정보 조회 - 주문 코드 성공")
    void testGetOrderPaymentInfoByOrderCode_success() {
        OrderPayInfoReadResponseDto responseDto = new OrderPayInfoReadResponseDto("orderId",
            "orderName", 10000L);

        given(bookOrderRepository.findOrderPayByOrderCode("orderId")).willReturn(
            Optional.of(responseDto));

        OrderPayInfoReadResponseDto result = bookOrderService.getOrderPaymentInfoByOrderCode(
            "orderId");

        assertAll(
            () -> assertEquals(responseDto.getOrderCode(), result.getOrderCode()),
            () -> assertEquals(responseDto.getOrderName(), result.getOrderName()),
            () -> assertEquals(responseDto.getAmount(), result.getAmount())
        );

        verify(bookOrderRepository, times(1)).findOrderPayByOrderCode("orderId");
    }

    @Test
    @DisplayName("주문 결제 정보 조회 - 주문 번호에 맞는 도서 번호가 없는 경우")
    void testGetOrderPaymentInfoByOrderId_not_found() {
        given(bookOrderRepository.findOrderPayByOrderId(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookOrderService.getOrderPaymentInfoByOrderId(1L));

        verify(bookOrderRepository, times(1)).findOrderPayByOrderId(1L);
    }

    @Test
    @DisplayName("주문 결제 정보 조회 - 주문 번호 성공")
    void testGetOrderPaymentInfoByOrderId_success() {
        OrderPayInfoReadResponseDto responseDto = new OrderPayInfoReadResponseDto("orderId",
            "orderName", 10000L);

        given(bookOrderRepository.findOrderPayByOrderId(1L)).willReturn(
            Optional.of(responseDto));

        OrderPayInfoReadResponseDto result = bookOrderService.getOrderPaymentInfoByOrderId(1L);

        assertAll(
            () -> assertEquals(responseDto.getOrderCode(), result.getOrderCode()),
            () -> assertEquals(responseDto.getOrderName(), result.getOrderName()),
            () -> assertEquals(responseDto.getAmount(), result.getAmount())
        );

        verify(bookOrderRepository, times(1)).findOrderPayByOrderId(1L);
    }

    @Test
    @DisplayName("주문 결제 상태 변경 - 주문 코드에 맞는 주문 정보가 없는 경우")
    void testUpdateBookOrderPayStatusByOrderCode_order_not_found() {
        given(bookOrderRepository.findByOrderCode("orderCode")).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookOrderService.updateBookOrderPayStatusByOrderCode("orderCode"));

        verify(bookOrderRepository, times(1)).findByOrderCode("orderCode");
    }

    @Test
    @DisplayName("주문 결제 상태 변경 - 성공")
    void testUpdateBookOrderPayStatusByOrderCode_success() {
        BookOrder bookOrder = BookOrder.builder().build();

        given(bookOrderRepository.findByOrderCode("orderCode")).willReturn(Optional.of(bookOrder));

        bookOrderService.updateBookOrderPayStatusByOrderCode("orderCode");

        verify(bookOrderRepository, times(1)).findByOrderCode("orderCode");
    }

    @Test
    @DisplayName("회원 사용 포인트 정보 조회")
    void testGetOrderPersonByOrderCode() {
        OrderedMemberPointReadResponseDto response =
            new OrderedMemberPointReadResponseDto(1L, 3000L, 5000L);

        given(bookOrderRepository.findUsedPointByOrderCode("orderCode")).willReturn(response);

        OrderedMemberPointReadResponseDto result = bookOrderService.getOrderedPersonByOrderCode(
            "orderCode");

        assertEquals(response, result);

        verify(bookOrderRepository, times(1)).findUsedPointByOrderCode("orderCode");
    }

    @Test
    @DisplayName("회원 주문 목록 조회")
    void testGetOrderListByMemberId() {
        OrderReadResponseDto response =
            new OrderReadResponseDto(1L, "orderCode", "orderName",
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
                LocalDate.of(2024, 1, 1), 10000L);

        Pageable pageable = PageRequest.of(1, 10);

        given(bookOrderRepository.findOrderByMemberId(1L, pageable)).willReturn(new PageImpl<>(
            List.of(response), pageable, 1L));

        Page<OrderReadResponseDto> result = bookOrderService.getOrderListByMemberId(1L, pageable);

        assertEquals(response, result.getContent().get(0));

        verify(bookOrderRepository, times(1)).findOrderByMemberId(1L, pageable);
    }

    @Test
    @DisplayName("주문 내역 조회 - 주문 번호에 맞는 주문 정보가 없는 경우")
    void testGetOrderByOrderId_not_found() {
        given(bookOrderRepository.findOrderByOrderId(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookOrderService.getOrderByOrderId(1L));

        verify(bookOrderRepository, times(1)).findOrderByOrderId(1L);
    }

    @Test
    @DisplayName("주문 내역 조회 - 주문 번호 - 성공")
    void testGetOrderByOrderId_success() {
        OrderResponseDto response = new OrderResponseDto("orderCode", "orderName",
            "장재훈", "01011111111", "11111", "주소",
            "상세주소", "장재훈", "01011111111",
            "test@inkbridge.store", LocalDate.of(2024, 1, 1),
            100L, 1000L, 3000L,
            LocalDateTime.of(2024, 1, 1, 0, 0, 0), null);

        given(bookOrderRepository.findOrderByOrderId(1L)).willReturn(Optional.of(response));

        OrderResponseDto result = bookOrderService.getOrderByOrderId(1L);

        assertEquals(response, result);

        verify(bookOrderRepository, times(1)).findOrderByOrderId(1L);
    }

    @Test
    @DisplayName("주문 내역 조회 - 주문 코드에 맞는 주문 정보가 없는 경우")
    void testGetOrderByOrderCode_not_found() {
        given(bookOrderRepository.findOrderByOrderCode("orderCode")).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookOrderService.getOrderByOrderCode("orderCode"));

        verify(bookOrderRepository, times(1)).findOrderByOrderCode("orderCode");
    }

    @Test
    @DisplayName("주문 내역 조회 - 주문 코드 - 성공")
    void testGetOrderByOrderCode_success() {
        OrderResponseDto response = new OrderResponseDto("orderCode", "orderName",
            "장재훈", "01011111111", "11111", "주소",
            "상세주소", "장재훈", "01011111111",
            "test@inkbridge.store", LocalDate.of(2024, 1, 1),
            100L, 1000L, 3000L,
            LocalDateTime.of(2024, 1, 1, 0, 0, 0), null);

        given(bookOrderRepository.findOrderByOrderCode("orderCode")).willReturn(
            Optional.of(response));

        OrderResponseDto result = bookOrderService.getOrderByOrderCode("orderCode");

        assertEquals(response, result);

        verify(bookOrderRepository, times(1)).findOrderByOrderCode("orderCode");
    }

    @Test
    @DisplayName("전체 주문 목록 조회")
    void testGetOrderList() {
        OrderReadResponseDto response = new OrderReadResponseDto(1L, "orderCode",
            "orderName", LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            LocalDate.of(2024, 1, 1), 1000L);

        Pageable pageable = PageRequest.of(0, 10);

        given(bookOrderRepository.findOrderBy(pageable))
            .willReturn(new PageImpl<>(List.of(response), pageable, 1L));

        Page<OrderReadResponseDto> result = bookOrderService.getOrderList(pageable);

        assertEquals(response, result.getContent().get(0));

        verify(bookOrderRepository, times(1)).findOrderBy(pageable);
    }

    @Test
    @DisplayName("주문 출고일 설정 - 주문 번호에 맞는 주문이 없는 경우")
    void testUpdateOrderShipDate_not_found() {
        given(bookOrderRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookOrderService.updateOrderShipDate(1L));

        verify(bookOrderRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 출고일 설정 - 설정 성공")
    void testUpdateOrderShipDate_success() {
        BookOrder bookOrder = BookOrder.builder().build();

        given(bookOrderRepository.findById(1L)).willReturn(Optional.of(bookOrder));

        bookOrderService.updateOrderShipDate(1L);

        assertAll(
            () -> assertNotNull(bookOrder.getShipDate()),
            () -> assertEquals(LocalDate.now(), bookOrder.getShipDate())
        );

        verify(bookOrderRepository, times(1)).findById(1L);
    }
}