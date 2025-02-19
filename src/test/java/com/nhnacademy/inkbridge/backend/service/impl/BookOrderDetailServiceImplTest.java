package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.inkbridge.backend.dto.book.BookStockUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderBooksIdResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderDetailCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.BookOrderDetail;
import com.nhnacademy.inkbridge.backend.entity.BookOrderStatus;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import com.nhnacademy.inkbridge.backend.entity.Wrapping;
import com.nhnacademy.inkbridge.backend.enums.OrderStatusEnum;
import com.nhnacademy.inkbridge.backend.exception.AlreadyProcessedException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookOrderDetailRepository;
import com.nhnacademy.inkbridge.backend.repository.BookOrderRepository;
import com.nhnacademy.inkbridge.backend.repository.BookOrderStatusRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberCouponRepository;
import com.nhnacademy.inkbridge.backend.repository.WrappingRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * class: BookOrderDetailServiceImplTest.
 *
 * @author jangjaehun
 * @version 2024/03/13
 */
@ExtendWith(MockitoExtension.class)
class BookOrderDetailServiceImplTest {

    @InjectMocks
    BookOrderDetailServiceImpl bookOrderDetailService;
    @Mock
    BookOrderRepository bookOrderRepository;
    @Mock
    BookOrderDetailRepository bookOrderDetailRepository;
    @Mock
    BookOrderStatusRepository bookOrderStatusRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    MemberCouponRepository memberCouponRepository;
    @Mock
    WrappingRepository wrappingRepository;

    BookOrderDetailCreateRequestDto requestDto;


    @BeforeEach
    void setup() {
        requestDto = new BookOrderDetailCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookId", 1L);
        ReflectionTestUtils.setField(requestDto, "price", 10000L);
        ReflectionTestUtils.setField(requestDto, "amount", 3);
        ReflectionTestUtils.setField(requestDto, "wrappingId", null);
        ReflectionTestUtils.setField(requestDto, "couponId", 1L);
        ReflectionTestUtils.setField(requestDto, "wrappingPrice", 0L);
    }

