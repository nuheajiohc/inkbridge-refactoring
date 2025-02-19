package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.pay.PayReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Pay;
import com.nhnacademy.inkbridge.backend.entity.QBookOrder;
import com.nhnacademy.inkbridge.backend.entity.QPay;
import com.nhnacademy.inkbridge.backend.repository.custom.PayRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: PayRepositoryImpl.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
public class PayRepositoryImpl extends QuerydslRepositorySupport implements PayRepositoryCustom {


    public PayRepositoryImpl() {
        super(Pay.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param payId 결제 번호
     * @return 결제 정보
     */
    @Override
    public Optional<PayReadResponseDto> findPayByPayId(Long payId) {
        QPay pay = QPay.pay;
        return Optional.ofNullable(from(pay)
            .select(Projections.constructor(PayReadResponseDto.class,
                pay.paymentKey,
                pay.method,
                pay.status,
                pay.requestedAt,
                pay.totalAmount,
                pay.balanceAmount,
                pay.vat,
                pay.isPartialCancelable,
                pay.provider))
            .where(pay.payId.eq(payId))
            .fetchOne());
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 번호
     * @return 결제 정보
     */
    @Override
    public PayReadResponseDto findPayByOrderId(Long orderId) {
        QPay pay = QPay.pay;
        return from(pay)
            .select(Projections.constructor(PayReadResponseDto.class,
                pay.paymentKey,
                pay.method,
                pay.status,
                pay.requestedAt,
                pay.totalAmount,
                pay.balanceAmount,
                pay.vat,
                pay.isPartialCancelable,
                pay.provider))
            .where(pay.order.orderId.eq(orderId))
            .fetchOne();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 결제 정보
     */
    @Override
    public PayReadResponseDto findPayByOrderCode(String orderCode) {
        QPay pay = QPay.pay;
        return from(pay)
            .select(Projections.constructor(PayReadResponseDto.class,
                pay.paymentKey,
                pay.method,
                pay.status,
                pay.requestedAt,
                pay.totalAmount,
                pay.balanceAmount,
                pay.vat,
                pay.isPartialCancelable,
                pay.provider))
            .where(pay.order.orderCode.eq(orderCode))
            .fetchOne();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 결제 정보
     */
    @Override
    public Optional<Pay> findByOrderCode(String orderCode) {
        QPay pay = QPay.pay;
        QBookOrder bookOrder = QBookOrder.bookOrder;

        return Optional.ofNullable(from(pay)
            .select(pay)
                .innerJoin(pay.order, bookOrder)
            .where(bookOrder.orderCode.eq(orderCode))
            .fetchOne());
    }
}
