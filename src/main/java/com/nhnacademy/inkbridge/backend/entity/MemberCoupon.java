package com.nhnacademy.inkbridge.backend.entity;

import com.nhnacademy.inkbridge.backend.dto.coupon.MemberCouponReadResponseDto;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: memberCoupon.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member_coupon")
public class MemberCoupon {

    @Id
    @Column(name = "member_coupon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCouponId;

    @Column(name = "expired_at")
    private LocalDate expiredAt;

    @Column(name = "issued_at")
    private LocalDate issuedAt;

    @Column(name = "used_at")
    private LocalDate usedAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Builder
    public MemberCoupon(Long memberCouponId, LocalDate expiredAt, LocalDate issuedAt,
        LocalDate usedAt, Member member, Coupon coupon) {
        this.memberCouponId = memberCouponId;
        this.expiredAt = expiredAt;
        this.issuedAt = issuedAt;
        this.usedAt = usedAt;
        this.member = member;
        this.coupon = coupon;
    }

    public MemberCouponReadResponseDto toResponseDto() {
        return MemberCouponReadResponseDto.builder().couponName(this.getCoupon().getCouponName())
            .couponStatusId(this.coupon.getCouponStatus().getCouponStatusId())
            .couponStatusName(this.coupon.getCouponStatus().getCouponStatusName())
            .couponTypeId(this.getCoupon().getCouponType().getCouponTypeId())
            .couponTypeName(this.coupon.getCouponType().getTypeName())
            .memberCouponId(this.getMemberCouponId())
            .discountPrice(this.coupon.getDiscountPrice())
            .maxDiscountPrice(this.coupon.getMaxDiscountPrice()).minPrice(this.coupon.getMinPrice())
            .expiredAt(this.getExpiredAt()).isBirth(this.coupon.getIsBirth())
            .usedAt(this.getUsedAt()).build();
    }

    public void use() {
        this.usedAt = LocalDate.now();
    }

    public void cancel() {
        this.usedAt = null;
    }
}
