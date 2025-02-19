package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewAverageReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewDetailByMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewDetailOnBookReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.QBook;
import com.nhnacademy.inkbridge.backend.entity.QFile;
import com.nhnacademy.inkbridge.backend.entity.QMember;
import com.nhnacademy.inkbridge.backend.entity.QReview;
import com.nhnacademy.inkbridge.backend.entity.Review;
import com.nhnacademy.inkbridge.backend.repository.custom.ReviewRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 *  class: ReviewRepositoryImpl.
 *
 *  @author minm063
 *  @version 2024/03/19
 */
public class ReviewRepositoryImpl extends QuerydslRepositorySupport implements
    ReviewRepositoryCustom {

    private static final Double DEFAULT_AVG = 0.0;

    public ReviewRepositoryImpl() {
        super(Review.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ReviewDetailOnBookReadResponseDto> findByBookId(Pageable pageable, Long bookId) {
        QBook book = QBook.book;
        QReview review = QReview.review;
        QMember member = QMember.member;

        List<ReviewDetailOnBookReadResponseDto> content = from(book)
            .innerJoin(review).on(review.book.eq(book))
            .innerJoin(member).on(member.eq(review.member))
            .where(book.bookId.eq(bookId))
            .orderBy(review.reviewId.desc())
            .select(
                Projections.constructor(ReviewDetailOnBookReadResponseDto.class, review.reviewId,
                    member.email, review.reviewTitle, review.reviewContent.coalesce(""),
                    review.registeredAt, review.score))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(content, pageable, getCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ReviewDetailByMemberReadResponseDto> findByMemberId(Pageable pageable,
        Long memberId) {
        QMember member = QMember.member;
        QReview review = QReview.review;
        QBook book = QBook.book;
        QFile file = QFile.file;

        List<ReviewDetailByMemberReadResponseDto> content = from(member)
            .innerJoin(review).on(review.member.eq(member))
            .innerJoin(book).on(book.eq(review.book))
            .innerJoin(file).on(book.thumbnailFile.eq(file))
            .where(member.memberId.eq(memberId))
            .orderBy(review.reviewId.desc())
            .select(
                Projections.constructor(ReviewDetailByMemberReadResponseDto.class, review.reviewId,
                    review.reviewTitle, review.reviewContent, review.registeredAt, review.score,
                    book.bookId, book.bookTitle, file.fileUrl))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(content, pageable, getCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReviewAverageReadResponseDto avgReview(Long bookId) {
        QReview review = QReview.review;
        QBook book = QBook.book;

        return from(book)
            .innerJoin(review).on(review.book.bookId.eq(book.bookId))
            .where(book.bookId.eq(bookId))
            .select(
                Projections.constructor(ReviewAverageReadResponseDto.class,
                    review.score.avg().coalesce(DEFAULT_AVG), review.count()))
            .fetchOne();
    }

    /**
     * 리뷰 전체 개수를 조회하는 메서드입니다.
     *
     * @return Long count
     */
    private Long getCount() {
        QReview review = QReview.review;

        return from(review)
            .select(review.count())
            .fetchOne();
    }

}
