package com.nhnacademy.inkbridge.backend.service;


import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberPointReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: BookOrderService.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
public interface BookOrderService {

    /**
     * 주문을 생성하는 메소드입니다.
     *
     * @param requestDto 주문 정보
     * @return 주문 번호
     */
    OrderCreateResponseDto createBookOrder(BookOrderCreateRequestDto requestDto);

    /**
     * 주문 결제 정보를 조회하는 메소드입니다.
     *
     * @param orderCode 주문 번호
     * @return 주문 결제 정보
     */
    OrderPayInfoReadResponseDto getOrderPaymentInfoByOrderCode(String orderCode);

    /**
     * 주문 결제 정보를 조회하는 메소드입니다.
     *
     * @param orderId 주문 코드
     * @return 주문 결제 정보
     */
    OrderPayInfoReadResponseDto getOrderPaymentInfoByOrderId(Long orderId);

    /**
     * 주문의 결제 상태를 변경하는 메소드입니다.
     *
     * @param orderCode 주문 코드
     */
    void updateBookOrderPayStatusByOrderCode(String orderCode);

    /**
     * 결제한 회원 정보와 사용한 포인트 정보를 조회하는 메소드입니다.
     *
     * @param orderCode 주문 번호
     * @return 회원 번호, 사용 포인트
     */
    OrderedMemberPointReadResponseDto getOrderedPersonByOrderCode(String orderCode);

    /**
     * 결제한 회원 정보와 사용한 포인트 정보를 조회하는 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return 회원 번호, 사용 포인트
     */
    OrderedMemberReadResponseDto getOrderedPersonByOrderId(Long orderId);


    /**
     * 회원 주문 목록을 조회하는 메소드입니다.
     *
     * @param memberId 회원 번호
     * @param pageable 페이지 정보
     * @return 주문 목록
     */
    Page<OrderReadResponseDto> getOrderListByMemberId(Long memberId, Pageable pageable);

    /**
     * 주문 내역을 조회하는 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return 주문 내역
     */
    OrderResponseDto getOrderByOrderId(Long orderId);

    /**
     * 주문 내역을 조회하는 메소드입니다.
     *
     * @param orderCode 주문 번호
     * @return 주문 내역
     */
    OrderResponseDto getOrderByOrderCode(String orderCode);


    /**
     * 전체 주문 목록을 조회하는 메소드입니다.
     *
     * @param pageable 페이지 정보
     * @return 주문 목록 페이지
     */
    Page<OrderReadResponseDto> getOrderList(Pageable pageable);

    /**
     * 주문의 출고일을 설정합니다.
     *
     * @param orderId 주문 번호
     */
    void updateOrderShipDate(Long orderId);

}
