package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoriesDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.MemberCouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import com.nhnacademy.inkbridge.backend.entity.QBookCoupon;
import com.nhnacademy.inkbridge.backend.entity.QCategoryCoupon;
import com.nhnacademy.inkbridge.backend.entity.QCoupon;
import com.nhnacademy.inkbridge.backend.entity.QMemberCoupon;
import com.nhnacademy.inkbridge.backend.repository.custom.MemberCouponCustomRepository;
import com.querydsl.core.types.Projections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: BookRepositoryImpl.
 *
 * @author JBum
 * @version 2024/03/08
 */
public class MemberCouponRepositoryImpl extends QuerydslRepositorySupport implements
    MemberCouponCustomRepository {

    private static final int ISSUABLE_COUPON_STATUS_ID = 1;

    public MemberCouponRepositoryImpl() {
        super(MemberCoupon.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Set<MemberCouponReadResponseDto> findOrderCoupons(Long memberId,
        BookCategoriesDto bookCategoriesDto) {
        QCoupon coupon = QCoupon.coupon;
        QBookCoupon bookCoupon = QBookCoupon.bookCoupon;
        QCategoryCoupon categoryCoupon = QCategoryCoupon.categoryCoupon;
        QMemberCoupon memberCoupon = QMemberCoupon.memberCoupon;

        Set<MemberCouponReadResponseDto> result = new HashSet<>();

        result.addAll(from(memberCoupon)
            .leftJoin(coupon).on(memberCoupon.coupon.couponId.eq(coupon.couponId))
            .leftJoin(bookCoupon).on(coupon.couponId.eq(bookCoupon.coupon.couponId))
            .leftJoin(categoryCoupon).on(coupon.couponId.eq(categoryCoupon.coupon.couponId))
            .where(memberCoupon.member.memberId.eq(memberId)
                .and(memberCoupon.usedAt.isNull())
                .and(memberCoupon.coupon.couponStatus.couponStatusId.eq(ISSUABLE_COUPON_STATUS_ID))
                .and(bookCoupon.book.bookId.eq(bookCategoriesDto.getBookId())
                    .or(categoryCoupon.category.categoryId.in(bookCategoriesDto.getCategoryIds()))
                    .or(categoryCoupon.category.categoryId.isNull()
                        .and(bookCoupon.book.bookId.isNull()))))
            .select(Projections.constructor(MemberCouponReadResponseDto.class,
                memberCoupon.memberCouponId,
                memberCoupon.expiredAt,
                memberCoupon.usedAt,
                coupon.couponName,
                coupon.minPrice,
                coupon.discountPrice,
                coupon.maxDiscountPrice,
                coupon.couponType.couponTypeId,
                coupon.couponType.typeName,
                coupon.isBirth,
                coupon.couponStatus.couponStatusId,
                coupon.couponStatus.couponStatusName))
            .fetch());

        return result;
    }
}
