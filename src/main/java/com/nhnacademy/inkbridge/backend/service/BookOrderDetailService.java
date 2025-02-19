package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.book.BookStockUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderBooksIdResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderDetailCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.OrderStatusEnum;
import java.util.List;

/**
 * class: BookOrderDetailService.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
public interface BookOrderDetailService {

    /**
     * 주문 상세를 생성하는 메소드입니다.
     *
     * @param orderId        주문 번호
     * @param requestDtoList 주문 상세 정보 목록
     */
    void createBookOrderDetail(Long orderId, List<BookOrderDetailCreateRequestDto> requestDtoList);

    /**
     * 주문의 주문 상세를 조회하는 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return 주문 상세 정보 목록
     */
    List<OrderDetailReadResponseDto> getOrderDetailListByOrderId(Long orderId);

    /**
     * 주문 상세 내역을 조회하는 메소드입니다.
     *
     * @param orderCode 주문 코드
     * @return 주문 상세 내역
     */
    List<OrderDetailReadResponseDto> getOrderDetailByOrderCode(String orderCode);

    /**
     * 주문 상세의 상태를 변경하는 메소드입니다.
     *
     * @param orderId 주문번호
     * @param status  주문 상태
     */
    void changeOrderStatus(Long orderId, OrderStatusEnum status);

    /**
     * 주문 상세의 상태를 변경하는 메소드입니다.
     *
     * @param orderCode 주문 코드
     * @param status    주문 상태
     */
    void changeOrderStatusByOrderCode(String orderCode, OrderStatusEnum status);

    /**
     * 사용한 쿠폰 번호 목록을 조회합니다.
     *
     * @param orderCode 주문 코드
     * @return 사용한 쿠폰 번호 목록
     */
    List<Long> getUsedCouponIdByOrderCode(String orderCode);

    /**
     * 도서 번호와 주문 수량을 조회합니다.
     *
     * @param orderCode 주문 코드
     * @return 도서 수량 목록
     */
    List<BookStockUpdateRequestDto> getBookStock(String orderCode);

    /**
     * 주문한 도서 번호를 조회하는 메소드입니다.
     *
     * @param orderCode 주문 코드
     * @return 도서 번호 목록
     */
    List<OrderBooksIdResponseDto> getOrderBooksIdByOrderId(String orderCode);
}

