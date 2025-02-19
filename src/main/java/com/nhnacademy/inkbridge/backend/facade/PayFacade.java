package com.nhnacademy.inkbridge.backend.facade;

import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberPointReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pay.PayCancelRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pay.PayCreateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.enums.PointHistoryReason;
import com.nhnacademy.inkbridge.backend.enums.OrderStatusEnum;
import com.nhnacademy.inkbridge.backend.exception.PaymentFailedException;
import com.nhnacademy.inkbridge.backend.service.BookOrderDetailService;
import com.nhnacademy.inkbridge.backend.service.BookOrderService;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import com.nhnacademy.inkbridge.backend.service.MemberPointService;
import com.nhnacademy.inkbridge.backend.service.PayService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: PayPacade.
 *
 * @author jangjaehun
 * @version 2024/03/16
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PayFacade {

    private final PayService payService;
    private final BookOrderService bookOrderService;
    private final BookOrderDetailService bookOrderDetailService;
    private final CouponService couponService;
    private final MemberPointService memberPointService;

    /**
     * 결제 정보를 저장하고 결제를 진행합니다.
     *
     * @param requestDto 결제 정보
     */
    public void doPay(PayCreateRequestDto requestDto) {

        try {
            payService.createPay(requestDto);
        } catch (Exception e) {
            throw new PaymentFailedException(e.getMessage());
        }

        bookOrderService.updateBookOrderPayStatusByOrderCode(requestDto.getOrderCode());

        OrderedMemberPointReadResponseDto orderedResponseDto = bookOrderService.getOrderedPersonByOrderCode(
            requestDto.getOrderCode());
        if (Objects.nonNull(orderedResponseDto.getMemberId())) {

            memberPointService.memberPointUpdate(orderedResponseDto.getMemberId(),
                orderedResponseDto.getUsePoint() * -1, PointHistoryReason.PURCHASE_GOODS);

            List<Long> usedCouponIdList = bookOrderDetailService.getUsedCouponIdByOrderCode(
                requestDto.getOrderCode());
            couponService.useCoupons(orderedResponseDto.getMemberId(), usedCouponIdList);
        }
    }

    /**
     * 결제 취소를 진행하는 메소드입니다.
     *
     * @param requestDto 취소 정보
     */
    public void cancelPay(PayCancelRequestDto requestDto) {
        payService.cancelPay(requestDto);
        bookOrderDetailService.changeOrderStatusByOrderCode(requestDto.getOrderCode(),
            OrderStatusEnum.WITHDRAW);

        OrderedMemberPointReadResponseDto orderedResponseDto = bookOrderService.getOrderedPersonByOrderCode(
            requestDto.getOrderCode());
        if (Objects.nonNull(orderedResponseDto.getMemberId())) {

            memberPointService.memberPointUpdate(orderedResponseDto.getMemberId(),
                orderedResponseDto.getUsePoint(), PointHistoryReason.PAYMENT_CANCELLATION);

            List<Long> usedCouponIdList = bookOrderDetailService.getUsedCouponIdByOrderCode(
                requestDto.getOrderCode());
            couponService.cancelCouponUsage(orderedResponseDto.getMemberId(), usedCouponIdList);
        }
    }
}
