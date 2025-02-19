package com.nhnacademy.inkbridge.backend.facade;

import com.nhnacademy.inkbridge.backend.dto.order.BookOrderDetailResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderBooksIdResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pay.PayReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.enums.PointHistoryReason;
import com.nhnacademy.inkbridge.backend.enums.OrderStatusEnum;
import com.nhnacademy.inkbridge.backend.service.AccumulationRatePolicyService;
import com.nhnacademy.inkbridge.backend.service.BookOrderDetailService;
import com.nhnacademy.inkbridge.backend.service.BookOrderService;
import com.nhnacademy.inkbridge.backend.service.MemberPointService;
import com.nhnacademy.inkbridge.backend.service.PayService;
import com.nhnacademy.inkbridge.backend.service.ReviewService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: OrderFacade.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OrderFacade {

    private final BookOrderService bookOrderService;
    private final BookOrderDetailService bookOrderDetailService;
    private final PayService payService;
    private final MemberPointService memberPointService;
    private final AccumulationRatePolicyService accumulationRatePolicyService;
    private final ReviewService reviewService;


    /**
     * 주문 , 주문 상세 테이블에 정보를 저장합니다.
     *
     * @param requestDto 주문 정보
     * @return 주문 번호
     */
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto requestDto) {
        OrderCreateResponseDto responseDto = bookOrderService.createBookOrder(
            requestDto.getBookOrder());
        bookOrderDetailService.createBookOrderDetail(responseDto.getOrderId(),
            requestDto.getBookOrderList());

        return responseDto;
    }

    /**
     * 주문 결제 정보를 조회하는 메소드입니다.
     *
     * @param orderCode 주문 코드
     * @return 주문 결제 정보
     */
    @Transactional(readOnly = true)
    public OrderPayInfoReadResponseDto getOrderPaymentInfo(String orderCode) {
        return bookOrderService.getOrderPaymentInfoByOrderCode(orderCode);
    }


    /**
     * 회원 주문 목록을 조회하는 메소드입니다.
     *
     * @param memberId 회원 번호
     * @param pageable 페이지 정보
     * @return 회원 주문 목록
     */
    @Transactional(readOnly = true)
    public Page<OrderReadResponseDto> getOrderListByMemberId(Long memberId, Pageable pageable) {
        return bookOrderService.getOrderListByMemberId(memberId, pageable);
    }

    /**
     * 회원 주문 상세 내역을 조회하는 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return 주문 상세 내역
     */
    @Transactional(readOnly = true)
    public BookOrderDetailResponseDto getOrderDetailByOrderId(Long orderId) {
        OrderResponseDto responseDto = bookOrderService.getOrderByOrderId(orderId);
        List<OrderDetailReadResponseDto> detailResponseDtoList = bookOrderDetailService.getOrderDetailListByOrderId(
            orderId);
        PayReadResponseDto payResponseDto = payService.getPayByOrderId(orderId);
        return BookOrderDetailResponseDto.builder()
            .orderInfo(responseDto)
            .payInfo(payResponseDto)
            .orderDetailInfoList(detailResponseDtoList)
            .build();
    }

    /**
     * 전체 주문 목록을 조회하는 메소드입니다.
     *
     * @param pageable 페이지 정보
     * @return 주문 목록
     */
    @Transactional(readOnly = true)
    public Page<OrderReadResponseDto> getOrderList(Pageable pageable) {
        return bookOrderService.getOrderList(pageable);
    }

    /**
     * 주문 상세 내역을 조회하는 메소드입니다.
     *
     * @param orderCode 주문 코드
     * @return 주문 상세 내역
     */
    @Transactional(readOnly = true)
    public BookOrderDetailResponseDto getOrderDetailByOrderCode(String orderCode) {
        OrderResponseDto responseDto = bookOrderService.getOrderByOrderCode(orderCode);
        List<OrderDetailReadResponseDto> detailResponseDtoList = bookOrderDetailService.getOrderDetailByOrderCode(
            orderCode);
        PayReadResponseDto payResponseDto = payService.getPayByOrderCode(orderCode);
        Map<Long, Boolean> reviewed = reviewService.isReviewed(
            detailResponseDtoList.stream().map(OrderDetailReadResponseDto::getOrderDetailId)
                .collect(Collectors.toList()));

        return BookOrderDetailResponseDto.builder()
            .orderInfo(responseDto)
            .payInfo(payResponseDto)
            .orderDetailInfoList(detailResponseDtoList)
            .isReviewed(reviewed)
            .build();
    }


    /**
     * 주문 상세 내역의 주문 상태를 변경하는 메소드입니다. <br/> 주문 상태를 배송중으로 변경할 경우 출고일을 설정하고 회원인 경우 포인트를 적립합니다.
     *
     * @param orderId         주문 번호
     * @param orderStatusEnum 주문 상태
     */
    public void updateStatus(Long orderId, OrderStatusEnum orderStatusEnum) {
        bookOrderDetailService.changeOrderStatus(orderId, orderStatusEnum);

        if (orderStatusEnum == OrderStatusEnum.SHIPPING) {
            bookOrderService.updateOrderShipDate(orderId);

            OrderedMemberReadResponseDto orderedResponseDto =
                bookOrderService.getOrderedPersonByOrderId(orderId);

            if (Objects.nonNull(orderedResponseDto.getMemberId())) {

                Integer rate = accumulationRatePolicyService.getCurrentAccumulationRate();

                memberPointService.memberPointUpdate(orderedResponseDto.getMemberId(),
                    Math.round((double) orderedResponseDto.getTotalPrice() * rate) / 100,
                    PointHistoryReason.PURCHASE_GOODS);
            }
        }
    }

    /**
     * 주문 코드에 맞는 도서 번호를 조회합니다.
     *
     * @param orderCode 주문 코드
     * @return 도서 번호
     */
    public List<OrderBooksIdResponseDto> getOrderBookIdList(String orderCode) {
        return bookOrderDetailService.getOrderBooksIdByOrderId(orderCode);
    }
}
