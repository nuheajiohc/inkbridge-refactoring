package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.book.BookIdNameReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.QBookCoupon;
import com.nhnacademy.inkbridge.backend.entity.QCategoryCoupon;
import com.nhnacademy.inkbridge.backend.entity.QCoupon;
import com.nhnacademy.inkbridge.backend.repository.custom.CouponCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: CouponRepositoryImpl.
 *
 * @author JBum
 * @version 2024/03/08
 */
public class CouponRepositoryImpl extends QuerydslRepositorySupport implements
    CouponCustomRepository {

    public CouponRepositoryImpl() {
        super(Coupon.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CouponDetailReadResponseDto> findDetailCoupon(String couponId) {
        QCoupon coupon = QCoupon.coupon;
        QBookCoupon bookCoupon = QBookCoupon.bookCoupon;
        QCategoryCoupon categoryCoupon = QCategoryCoupon.categoryCoupon;

        List<CategoryReadResponseDto> categories = from(categoryCoupon)
            .select(Projections.constructor(
                CategoryReadResponseDto.class,
                categoryCoupon.category.categoryId,
                categoryCoupon.category.categoryName))
            .where(categoryCoupon.coupon.couponId.eq(couponId))
            .fetch();

        List<BookIdNameReadResponseDto> books = from(bookCoupon)
            .select(Projections.constructor(
                BookIdNameReadResponseDto.class,
                bookCoupon.book.bookId,
                bookCoupon.book.bookTitle))
            .where(bookCoupon.coupon.couponId.eq(couponId))
            .fetch();

        CouponDetailReadResponseDto couponDetail = from(coupon)
            .where(coupon.couponId.eq(couponId))
            .select(Projections.constructor(
                CouponDetailReadResponseDto.class,
                coupon.couponId,
                coupon.couponName,
                coupon.minPrice,
                coupon.discountPrice,
                coupon.maxDiscountPrice,
                Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", coupon.basicIssuedDate),
                Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", coupon.basicExpiredDate),
                coupon.validity,
                coupon.couponType.typeName,
                coupon.isBirth,
                coupon.couponStatus.couponStatusName))
            .fetchFirst();

        if (couponDetail != null) {
            couponDetail.setRelation(categories, books);
        }

        return Optional.ofNullable(couponDetail);
    }
}
