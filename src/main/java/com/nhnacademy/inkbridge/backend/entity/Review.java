package com.nhnacademy.inkbridge.backend.entity;

import java.time.LocalDateTime;
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
 * class: Review.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToOne
    @JoinColumn(name = "order_detail_id")
    private BookOrderDetail bookOrderDetail;

    @Column(name = "review_title")
    private String reviewTitle;

    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "score")
    private Integer score;

    @Builder
    public Review(Member member, Book book, BookOrderDetail bookOrderDetail, String reviewTitle,
        String reviewContent, LocalDateTime registeredAt, Integer score) {
        this.member = member;
        this.book = book;
        this.bookOrderDetail = bookOrderDetail;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.registeredAt = registeredAt;
        this.score = score;
    }

    /**
     * 리뷰를 수정합니다.
     *
     * @param reviewTitle review title
     * @param reviewContent review content
     * @param registeredAt review registeredAt
     * @param score review score
     */
    public void updateReview(String reviewTitle, String reviewContent, LocalDateTime registeredAt,
        Integer score) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.registeredAt = registeredAt;
        this.score = score;
    }
}
