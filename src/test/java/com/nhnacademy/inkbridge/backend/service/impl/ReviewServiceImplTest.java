package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewBookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewDetailByMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewDetailOnBookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookOrderDetail;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.Review;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.BookOrderDetailMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.ReviewMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookOrderDetailRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.FileRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.repository.ReviewFileRepository;
import com.nhnacademy.inkbridge.backend.repository.ReviewRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 *  class: ReviewServiceImplTest.
 *
 *  @author minm063
 *  @version 2024/03/22
 */
@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @InjectMocks
    ReviewServiceImpl reviewService;

    @Mock
    ReviewRepository reviewRepository;
    @Mock
    ReviewFileRepository reviewFileRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    BookOrderDetailRepository bookOrderDetailRepository;
    @Mock
    FileRepository fileRepository;
    @Mock
    Pageable pageable;

    ReviewCreateRequestDto reviewCreateRequestDto;
    ReviewUpdateRequestDto reviewUpdateRequestDto;
    List<File> files;

    @BeforeEach
    void setup() {
        reviewCreateRequestDto = ReviewCreateRequestDto.builder().bookId(1L).orderDetailId(1L)
            .reviewTitle("")
            .reviewContent("").score(1).build();
        reviewUpdateRequestDto = ReviewUpdateRequestDto.builder().reviewTitle("").reviewContent("")
            .score(1).build();
        files = List.of(File.builder().build());
    }

    @Test
    @DisplayName("회원 번호로 리뷰 조회")
    void getReviewsByMember() {
        when(memberRepository.existsById(anyLong())).thenReturn(true);
        when(reviewRepository.findByMemberId(any(Pageable.class), anyLong())).thenReturn(
            new PageImpl<>(List.of(
                ReviewDetailByMemberReadResponseDto.builder().reviewId(1L)
                    .registeredAt(LocalDateTime.now()).build())));
        when(fileRepository.getAllFileByReviewId(anyList())).thenReturn(new HashMap<>());
        when(reviewRepository.countByMember_MemberId(anyLong())).thenReturn(1L);

        ReviewMemberReadResponseDto reviewsByMember = reviewService.getReviewsByMember(pageable,
            1L);

        assertNotNull(reviewsByMember);
        verify(memberRepository, times(1)).existsById(anyLong());
        verify(reviewRepository, times(1)).findByMemberId(any(), anyLong());
        verify(fileRepository, times(1)).getAllFileByReviewId(anyList());
        verify(reviewRepository, times(1)).countByMember_MemberId(anyLong());
    }

    @Test
    @DisplayName("회원 번호로 리뷰 조회 - 회원 검증 실패 ")
    void givenInvalidMember_whenGetReviewsByMember_thenThrowNotFoundException() {
        when(memberRepository.existsById(anyLong())).thenReturn(false);

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> reviewService.getReviewsByMember(pageable, 1L));
        assertEquals(notFoundException.getMessage(),
            MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("도서 번호로 리뷰 조회")
    void getReviewsByBookId() {
        when(reviewRepository.findByBookId(any(Pageable.class), anyLong())).thenReturn(
            new PageImpl<>(List.of(
                ReviewDetailOnBookReadResponseDto.builder().reviewId(1L)
                    .registeredAt(LocalDateTime.now()).build())));
        when(fileRepository.getAllFileByReviewId(anyList())).thenReturn(new HashMap<>());

        ReviewBookReadResponseDto reviewsByBookId = reviewService.getReviewsByBookId(pageable, 1L);

        assertNotNull(reviewsByBookId);
        verify(reviewRepository, times(1)).findByBookId(pageable, 1L);
        verify(fileRepository, times(1)).getAllFileByReviewId(anyList());
    }

    @Test
    @DisplayName("리뷰 작성 여부 조회")
    void isReviewed() {
        when(reviewRepository.existsByBookOrderDetail_OrderDetailId(anyLong())).thenReturn(true);

        Map<Long, Boolean> reviewed = reviewService.isReviewed(List.of(1L));

        assertEquals(1, reviewed.size());
    }

    @Test
    @DisplayName("리뷰 생성")
    void createReview() {
        when(memberRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Member.class)));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(bookOrderDetailRepository.findById(anyLong())).thenReturn(Optional.of(mock(
            BookOrderDetail.class)));
        when(reviewRepository.save(any(Review.class))).thenReturn(mock(Review.class));
        when(reviewFileRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        reviewService.createReview(1L,
            ReviewCreateRequestDto.builder().bookId(1L).orderDetailId(1L).reviewTitle("")
                .reviewContent("").score(1).build(), List.of(
                File.builder().fileId(1L).build()));

        verify(memberRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookOrderDetailRepository, times(1)).findById(anyLong());
        verify(reviewRepository, times(1)).save(any());
        verify(reviewFileRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("리뷰 생성 - 회원 검증 실패")
    void givenInvalidMember_whenCreateReview_thenThrowNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> reviewService.createReview(1L, reviewCreateRequestDto, files));
        assertEquals(notFoundException.getMessage(),
            MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
        verify(memberRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("리뷰 생성 - 도서 검증 실패")
    void givenInvalidBook_whenCreateReview_thenThrowNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Member.class)));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> reviewService.createReview(1L, reviewCreateRequestDto, files));
        assertEquals(notFoundException.getMessage(),
            BookMessageEnum.BOOK_NOT_FOUND.getMessage());
        verify(memberRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("리뷰 생성 - 주문 상세 검증 실패")
    void givenInvalidBookOrderDetail_whenCreateReview_thenThrowNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Member.class)));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(bookOrderDetailRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> reviewService.createReview(1L, reviewCreateRequestDto, files));
        assertEquals(notFoundException.getMessage(),
            BookOrderDetailMessageEnum.BOOK_ORDER_DETAIL_NOT_FOUND.getMessage());
        verify(memberRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookOrderDetailRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("리뷰 수정")
    void updateReview() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(mock(Review.class)));
        when(memberRepository.existsById(anyLong())).thenReturn(true);
        when(reviewFileRepository.countByReview_ReviewId(anyLong())).thenReturn(1L);
        doNothing().when(reviewFileRepository)
            .deleteByFile_FileIdInAndReview_ReviewId(anyList(), anyLong());
        when(reviewFileRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        reviewService.updateReview(1L, 1L, reviewUpdateRequestDto, files);

        verify(reviewRepository, times(1)).findById(anyLong());
        verify(memberRepository, times(1)).existsById(anyLong());
        verify(reviewFileRepository, times(1)).countByReview_ReviewId(anyLong());
        verify(reviewFileRepository, times(1)).deleteByFile_FileIdInAndReview_ReviewId(anyList(),
            anyLong());
        verify(reviewFileRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("리뷰 수정 - 리뷰 검증 실패")
    void givenInvalidBook_whenUpdateReview_thenThrowNotFoundException() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> reviewService.updateReview(1L, 1L, reviewUpdateRequestDto, files));
        assertEquals(notFoundException.getMessage(),
            ReviewMessageEnum.REVIEW_NOT_FOUND.getMessage());
        verify(reviewRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("리뷰 수정 - 회원 검증 실패")
    void givenInvalidMember_whenUpdateReview_thenThrowNotFoundException() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(mock(Review.class)));
        when(memberRepository.existsById(anyLong())).thenReturn(false);

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> reviewService.updateReview(1L, 1L, reviewUpdateRequestDto, files));
        assertEquals(notFoundException.getMessage(),
            MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
        verify(reviewRepository, times(1)).findById(anyLong());
        verify(memberRepository, times(1)).existsById(anyLong());
    }
}