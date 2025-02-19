package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewAverageReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewDetailByMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewDetailOnBookReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.BookOrderDetail;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberStatus;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.entity.Review;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *  class: ReviewRepositoryTest.
 *
 *  @author minm063
 *  @version 2024/03/22
 */
@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ReviewRepository reviewRepository;

    Pageable pageable;
    Review review;
    Member member;
    Book book;

    @BeforeEach
    void setup() {
        pageable = PageRequest.of(0, 5);

        BookStatus status = BookStatus.builder().statusId(1L).statusName("status").build();
        Publisher publisher = Publisher.builder().publisherName("publisher").build();
        File file = File.builder().fileName("file").fileExtension("extension").fileExtension("png")
            .build();
        Author author = Author.builder().authorName("author").authorIntroduce("introduce")
            .file(file)
            .build();
        MemberStatus memberStatus = MemberStatus.create().memberStatusId(1).memberStatusName("")
            .build();

        status = testEntityManager.persist(status);
        publisher = testEntityManager.persist(publisher);
        file = testEntityManager.persist(file);
        memberStatus = testEntityManager.persist(memberStatus);
        author = testEntityManager.persist(author);

        book = Book.builder().bookTitle("title").bookIndex("index").description("description")
            .publicatedAt(LocalDate.parse("2024-03-06")).isbn("1234567890123").regularPrice(1L)
            .price(1L)
            .discountRatio(BigDecimal.valueOf(3.33)).stock(1).isPackagable(true)
            .bookStatus(status)
            .publisher(publisher).thumbnailFile(file).build();
        book = testEntityManager.persist(book);
        member = Member.create().memberName("member").build();
        member = testEntityManager.persist(member);
        BookOrder bookOrder = BookOrder.builder().member(member).build();
        bookOrder = testEntityManager.persist(bookOrder);
        BookOrderDetail bookOrderDetail = BookOrderDetail.builder().book(book).bookOrder(bookOrder)
            .build();
        bookOrderDetail = testEntityManager.persist(bookOrderDetail);
        review = Review.builder().reviewTitle("reviewTitle").reviewContent("reviewContent")
            .registeredAt(LocalDateTime.now()).score(5).book(book).bookOrderDetail(bookOrderDetail)
            .member(member).build();
        review = testEntityManager.persist(review);
    }

    @Test
    @DisplayName("도서 번호로 리뷰 조회")
    void findByBookId() {
        Page<ReviewDetailOnBookReadResponseDto> page = reviewRepository.findByBookId(pageable,
            book.getBookId());

        assertAll(
            () -> assertEquals(5, page.getSize()),
            () -> assertEquals(1, page.getContent().size()),
            () -> assertEquals(review.getReviewId(), page.getContent().get(0).getReviewId()),
            () -> assertEquals(review.getReviewTitle(), page.getContent().get(0).getReviewTitle()),
            () -> assertEquals(review.getReviewContent(),
                page.getContent().get(0).getReviewContent()),
            () -> assertEquals(review.getScore(), page.getContent().get(0).getScore()),
            () -> assertEquals(review.getRegisteredAt().toLocalDate(),
                page.getContent().get(0).getRegisteredAt())
        );
    }

    @Test
    @DisplayName("회원 번호로 리뷰 조회")
    void findByMemberId() {
        Page<ReviewDetailByMemberReadResponseDto> page = reviewRepository.findByMemberId(
            pageable, member.getMemberId());

        assertAll(
            () -> assertEquals(5, page.getSize()),
            () -> assertEquals(1, page.getContent().size()),
            () -> assertEquals(review.getReviewId(), page.getContent().get(0).getReviewId()),
            () -> assertEquals(review.getReviewTitle(), page.getContent().get(0).getReviewTitle()),
            () -> assertEquals(review.getReviewContent(),
                page.getContent().get(0).getReviewContent()),
            () -> assertEquals(review.getScore(), page.getContent().get(0).getScore()),
            () -> assertEquals(book.getBookId(), page.getContent().get(0).getBookId())
        );
    }

    @Test
    @DisplayName("리뷰 평점 평균 조회")
    void avgReview() {
        ReviewAverageReadResponseDto avgReview = reviewRepository.avgReview(book.getBookId());

        assertAll(
            () -> assertNotNull(avgReview),
            () -> assertEquals(avgReview.getAvg(), Double.valueOf(review.getScore())),
            () -> assertEquals(1, avgReview.getCount())
        );
    }
}