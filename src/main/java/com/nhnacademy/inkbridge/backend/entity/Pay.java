package com.nhnacademy.inkbridge.backend.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: Pay.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "pay")
public class Pay {

    @Id
    @Column(name = "pay_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payId;

    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "method")
    private String method;

    @Column(name = "status")
    private String status;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "total_Amount")
    private Long totalAmount;

    @Column(name = "balance_Amount")
    private Long balanceAmount;

    @Column(name = "vat")
    private Long vat;

    @Column(name = "is_partial_cancelable")
    private Boolean isPartialCancelable;

    @Column(name = "provider")
    private String provider;

    @OneToOne
    @JoinColumn(name = "order_id")
    private BookOrder order;

    @Builder
    public Pay(String paymentKey, String method, String status, LocalDateTime requestedAt,
        LocalDateTime approvedAt, Long totalAmount, Long balanceAmount, Long vat,
        Boolean isPartialCancelable, String provider, BookOrder order) {
        this.paymentKey = paymentKey;
        this.method = method;
        this.status = status;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.totalAmount = totalAmount;
        this.balanceAmount = balanceAmount;
        this.vat = vat;
        this.isPartialCancelable = isPartialCancelable;
        this.provider = provider;
        this.order = order;
    }

    /**
     * 결제 상태, 결제 금액, 취소 가능 금액을 변경합니다.
     * @param status
     * @param totalAmount
     * @param balanceAmount
     * @param isPartialCancelable
     */
    public void updatePay(String status, Long totalAmount, Long balanceAmount, Boolean isPartialCancelable) {
        this.status = status;
        this.totalAmount = totalAmount;
        this.balanceAmount = balanceAmount;
        this.isPartialCancelable = isPartialCancelable;
    }
}
