package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.inkbridge.backend.dto.order.BookOrderDetailResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderBooksIdResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderDetailCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pay.PayReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.OrderMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.OrderStatusEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.facade.OrderFacade;
import com.nhnacademy.inkbridge.backend.service.AccumulationRatePolicyService;
import com.nhnacademy.inkbridge.backend.service.BookOrderDetailService;
import com.nhnacademy.inkbridge.backend.service.BookOrderService;
import com.nhnacademy.inkbridge.backend.service.MemberPointService;
import com.nhnacademy.inkbridge.backend.service.PayService;
import com.nhnacademy.inkbridge.backend.service.ReviewService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
 * class: OrderFacadeTest.
 *
 * @author jangjaehun
 * @version 2024/03/13
 */
@ExtendWith(MockitoExtension.class)
class OrderFacadeTest {

    @InjectMocks
    OrderFacade orderFacade;

    @Mock
    BookOrderService bookOrderService;

    @Mock
    BookOrderDetailService bookOrderDetailService;

    @Mock
    PayService payService;

    @Mock
    AccumulationRatePolicyService accumulationRatePolicyService;

    @Mock
    MemberPointService memberPointService;
    @Mock
    ReviewService reviewService;

    @Test
    @DisplayName("주문 생성 - 성공")
    void testCreateOrder_success() {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookOrderList",
            List.of(new BookOrderDetailCreateRequestDto()));
        ReflectionTestUtils.setField(requestDto, "bookOrder", new BookOrderCreateRequestDto());
        OrderCreateResponseDto responseDto = new OrderCreateResponseDto(1L, "orderCode");

        given(bookOrderService.createBookOrder(any())).willReturn(responseDto);

        OrderCreateResponseDto result = orderFacade.createOrder(requestDto);

        assertEquals(responseDto, result);

