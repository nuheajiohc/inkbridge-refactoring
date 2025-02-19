package com.nhnacademy.inkbridge.backend.entity.enums;

/**
 * class: PointHistoryReason.
 *
 * @author devminseo
 * @version 3/19/24
 */
public enum PointHistoryReason {
    REGISTER_MSG("회원가입 축하금 지급"),
    REVIEW_MSG("리뷰 작성 포인트 지급"),
    PURCHASE_GOODS("상품 구매"),
    PAYMENT_CANCELLATION("결제 취소");

    private final String message;

    PointHistoryReason(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
