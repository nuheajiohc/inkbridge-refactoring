package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberPointReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.QBookOrder;
import com.nhnacademy.inkbridge.backend.repository.custom.BookOrderRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: BookOrderRepositoryImpl.
 *
 * @author jangjaehun
 * @version 2024/03/12
 */
public class BookOrderRepositoryImpl extends QuerydslRepositorySupport implements
    BookOrderRepositoryCustom {

    public BookOrderRepositoryImpl() {
        super(BookOrder.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 주문 결제 정보
     */
    @Override
    public Optional<OrderPayInfoReadResponseDto> findOrderPayByOrderCode(String orderCode) {
        QBookOrder bookOrder = QBookOrder.bookOrder;

        OrderPayInfoReadResponseDto orderPayInfoReadResponseDto = from(bookOrder)
            .select(Projections.constructor(OrderPayInfoReadResponseDto.class,
                bookOrder.orderCode,
                bookOrder.orderName,
                bookOrder.totalPrice))
            .where(bookOrder.orderCode.eq(orderCode))
            .fetchOne();
        return Optional.ofNullable(orderPayInfoReadResponseDto);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 코드
     * @return 주문 결제 정보
     */
    @Override
    public Optional<OrderPayInfoReadResponseDto> findOrderPayByOrderId(Long orderId) {
        QBookOrder bookOrder = QBookOrder.bookOrder;

        OrderPayInfoReadResponseDto orderPayInfoReadResponseDto = from(bookOrder)
            .select(Projections.constructor(OrderPayInfoReadResponseDto.class,
                bookOrder.orderCode,
                bookOrder.orderName,
                bookOrder.totalPrice))
            .where(bookOrder.orderId.eq(orderId))
            .fetchOne();
        return Optional.ofNullable(orderPayInfoReadResponseDto);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 사용한 포인트 정보
     */
    @Override
    public OrderedMemberPointReadResponseDto findUsedPointByOrderCode(String orderCode) {
        QBookOrder bookOrder = QBookOrder.bookOrder;

        return from(bookOrder)
            .select(Projections.constructor(OrderedMemberPointReadResponseDto.class,
                bookOrder.member.memberId,
                bookOrder.usePoint,
                bookOrder.totalPrice))
            .where(bookOrder.orderCode.eq(orderCode))
            .fetchOne();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 번호
     * @return 회원 주문 금액
     */
    @Override
    public OrderedMemberReadResponseDto findUsedPointByOrderId(Long orderId) {
        QBookOrder bookOrder = QBookOrder.bookOrder;

        return from(bookOrder)
            .select(Projections.constructor(OrderedMemberReadResponseDto.class,
                bookOrder.member.memberId,
                bookOrder.totalPrice))
            .where(bookOrder.orderId.eq(orderId))
            .fetchOne();
    }


    /**
     * {@inheritDoc}
     *
     * @param memberId 회원 번호
     * @param pageable 페이지 정보
     * @return 회원 주문 목록
     */
    @Override
    public Page<OrderReadResponseDto> findOrderByMemberId(Long memberId, Pageable pageable) {
        QBookOrder bookOrder = QBookOrder.bookOrder;

        List<OrderReadResponseDto> responseDtoList = from(bookOrder)
            .select(Projections.constructor(OrderReadResponseDto.class,
                bookOrder.orderId,
                bookOrder.orderCode,
                bookOrder.orderName,
                bookOrder.orderAt,
                bookOrder.deliveryDate,
                bookOrder.totalPrice))
            .where(bookOrder.member.memberId.eq(memberId))
            .orderBy(bookOrder.orderId.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        Long count = from(bookOrder)
            .select(bookOrder.count())
            .where(bookOrder.member.memberId.eq(memberId))
            .fetchOne();

        return new PageImpl<>(responseDtoList, pageable, count);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 번호
     * @return 주문 상세 내역
     */
    @Override
    public Optional<OrderResponseDto> findOrderByOrderId(Long orderId) {
        QBookOrder bookOrder = QBookOrder.bookOrder;

        return Optional.ofNullable(
            from(bookOrder)
                .select(Projections.constructor(OrderResponseDto.class,
                    bookOrder.orderCode,
                    bookOrder.orderName,
                    bookOrder.receiver,
                    bookOrder.receiverNumber,
                    bookOrder.zipCode,
                    bookOrder.address,
                    bookOrder.addressDetail,
                    bookOrder.orderer,
                    bookOrder.ordererNumber,
                    bookOrder.ordererEmail,
                    bookOrder.deliveryDate,
                    bookOrder.usePoint,
                    bookOrder.totalPrice,
                    bookOrder.deliveryPrice,
                    bookOrder.orderAt,
                    bookOrder.shipDate))
                .where(bookOrder.orderId.eq(orderId))
                .fetchOne());
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 주문 상세 내역
     */
    @Override
    public Optional<OrderResponseDto> findOrderByOrderCode(String orderCode) {
        QBookOrder bookOrder = QBookOrder.bookOrder;

        return Optional.ofNullable(from(bookOrder)
            .select(Projections.constructor(OrderResponseDto.class,
                bookOrder.orderCode,
                bookOrder.orderName,
                bookOrder.receiver,
                bookOrder.receiverNumber,
                bookOrder.zipCode,
                bookOrder.address,
                bookOrder.addressDetail,
                bookOrder.orderer,
                bookOrder.ordererNumber,
                bookOrder.ordererEmail,
                bookOrder.deliveryDate,
                bookOrder.usePoint,
                bookOrder.totalPrice,
                bookOrder.deliveryPrice,
                bookOrder.orderAt,
                bookOrder.shipDate))
            .where(bookOrder.orderCode.eq(orderCode))
            .fetchOne());
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable 페이지 정보
     * @return 주문 목록 페이지
     */
    @Override
    public Page<OrderReadResponseDto> findOrderBy(Pageable pageable) {
        QBookOrder bookOrder = QBookOrder.bookOrder;

        List<OrderReadResponseDto> content = from(bookOrder)
            .select(Projections.constructor(OrderReadResponseDto.class,
                bookOrder.orderId,
                bookOrder.orderCode,
                bookOrder.orderName,
                bookOrder.orderAt,
                bookOrder.deliveryDate,
                bookOrder.totalPrice))
            .orderBy(bookOrder.orderId.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        Long count = from(bookOrder)
            .select(bookOrder.count())
            .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }


}
