package com.nhnacademy.inkbridge.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookOrderDetail.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_order_detail")
public class BookOrderDetail {

    @Id
    @Column(name = "order_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    @Column(name = "book_price")
    private Long bookPrice;

    @Column(name = "wrapping_price")
    private Long wrappingPrice;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private BookOrderStatus bookOrderStatus;

    @ManyToOne
    @JoinColumn(name = "wrapping_id")
    private Wrapping wrapping;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private BookOrder bookOrder;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToOne
    @JoinColumn(name = "member_coupon_id")
    private MemberCoupon memberCoupon;

    @Builder
    public BookOrderDetail(Long orderDetailId, Long bookPrice, Long wrappingPrice, Integer amount,
        BookOrderStatus bookOrderStatus, Wrapping wrapping, BookOrder bookOrder, Book book,
        MemberCoupon memberCoupon) {
        this.orderDetailId = orderDetailId;
        this.bookPrice = bookPrice;
        this.wrappingPrice = wrappingPrice;
        this.amount = amount;
        this.bookOrderStatus = bookOrderStatus;
        this.wrapping = wrapping;
        this.bookOrder = bookOrder;
        this.book = book;
        this.memberCoupon = memberCoupon;
    }

    public void updateStatus(BookOrderStatus bookOrderStatus) {
        this.bookOrderStatus = bookOrderStatus;
    }
}
