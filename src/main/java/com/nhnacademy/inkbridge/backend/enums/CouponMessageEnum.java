package com.nhnacademy.inkbridge.backend.enums;

/**
 * class: CouponMessageEnum.
 *
 * @author JBum
 * @version 2024/02/15
 */
public enum CouponMessageEnum {
    COUPON_NOT_FOUND("존재하지 않는 쿠폰입니다."),
    COUPON_ID("CouponId: "),
    COUPON_VALIDATION_ERROR("쿠폰 생성 규칙이 맞지 않습니다."),
    COUPON_TYPE_NOT_FOUND("CouponType을 찾을 수 없습니다."),
    COUPON_TYPE_ID("CouponTypeId: "),
    COUPON_STATUS_NOT_FOUND("존재하지 않는 쿠폰 상태입니다"),
    COUPON_ISSUED_EXIST("해당 유저는 이미 발급받은 쿠폰입니다"),
    COUPON_ISSUE_PERIOD_NOT_STARTED("쿠폰 발급 시작 기간이 아닙니다."),
    COUPON_ISSUE_PERIOD_EXPIRED("쿠폰 발급 기간이 지났습니다."),
    COUPON_ALREADY_USED("이미 사용된 쿠폰입니다."),
    COUPON_ALREADY_NOT_USED("아직 사용되지 않은 쿠폰입니다."),

    COUPON_DUPLICATED("이미 생산된 쿠폰입니다"),
    COUPON_CANNOT_CREATE_PAST_DATE("과거 날짜의 쿠폰은 생성할 수 없습니다.");
    private final String message;

    CouponMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