        verify(bookOrderService, times(1)).createBookOrder(any());
        verify(bookOrderDetailService, times(1)).createBookOrderDetail(any(), anyList());
    }

    @Test
    @DisplayName("주문 생성 - 멤버 번호에 맞는 멤버가 없는 경우")
    void testCreateOrder_member_not_found() {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookOrderList",
            List.of(new BookOrderDetailCreateRequestDto()));
        ReflectionTestUtils.setField(requestDto, "bookOrder", new BookOrderCreateRequestDto());

        given(bookOrderService.createBookOrder(any())).willThrow(new NotFoundException(
            MemberMessageEnum.MEMBER_NOT_FOUND.getMessage()));

        assertThrows(NotFoundException.class, () -> orderFacade.createOrder(requestDto));

        verify(bookOrderService, times(1)).createBookOrder(any());
    }

    @Test
    @DisplayName("주문 생성 - 도서 번호에 맞는 도서가 없는 경우")
    void testCreateOrder_book_not_found() {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookOrderList",
            List.of(new BookOrderDetailCreateRequestDto()));
        ReflectionTestUtils.setField(requestDto, "bookOrder", new BookOrderCreateRequestDto());
        OrderCreateResponseDto responseDto = new OrderCreateResponseDto(1L, "orderCode");

        given(bookOrderService.createBookOrder(any())).willReturn(responseDto);
        doThrow(new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage())).when(
            bookOrderDetailService).createBookOrderDetail(any(), anyList());

        assertThrows(NotFoundException.class, () -> orderFacade.createOrder(requestDto));

        verify(bookOrderService, times(1)).createBookOrder(any());
        verify(bookOrderDetailService, times(1)).createBookOrderDetail(any(), anyList());
    }

    @Test
    @DisplayName("주문 생성 - 쿠폰 번호에 맞는 쿠폰이 없는 경우")
    void testCreateOrder_coupon_not_found() {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookOrderList",
            List.of(new BookOrderDetailCreateRequestDto()));
        ReflectionTestUtils.setField(requestDto, "bookOrder", new BookOrderCreateRequestDto());
        OrderCreateResponseDto responseDto = new OrderCreateResponseDto(1L, "orderCode");

        given(bookOrderService.createBookOrder(any())).willReturn(responseDto);
        doThrow(new NotFoundException(CouponMessageEnum.COUPON_NOT_FOUND.getMessage())).when(
            bookOrderDetailService).createBookOrderDetail(any(), anyList());

        assertThrows(NotFoundException.class, () -> orderFacade.createOrder(requestDto));

        verify(bookOrderService, times(1)).createBookOrder(any());
        verify(bookOrderDetailService, times(1)).createBookOrderDetail(any(), anyList());
    }

    @Test
    @DisplayName("주문 생성 - 주문 상태 번호에 맞는 주문 상태가 없는 경우")
    void testCreateOrder_book_order_status_not_found() {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookOrderList",
            List.of(new BookOrderDetailCreateRequestDto()));
        ReflectionTestUtils.setField(requestDto, "bookOrder", new BookOrderCreateRequestDto());
        OrderCreateResponseDto responseDto = new OrderCreateResponseDto(1L, "orderCode");

        given(bookOrderService.createBookOrder(any())).willReturn(responseDto);
        doThrow(new NotFoundException(OrderMessageEnum.ORDER_STATUS_NOT_FOUND.getMessage())).when(
            bookOrderDetailService).createBookOrderDetail(any(), anyList());

        assertThrows(NotFoundException.class, () -> orderFacade.createOrder(requestDto));

        verify(bookOrderService, times(1)).createBookOrder(any());
        verify(bookOrderDetailService, times(1)).createBookOrderDetail(any(), anyList());
    }

    @Test
    @DisplayName("주문 생성 - 포장지 번호에 맞는 포장지가 없는 경우")
    void testCreateOrder_wrapping_not_found() {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookOrderList",
            List.of(new BookOrderDetailCreateRequestDto()));
        ReflectionTestUtils.setField(requestDto, "bookOrder", new BookOrderCreateRequestDto());
        OrderCreateResponseDto responseDto = new OrderCreateResponseDto(1L, "orderCode");

        given(bookOrderService.createBookOrder(any())).willReturn(responseDto);
        doThrow(new NotFoundException(OrderMessageEnum.WRAPPING_NOT_FOUND.getMessage())).when(
            bookOrderDetailService).createBookOrderDetail(any(), anyList());

        assertThrows(NotFoundException.class, () -> orderFacade.createOrder(requestDto));

        verify(bookOrderService, times(1)).createBookOrder(any());
        verify(bookOrderDetailService, times(1)).createBookOrderDetail(any(), anyList());
    }

    @Test
    @DisplayName("주문 결제 정보 조회 - 주문 번호에 맞는 주문이 없는 경우")
    void testGetOrderPaymentInfo_not_found() {
        given(bookOrderService.getOrderPaymentInfoByOrderCode("orderId")).willThrow(
            new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage()));

        assertThrows(NotFoundException.class, () -> orderFacade.getOrderPaymentInfo("orderId"));

        verify(bookOrderService, times(1)).getOrderPaymentInfoByOrderCode("orderId");
    }

    @Test
    @DisplayName("주문 결제 정보 조회 - 성공")
    void testGetOrderPaymentInfo_success() {
        OrderPayInfoReadResponseDto responseDto = new OrderPayInfoReadResponseDto("orderId",
            "orderName", 10000L);

        given(bookOrderService.getOrderPaymentInfoByOrderCode("orderId")).willReturn(responseDto);

        OrderPayInfoReadResponseDto result = orderFacade.getOrderPaymentInfo("orderId");

        verify(bookOrderService, times(1)).getOrderPaymentInfoByOrderCode("orderId");
    }

    @Test
    @DisplayName("회원 주문 목록 조회")
    void testGetOrderListByMemberId() {
        Pageable pageable = PageRequest.of(0, 1);
        OrderReadResponseDto responseDto = new OrderReadResponseDto(1L, "orderCode", "orderName",
            LocalDateTime.now(), LocalDate.now(), 10000L);
        Page<OrderReadResponseDto> response = new PageImpl<>(List.of(responseDto), pageable, 1);

        given(bookOrderService.getOrderListByMemberId(1L, pageable)).willReturn(response);

        Page<OrderReadResponseDto> result = orderFacade.getOrderListByMemberId(1L, pageable);

        assertEquals(response, result);

        verify(bookOrderService, times(1)).getOrderListByMemberId(1L, pageable);
    }

    @Test
    @DisplayName("회원 주문 상세 조회")
    void testGetOrderDetailByOrderId() {
        OrderResponseDto orderResponse = new OrderResponseDto("orderCode",
            "orderName", "receiver", "01011112222",
            "11111", "address", "detailAddress", "sender",
            "01033334444", "test@inkbridge.store",
            LocalDate.of(2024, 1, 1), 0L, 30000L,
            5000L, LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            LocalDate.of(2024, 1, 1));

        PayReadResponseDto payResponse = new PayReadResponseDto("paymentKey",
            "method", "status",
            LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            30000L, 30000L, 3000L, true, "provider");

        OrderDetailReadResponseDto orderDetailReadResponseDto = new OrderDetailReadResponseDto(1L,
            30000L, 0L, 1,
            "wrappingName", "WAITING", 1L,
            "thumbnailUrl", "bookTitle", "PERCENT",
            "couponName", 15000L, 5L);
        List<OrderDetailReadResponseDto> detailResponseList = List.of(orderDetailReadResponseDto);

        given(bookOrderService.getOrderByOrderId(any())).willReturn(orderResponse);
        given(bookOrderDetailService.getOrderDetailListByOrderId(any())).willReturn(detailResponseList);
        given(payService.getPayByOrderId(any())).willReturn(payResponse);

        BookOrderDetailResponseDto result = orderFacade.getOrderDetailByOrderId(1L);

        assertAll(
            () -> assertEquals(orderResponse, result.getOrderInfo()),
            () -> assertEquals(payResponse, result.getPayInfo()),
            () -> assertEquals(detailResponseList, result.getOrderDetailInfoList())
        );

        verify(bookOrderService, times(1)).getOrderByOrderId(any());
        verify(bookOrderDetailService, times(1)).getOrderDetailListByOrderId(any());
        verify(payService, times(1)).getPayByOrderId(any());
    }

    @Test
    @DisplayName("전체 주문 목록 조회")
    void testGetOrderList() {
        Pageable pageable = PageRequest.of(0, 1);
        OrderReadResponseDto responseDto = new OrderReadResponseDto(1L, "orderCode", "orderName",
            LocalDateTime.now(), LocalDate.now(), 10000L);
        Page<OrderReadResponseDto> response = new PageImpl<>(List.of(responseDto), pageable, 1);

        given(bookOrderService.getOrderList(pageable)).willReturn(response);

        Page<OrderReadResponseDto> result = orderFacade.getOrderList(pageable);

        assertEquals(response, result);

        verify(bookOrderService, times(1)).getOrderList(pageable);
    }

    @Test
    @DisplayName("주문 상세 내역 조회")
    void testGetOrderDetailByOrderCode() {
        OrderResponseDto orderResponse = new OrderResponseDto("orderCode",
            "orderName", "receiver", "01011112222",
            "11111", "address", "detailAddress", "sender",
            "01033334444", "test@inkbridge.store",
            LocalDate.of(2024, 1, 1), 0L, 30000L,
            5000L, LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            LocalDate.of(2024, 1, 1));

        PayReadResponseDto payResponse = new PayReadResponseDto("paymentKey",
            "method", "status",
            LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            30000L, 30000L, 3000L, true, "provider");

        OrderDetailReadResponseDto orderDetailReadResponseDto = new OrderDetailReadResponseDto(1L,
            30000L, 0L, 1,
            "wrappingName", "WAITING", 1L,
            "thumbnailUrl", "bookTitle", "PERCENT",
            "couponName", 15000L, 5L);
        List<OrderDetailReadResponseDto> detailResponseList = List.of(orderDetailReadResponseDto);

        given(bookOrderService.getOrderByOrderCode("orderCode")).willReturn(orderResponse);
        given(bookOrderDetailService.getOrderDetailByOrderCode("orderCode")).willReturn(
            detailResponseList);
        given(payService.getPayByOrderCode("orderCode")).willReturn(payResponse);
        given(reviewService.isReviewed(anyList())).willReturn(new HashMap<>());

        BookOrderDetailResponseDto result = orderFacade.getOrderDetailByOrderCode("orderCode");

        assertAll(
            () -> assertEquals(orderResponse, result.getOrderInfo()),
            () -> assertEquals(payResponse, result.getPayInfo()),
            () -> assertEquals(detailResponseList, result.getOrderDetailInfoList())
        );

        verify(bookOrderService, times(1)).getOrderByOrderCode("orderCode");
        verify(bookOrderDetailService, times(1)).getOrderDetailByOrderCode("orderCode");
        verify(payService, times(1)).getPayByOrderCode("orderCode");
        verify(reviewService, times(1)).isReviewed(anyList());
    }

    @Test
    @DisplayName("주문 상태 변경")
    void testUpdateStatus() {
        orderFacade.updateStatus(1L, OrderStatusEnum.DONE);

        verify(bookOrderDetailService, times(1)).changeOrderStatus(1L, OrderStatusEnum.DONE);
    }

    @Test
    @DisplayName("주문 상태 변경 - 배송중인 경우 - 비회원")
    void testUpdateStatus_shipping_anonymous() {

        given(bookOrderService.getOrderedPersonByOrderId(1L)).willReturn(new OrderedMemberReadResponseDto(null, 10000L));

        orderFacade.updateStatus(1L, OrderStatusEnum.SHIPPING);

        verify(bookOrderDetailService, times(1)).changeOrderStatus(1L, OrderStatusEnum.SHIPPING);
        verify(bookOrderService, times(1)).updateOrderShipDate(1L);
        verify(bookOrderService, times(1)).getOrderedPersonByOrderId(1L);
    }

    @Test
    @DisplayName("주문 상태 변경 - 배송중인 경우 - 회원")
    void testUpdateStatus_shipping_member() {

        given(bookOrderService.getOrderedPersonByOrderId(1L)).willReturn(new OrderedMemberReadResponseDto(1L, 10000L));
        given(accumulationRatePolicyService.getCurrentAccumulationRate()).willReturn(1);

        orderFacade.updateStatus(1L, OrderStatusEnum.SHIPPING);

        verify(bookOrderDetailService, times(1)).changeOrderStatus(1L, OrderStatusEnum.SHIPPING);
        verify(bookOrderService, times(1)).updateOrderShipDate(1L);
        verify(bookOrderService, times(1)).getOrderedPersonByOrderId(1L);
        verify(accumulationRatePolicyService, times(1)).getCurrentAccumulationRate();
        verify(memberPointService, times(1)).memberPointUpdate(any(), anyLong(), any());
    }

    @Test
    @DisplayName("도서 번호 조회")
    void testGetOrderBookIdList() {
        List<OrderBooksIdResponseDto> response = List.of(new OrderBooksIdResponseDto(1L));

        given(bookOrderDetailService.getOrderBooksIdByOrderId("orderCode")).willReturn(response);

        List<OrderBooksIdResponseDto> result = orderFacade.getOrderBookIdList("orderCode");

        assertEquals(response, result);

        verify(bookOrderDetailService, times(1)).getOrderBooksIdByOrderId("orderCode");
    }
}