package com.nhnacademy.inkbridge.backend.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: CategoryCoupon.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "category_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryCoupon {

    @EmbeddedId
    private Pk pk;

    @MapsId("categoryId")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @MapsId("couponId")
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Builder
    public CategoryCoupon(Pk pk, Category category, Coupon coupon) {
        this.pk = pk;
        this.category = category;
        this.coupon = coupon;
    }

    /**
     * class: CategoryCoupon.Pk.
     *
     * @author minseo
     * @version 2/8/24
     */
    @Embeddable
    @EqualsAndHashCode
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Pk implements Serializable {

        @Column(name = "category_id")
        private Long categoryId;

        @Column(name = "coupon_id")
        private String couponId;

        @Builder
        public Pk(Long categoryId, String couponId) {
            this.categoryId = categoryId;
            this.couponId = couponId;
        }
    }
}
