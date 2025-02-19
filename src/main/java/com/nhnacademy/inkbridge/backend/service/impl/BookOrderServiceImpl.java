package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberPointReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.OrderMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookOrderRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.service.BookOrderService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: BookOrderServiceImpl.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookOrderServiceImpl implements BookOrderService {

    private final BookOrderRepository bookOrderRepository;
    private final MemberRepository memberRepository;

    /**
     * {@inheritDoc}
     *
     * @param requestDto 주문 정보
     * @return 주문 번호
     */
    @Override
    public OrderCreateResponseDto createBookOrder(BookOrderCreateRequestDto requestDto) {

        BookOrder bookOrder = BookOrder.builder()
            .orderCode(UUID.randomUUID().toString().replace("-", ""))
            .orderName(requestDto.getOrderName())
            .orderAt(LocalDateTime.now())
            .receiver(requestDto.getReceiverName())
            .receiverNumber(requestDto.getReceiverPhoneNumber())
            .zipCode(requestDto.getZipCode())
            .address(requestDto.getAddress())
            .addressDetail(requestDto.getDetailAddress())
            .orderer(requestDto.getSenderName())
            .ordererNumber(requestDto.getSenderPhoneNumber())
            .ordererEmail(requestDto.getSenderEmail())
            .deliveryDate(requestDto.getDeliveryDate())
            .usePoint(requestDto.getUsePoint())
            .totalPrice(requestDto.getPayAmount())
            .deliveryPrice(requestDto.getDeliveryPrice())
            .isPayment(false)
            .member(Objects.nonNull(requestDto.getMemberId())
                ? memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new NotFoundException(
                    MemberMessageEnum.MEMBER_NOT_FOUND.getMessage())) : null)
            .build();

        bookOrder = bookOrderRepository.save(bookOrder);

        return new OrderCreateResponseDto(bookOrder.getOrderId(), bookOrder.getOrderCode());
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 주문 결제 정보
     */
    @Transactional(readOnly = true)
    @Override
    public OrderPayInfoReadResponseDto getOrderPaymentInfoByOrderCode(String orderCode) {
        return bookOrderRepository.findOrderPayByOrderCode(orderCode).orElseThrow(
            () -> new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage())
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 코드
     * @return 주문 결제 정보
     */
    @Transactional(readOnly = true)
    @Override
    public OrderPayInfoReadResponseDto getOrderPaymentInfoByOrderId(Long orderId) {
        return bookOrderRepository.findOrderPayByOrderId(orderId).orElseThrow(
            () -> new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage())
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     */
    @Override
    public void updateBookOrderPayStatusByOrderCode(String orderCode) {
        BookOrder bookOrder = bookOrderRepository.findByOrderCode(orderCode).orElseThrow(
            () -> new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage()));

        bookOrder.updateStatus();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 번호
     * @return 주문 사용 포인트
     */
    @Transactional(readOnly = true)
    @Override
    public OrderedMemberPointReadResponseDto getOrderedPersonByOrderCode(String orderCode) {
        return bookOrderRepository.findUsedPointByOrderCode(orderCode);
    }

    @Override
    public OrderedMemberReadResponseDto getOrderedPersonByOrderId(Long orderId) {
        return bookOrderRepository.findUsedPointByOrderId(orderId);
    }

    /**
     * {@inheritDoc}
     *
     * @param memberId 회원 번호
     * @param pageable 페이지 정보
     * @return 주문 목록
     */
    @Transactional(readOnly = true)
    @Override
    public Page<OrderReadResponseDto> getOrderListByMemberId(Long memberId, Pageable pageable) {
        return bookOrderRepository.findOrderByMemberId(memberId, pageable);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 번호
     * @return 주문 정보
     */
    @Transactional(readOnly = true)
    @Override
    public OrderResponseDto getOrderByOrderId(Long orderId) {
        return bookOrderRepository.findOrderByOrderId(orderId)
            .orElseThrow(() -> new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage()));
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 번호
     * @return 주문 내역
     */
    @Override
    public OrderResponseDto getOrderByOrderCode(String orderCode) {
        return bookOrderRepository.findOrderByOrderCode(orderCode)
            .orElseThrow(() -> new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage()));
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable 페이지 정보
     * @return 전체 주문목록 페이지
     */
    @Transactional(readOnly = true)
    @Override
    public Page<OrderReadResponseDto> getOrderList(Pageable pageable) {
        return bookOrderRepository.findOrderBy(pageable);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 번호
     */
    @Override
    public void updateOrderShipDate(Long orderId) {
        BookOrder bookOrder = bookOrderRepository.findById(orderId).orElseThrow(
            () -> new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage()));

        bookOrder.updateShipDate(LocalDate.now());
    }


}
