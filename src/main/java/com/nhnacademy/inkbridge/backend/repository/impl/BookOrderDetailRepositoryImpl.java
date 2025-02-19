package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.book.BookStockUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrderDetail;
import com.nhnacademy.inkbridge.backend.entity.QBook;
import com.nhnacademy.inkbridge.backend.entity.QBookOrder;
import com.nhnacademy.inkbridge.backend.entity.QBookOrderDetail;
import com.nhnacademy.inkbridge.backend.entity.QBookOrderStatus;
import com.nhnacademy.inkbridge.backend.entity.QCoupon;
import com.nhnacademy.inkbridge.backend.entity.QCouponType;
import com.nhnacademy.inkbridge.backend.entity.QFile;
import com.nhnacademy.inkbridge.backend.entity.QMemberCoupon;
import com.nhnacademy.inkbridge.backend.entity.QWrapping;
import com.nhnacademy.inkbridge.backend.repository.custom.BookOrderDetailRepositoryCustom;
import com.nhnacademy.inkbridge.backend.dto.order.OrderBooksIdResponseDto;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: BookOrderDetailRepositoryImpl.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
public class BookOrderDetailRepositoryImpl extends QuerydslRepositorySupport implements
    BookOrderDetailRepositoryCustom {

    public BookOrderDetailRepositoryImpl() {
        super(BookOrderDetail.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 사용한 쿠폰 목록
     */
    @Override
    public List<Long> findAllByOrderCode(String orderCode) {
        QBookOrderDetail bookOrderDetail = QBookOrderDetail.bookOrderDetail;
        QBookOrder bookOrder = QBookOrder.bookOrder;

        return from(bookOrderDetail)
            .innerJoin(bookOrder)
            .on(bookOrderDetail.bookOrder.eq(bookOrder))
            .where(bookOrder.orderCode.eq(orderCode)
                .and(bookOrderDetail.memberCoupon.isNotNull()))
            .select(bookOrderDetail.memberCoupon.memberCouponId)
            .fetch();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 번호
     * @return 주문 상세 목록
     */
    @Override
    public List<OrderDetailReadResponseDto> findAllOrderDetailByOrderId(Long orderId) {
        QBook book = QBook.book;
        QBookOrderDetail bookOrderDetail = QBookOrderDetail.bookOrderDetail;
        QCoupon coupon = QCoupon.coupon;
        QMemberCoupon memberCoupon = QMemberCoupon.memberCoupon;
        QCouponType couponType = QCouponType.couponType;
        QBookOrderStatus bookOrderStatus = QBookOrderStatus.bookOrderStatus;
        QWrapping wrapping = QWrapping.wrapping;
        QFile file = QFile.file;

        return from(bookOrderDetail)
            .innerJoin(bookOrderStatus)
            .on(bookOrderDetail.bookOrderStatus.eq(bookOrderStatus))
            .innerJoin(book)
            .on(bookOrderDetail.book.eq(book))
            .innerJoin(file)
            .on(book.thumbnailFile.eq(file))
            .leftJoin(wrapping)
            .on(bookOrderDetail.wrapping.eq(wrapping))
            .leftJoin(memberCoupon)
            .on(bookOrderDetail.memberCoupon.eq(memberCoupon))
            .leftJoin(coupon)
            .on(memberCoupon.coupon.eq(coupon))
            .leftJoin(coupon.couponType, couponType)
            .select(Projections.constructor(OrderDetailReadResponseDto.class,
                bookOrderDetail.orderDetailId,
                bookOrderDetail.bookPrice,
                bookOrderDetail.wrappingPrice,
                bookOrderDetail.amount,
                wrapping.wrappingName,
                bookOrderStatus.orderStatus,
                book.bookId,
                file.fileUrl,
                book.bookTitle,
                couponType.typeName,
                coupon.couponName,
                coupon.maxDiscountPrice,
                coupon.discountPrice))
            .where(bookOrderDetail.bookOrder.orderId.eq(orderId))
            .orderBy(bookOrderDetail.orderDetailId.asc())
            .fetch();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 주문 상세 목록
     */
    @Override
    public List<OrderDetailReadResponseDto> findAllOrderDetailByOrderCode(String orderCode) {
        QBook book = QBook.book;
        QBookOrderDetail bookOrderDetail = QBookOrderDetail.bookOrderDetail;
        QCoupon coupon = QCoupon.coupon;
        QCouponType couponType = QCouponType.couponType;
        QMemberCoupon memberCoupon = QMemberCoupon.memberCoupon;
        QBookOrderStatus bookOrderStatus = QBookOrderStatus.bookOrderStatus;
        QWrapping wrapping = QWrapping.wrapping;
        QFile file = QFile.file;

        return from(bookOrderDetail)
            .innerJoin(bookOrderStatus)
            .on(bookOrderDetail.bookOrderStatus.eq(bookOrderStatus))
            .innerJoin(book)
            .on(bookOrderDetail.book.eq(book))
            .innerJoin(file)
            .on(book.thumbnailFile.eq(file))
            .leftJoin(wrapping)
            .on(bookOrderDetail.wrapping.eq(wrapping))
            .leftJoin(memberCoupon)
            .on(bookOrderDetail.memberCoupon.eq(memberCoupon))
            .leftJoin(coupon)
            .on(memberCoupon.coupon.eq(coupon))
            .leftJoin(coupon.couponType, couponType)
            .select(Projections.constructor(OrderDetailReadResponseDto.class,
                bookOrderDetail.orderDetailId,
                bookOrderDetail.bookPrice,
                bookOrderDetail.wrappingPrice,
                bookOrderDetail.amount,
                wrapping.wrappingName,
                bookOrderStatus.orderStatus,
                book.bookId,
                file.fileUrl,
                book.bookTitle,
                couponType.typeName,
                coupon.couponName,
                coupon.maxDiscountPrice,
                coupon.discountPrice))
            .where(bookOrderDetail.bookOrder.orderCode.eq(orderCode))
            .orderBy(bookOrderDetail.orderDetailId.asc())
            .fetch();

    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 번호
     * @return 주문 상세 목록
     */
    @Override
    public List<BookOrderDetail> findOrderDetailByOrderId(Long orderId) {
        QBookOrderDetail bookOrderDetail = QBookOrderDetail.bookOrderDetail;

        return from(bookOrderDetail)
            .select(bookOrderDetail)
            .where(bookOrderDetail.bookOrder.orderId.eq(orderId))
            .fetch();
    }

    @Override
    public List<BookOrderDetail> findOrderDetailByOrderCode(String orderCode) {
        QBookOrderDetail bookOrderDetail = QBookOrderDetail.bookOrderDetail;

        return from(bookOrderDetail)
            .select(bookOrderDetail)
            .where(bookOrderDetail.bookOrder.orderCode.eq(orderCode))
            .fetch();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 도서 번호 목록
     */
    @Override
    public List<OrderBooksIdResponseDto> findBookIdByOrderCode(String orderCode) {
        QBookOrderDetail bookOrderDetail = QBookOrderDetail.bookOrderDetail;

        return from(bookOrderDetail)
            .select(Projections.constructor(OrderBooksIdResponseDto.class,
                bookOrderDetail.book.bookId))
            .where(bookOrderDetail.bookOrder.orderCode.eq(orderCode))
            .fetch();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 주문 수량 목록
     */
    @Override
    public List<BookStockUpdateRequestDto> findBookStockByOrderCode(String orderCode) {
        QBookOrderDetail bookOrderDetail = QBookOrderDetail.bookOrderDetail;
        QBookOrder bookOrder = QBookOrder.bookOrder;

        return from(bookOrderDetail)
            .leftJoin(bookOrderDetail.bookOrder, bookOrder)
            .select(Projections.constructor(BookStockUpdateRequestDto.class,
                bookOrderDetail.book.bookId,
                bookOrderDetail.amount))
            .where(bookOrder.orderCode.eq(orderCode))
            .fetch();
    }
}