    @Test
    @DisplayName("주문 상세 생성 - 성공 - 포장지, 쿠폰 선택 안한 경우")
    void testCreateOrderDetail_success_no_check_wrapping_coupon() {
        ReflectionTestUtils.setField(requestDto, "couponId", null);
        Book book = Book.builder().build();
        BookOrderStatus status = BookOrderStatus.builder().build();
        BookOrder bookOrder = BookOrder.builder().build();

        given(bookOrderRepository.findById(any())).willReturn(Optional.of(bookOrder));
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));
        given(bookOrderStatusRepository.findById(1L)).willReturn(Optional.of(status));

        bookOrderDetailService.createBookOrderDetail(1L, List.of(requestDto));

        verify(bookOrderRepository, times(1)).findById(any());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookOrderStatusRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 상세 생성 - 성공 - 포장지, 쿠폰 선택한 경우")
    void testCrateOrderDetail_success_check_wrapping_coupon() {
        ReflectionTestUtils.setField(requestDto, "wrappingId", 1L);
        ReflectionTestUtils.setField(requestDto, "couponId", 1L);
        ReflectionTestUtils.setField(requestDto, "wrappingPrice", 3000L);

        Book book = Book.builder().build();
        BookOrderStatus status = BookOrderStatus.builder().build();
        BookOrder bookOrder = BookOrder.builder().build();
        MemberCoupon coupon = MemberCoupon.builder().build();
        Wrapping wrapping = Wrapping.builder().build();

        given(bookOrderRepository.findById(any())).willReturn(Optional.of(bookOrder));
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));
        given(memberCouponRepository.findById(1L)).willReturn(Optional.of(coupon));
        given(bookOrderStatusRepository.findById(1L)).willReturn(Optional.of(status));
        given(wrappingRepository.findById(1L)).willReturn(Optional.of(wrapping));

        List<BookOrderDetailCreateRequestDto> request = List.of(requestDto);

        bookOrderDetailService.createBookOrderDetail(1L, request);

        verify(bookOrderRepository, times(1)).findById(any());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookOrderStatusRepository, times(1)).findById(1L);
        verify(memberCouponRepository, times(1)).findById(1L);
        verify(wrappingRepository, times(1)).findById(1L);
        verify(bookOrderDetailRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("주문 상세 생성 - 주문이 없는 경우")
    void testCreateBookOrderDetail_order_not_found() {
        given(bookOrderRepository.findById(1L)).willReturn(Optional.empty());

        List<BookOrderDetailCreateRequestDto> request = List.of(requestDto);

        assertThrows(NotFoundException.class,
            () -> bookOrderDetailService.createBookOrderDetail(1L, request));

        verify(bookOrderRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 상세 생성 - 도서가 없는 경우")
    void testCreateBookOrderDetail_book_not_found() {
        BookOrder bookOrder = BookOrder.builder().build();

        given(bookOrderRepository.findById(1L)).willReturn(Optional.of(bookOrder));
        given(bookRepository.findById(1L)).willReturn(Optional.empty());

        List<BookOrderDetailCreateRequestDto> request = List.of(requestDto);

        assertThrows(NotFoundException.class,
            () -> bookOrderDetailService.createBookOrderDetail(1L, request));

        verify(bookOrderRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 상세 생성 - 쿠폰이 없는 경우")
    void testCreateBookOrderDetail_coupon_not_found() {
        ReflectionTestUtils.setField(requestDto, "couponId", 1L);
        BookOrder bookOrder = BookOrder.builder().build();
        Book book = Book.builder().build();

        given(bookOrderRepository.findById(1L)).willReturn(Optional.of(bookOrder));
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));
        given(memberCouponRepository.findById(1L)).willReturn(Optional.empty());

        List<BookOrderDetailCreateRequestDto> request = List.of(requestDto);

        assertThrows(NotFoundException.class,
            () -> bookOrderDetailService.createBookOrderDetail(1L, request));

        verify(bookOrderRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).findById(1L);
        verify(memberCouponRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 상세 생성 - 주문 상태가 없는 경우")
    void testCreateBookOrderDetail_order_status_not_found() {
        ReflectionTestUtils.setField(requestDto, "couponId", 1L);
        BookOrder bookOrder = BookOrder.builder().build();
        Book book = Book.builder().build();
        MemberCoupon coupon = MemberCoupon.builder().build();

        given(bookOrderRepository.findById(1L)).willReturn(Optional.of(bookOrder));
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));
        given(memberCouponRepository.findById(1L)).willReturn(Optional.of(coupon));
        given(bookOrderStatusRepository.findById(1L)).willReturn(Optional.empty());

        List<BookOrderDetailCreateRequestDto> request = List.of(requestDto);

        assertThrows(NotFoundException.class,
            () -> bookOrderDetailService.createBookOrderDetail(1L, request));

        verify(bookOrderRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).findById(1L);
        verify(memberCouponRepository, times(1)).findById(1L);
        verify(bookOrderStatusRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 상세 생성 - 포장지가 없는 경우")
    void testCreateBookOrderDetail_wrapping_not_found() {
        ReflectionTestUtils.setField(requestDto, "couponId", 1L);
        ReflectionTestUtils.setField(requestDto, "wrappingId", 1L);
        BookOrder bookOrder = BookOrder.builder().build();
        Book book = Book.builder().build();
        MemberCoupon coupon = MemberCoupon.builder().build();
        BookOrderStatus status = BookOrderStatus.builder().build();

        given(bookOrderRepository.findById(1L)).willReturn(Optional.of(bookOrder));
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));
        given(memberCouponRepository.findById(1L)).willReturn(Optional.of(coupon));
        given(bookOrderStatusRepository.findById(1L)).willReturn(Optional.of(status));
        given(wrappingRepository.findById(1L)).willReturn(Optional.empty());

        List<BookOrderDetailCreateRequestDto> response = List.of(requestDto);

        assertThrows(NotFoundException.class,
            () -> bookOrderDetailService.createBookOrderDetail(1L, response));

        verify(bookOrderRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).findById(1L);
        verify(memberCouponRepository, times(1)).findById(1L);
        verify(bookOrderStatusRepository, times(1)).findById(1L);
        verify(wrappingRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 쿠폰 번호 목록 조회")
    void testGetUsedCouponIdByOrderCode() {
        given(bookOrderDetailRepository.findAllByOrderCode(anyString())).willReturn(List.of(1L));

        List<Long> result = bookOrderDetailService.getUsedCouponIdByOrderCode("orderCode");

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(1L, result.get(0))
        );

        verify(bookOrderDetailRepository, times(1)).findAllByOrderCode(anyString());
    }

    @Test
    @DisplayName("주문 한 도서 수량 조회")
    void testGetBookStock() {
        given(bookOrderDetailRepository.findBookStockByOrderCode(anyString())).willReturn(
            List.of(new BookStockUpdateRequestDto(1L, 4)));

        List<BookStockUpdateRequestDto> result = bookOrderDetailService.getBookStock("orderCode");

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(1L, result.get(0).getBookId()),
            () -> assertEquals(4, result.get(0).getAmount())
        );

        verify(bookOrderDetailRepository, times(1)).findBookStockByOrderCode(anyString());
    }

    @Test
    @DisplayName("주문 상세 목록 조회 - 주문 번호")
    void testGetOrderDetailListByOrderId() {
        OrderDetailReadResponseDto response = new OrderDetailReadResponseDto(
            1L, 10000L, 1000L, 3, "포장지",
            "WAITING", 1L, "썸네일파일경로", "도서제목",
            "PERCENT", "쿠폰이름", 1500L, 3L);

        given(bookOrderDetailRepository.findAllOrderDetailByOrderId(anyLong()))
            .willReturn(List.of(response));

        List<OrderDetailReadResponseDto> result =
            bookOrderDetailService.getOrderDetailListByOrderId(1L);

        assertEquals(response, result.get(0));

        verify(bookOrderDetailRepository, times(1)).findAllOrderDetailByOrderId(anyLong());
    }

    @Test
    @DisplayName("주문 상세 목록 조회 - 주문 코드")
    void testGetOrderDetailListByOrderCode() {
        OrderDetailReadResponseDto response = new OrderDetailReadResponseDto(
            1L, 10000L, 1000L, 3, "포장지",
            "WAITING", 1L, "썸네일파일경로", "도서제목",
            "PERCENT", "쿠폰이름", 1500L, 3L);

        given(bookOrderDetailRepository.findAllOrderDetailByOrderCode(anyString()))
            .willReturn(List.of(response));

        List<OrderDetailReadResponseDto> result =
            bookOrderDetailService.getOrderDetailByOrderCode("orderCode");

        assertEquals(response, result.get(0));

        verify(bookOrderDetailRepository, times(1)).findAllOrderDetailByOrderCode(anyString());
    }

    @Test
    @DisplayName("주문 상태 변경 - 주문 상태가 존재하지 않는 경우")
    void testChangeOrderStatus_status_not_found() {
        BookOrderDetail bookOrderDetail = BookOrderDetail.builder().build();

        given(bookOrderDetailRepository.findOrderDetailByOrderId(anyLong()))
            .willReturn(List.of(bookOrderDetail));

        given(bookOrderStatusRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookOrderDetailService.changeOrderStatus(1L, OrderStatusEnum.SHIPPING));

        verify(bookOrderDetailRepository, times(1)).findOrderDetailByOrderId(anyLong());
        verify(bookOrderStatusRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("주문 상태 변경 - 주문의 상태가 변경하려는 상태와 동일한 경우")
    void testChangeOrderStatus_already_process() {
        BookOrderStatus bookOrderStatus = BookOrderStatus.builder().build();
        BookOrderDetail bookOrderDetail = BookOrderDetail.builder()
            .bookOrderStatus(bookOrderStatus)
            .build();

        given(bookOrderDetailRepository.findOrderDetailByOrderId(anyLong()))
            .willReturn(List.of(bookOrderDetail));

        given(bookOrderStatusRepository.findById(any()))
            .willReturn(Optional.of(bookOrderStatus));

        assertThrows(AlreadyProcessedException.class,
            () -> bookOrderDetailService.changeOrderStatus(1L, OrderStatusEnum.SHIPPING));

        verify(bookOrderDetailRepository, times(1)).findOrderDetailByOrderId(anyLong());
        verify(bookOrderStatusRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("주문 상태 변경 - 성공")
    void testChangeOrderStatus_success() {
        BookOrderStatus bookOrderStatus = BookOrderStatus.builder().build();
        BookOrderDetail bookOrderDetail = BookOrderDetail.builder()
            .bookOrderStatus(bookOrderStatus)
            .build();

        given(bookOrderDetailRepository.findOrderDetailByOrderId(anyLong()))
            .willReturn(List.of(bookOrderDetail));

        given(bookOrderStatusRepository.findById(any()))
            .willReturn(Optional.of(BookOrderStatus.builder().build()));

        bookOrderDetailService.changeOrderStatus(1L, OrderStatusEnum.SHIPPING);

        verify(bookOrderDetailRepository, times(1)).findOrderDetailByOrderId(anyLong());
        verify(bookOrderStatusRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("주문한 도서 번호 조회 - 도서에 맞는 주문이 없는 경우")
    void testGetOrderBooksIdByOrderId_order_not_found() {
        given(bookOrderRepository.existsByOrderCode(anyString())).willReturn(false);

        assertThrows(NotFoundException.class,
            () -> bookOrderDetailService.getOrderBooksIdByOrderId("orderCode"));

        verify(bookOrderRepository, times(1)).existsByOrderCode(anyString());
    }

    @Test
    @DisplayName("주문한 도서 번호 조회 - 성공")
    void testGetOrderBooksIdByOrderId_success() {
        OrderBooksIdResponseDto response = new OrderBooksIdResponseDto(1L);
        List<OrderBooksIdResponseDto> list = List.of(response);

        given(bookOrderRepository.existsByOrderCode(anyString())).willReturn(true);
        given(bookOrderDetailRepository.findBookIdByOrderCode(anyString())).willReturn(list);

        List<OrderBooksIdResponseDto> result = bookOrderDetailService.getOrderBooksIdByOrderId(
            "orderCode");

        assertEquals(list, result);

        verify(bookOrderRepository, times(1)).existsByOrderCode(anyString());
        verify(bookOrderDetailRepository, times(1)).findBookIdByOrderCode(anyString());
    }

    @Test
    @DisplayName("주문 상태 변경 - 주문 상태가 없는 경우")
    void testChangeOrderStatusByOrderCode_order_not_found() {
        List<BookOrderDetail> bookOrderDetailList = List.of(BookOrderDetail.builder().build());

        given(bookOrderDetailRepository.findOrderDetailByOrderCode("orderCode")).willReturn(
            bookOrderDetailList);

        given(bookOrderStatusRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookOrderDetailService.changeOrderStatusByOrderCode("orderCode",
                OrderStatusEnum.SHIPPING));

        verify(bookOrderDetailRepository, times(1)).findOrderDetailByOrderCode("orderCode");
        verify(bookOrderStatusRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("주문 상태 변경 - 변경된 상태로 다시 바꿀려는 경우")
    void testChangeOrderStatusByOrderCode_already_process() {
        BookOrderStatus bookOrderStatus = BookOrderStatus.builder().build();

        List<BookOrderDetail> bookOrderDetailList = List.of(BookOrderDetail.builder()
                .bookOrderStatus(bookOrderStatus)
            .build());

        given(bookOrderDetailRepository.findOrderDetailByOrderCode("orderCode")).willReturn(
            bookOrderDetailList);

        given(bookOrderStatusRepository.findById(any())).willReturn(Optional.of(bookOrderStatus));

        assertThrows(AlreadyProcessedException.class,
            () -> bookOrderDetailService.changeOrderStatusByOrderCode("orderCode",
                OrderStatusEnum.SHIPPING));

        verify(bookOrderDetailRepository, times(1)).findOrderDetailByOrderCode("orderCode");
        verify(bookOrderStatusRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("주문 상태 변경 - 변경된 상태로 다시 바꿀려는 경우")
    void testChangeOrderStatusByOrderCode_success() {
        BookOrderStatus bookOrderStatus = BookOrderStatus.builder().build();
        BookOrderStatus current = BookOrderStatus.builder().build();

        BookOrderDetail detail = BookOrderDetail.builder()
            .bookOrderStatus(current)
            .build();

        List<BookOrderDetail> bookOrderDetailList = List.of(detail);

        given(bookOrderDetailRepository.findOrderDetailByOrderCode("orderCode")).willReturn(
            bookOrderDetailList);

        given(bookOrderStatusRepository.findById(any())).willReturn(Optional.of(bookOrderStatus));

        bookOrderDetailService.changeOrderStatusByOrderCode("orderCode", OrderStatusEnum.SHIPPING);

        assertEquals(bookOrderStatus, detail.getBookOrderStatus());

        verify(bookOrderDetailRepository, times(1)).findOrderDetailByOrderCode("orderCode");
        verify(bookOrderStatusRepository, times(1)).findById(any());
    }
}