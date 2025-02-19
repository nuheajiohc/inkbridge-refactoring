package com.nhnacademy.inkbridge.backend.facade;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewBookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.service.FileService;
import com.nhnacademy.inkbridge.backend.service.MemberPointService;
import com.nhnacademy.inkbridge.backend.service.PointHistoryService;
import com.nhnacademy.inkbridge.backend.service.PointPolicyService;
import com.nhnacademy.inkbridge.backend.service.ReviewService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 *  class: ReviewFacadeTest.
 *
 *  @author minm063
 *  @version 2024/03/25
 */
@ExtendWith(MockitoExtension.class)
class ReviewFacadeTest {

    @InjectMocks
    ReviewFacade reviewFacade;

    @Mock
    MemberPointService memberPointService;
    @Mock
    ReviewService reviewService;
    @Mock
    PointHistoryService pointHistoryService;
    @Mock
    FileService fileService;
    @Mock
    PointPolicyService pointPolicyService;
    @Mock
    Pageable pageable;

    @Test
    @DisplayName("회원 번호로 리뷰 목록 조회")
    void getReviewsByMember() {
        when(reviewService.getReviewsByMember(any(Pageable.class), anyLong())).thenReturn(mock(
            ReviewMemberReadResponseDto.class));

        ReviewMemberReadResponseDto reviewsByMember = reviewFacade.getReviewsByMember(pageable, 1L);

        assertNotNull(reviewsByMember);
        verify(reviewService, times(1)).getReviewsByMember(any(Pageable.class), anyLong());
    }

    @Test
    @DisplayName("도서 번호로 리뷰 목록 조회")
    void getReviewsByBookId() {
        when(reviewService.getReviewsByBookId(any(Pageable.class), anyLong())).thenReturn(mock(
            ReviewBookReadResponseDto.class));

        ReviewBookReadResponseDto reviewsByBookId = reviewFacade.getReviewsByBookId(pageable, 1L);

        assertNotNull(reviewsByBookId);
        verify(reviewService, times(1)).getReviewsByBookId(any(Pageable.class), anyLong());
    }

    @Test
    @DisplayName("리뷰 등록, 회원 포인트 수정, 포인트 내역 추가")
    void createReviewAndUpdatePoint() {
        ReviewCreateRequestDto dto = ReviewCreateRequestDto.builder().build();

        when(fileService.saveThumbnail(any(MultipartFile.class))).thenReturn(mock(File.class));
        doNothing().when(reviewService)
            .createReview(anyLong(), any(ReviewCreateRequestDto.class), anyList());
        when(pointHistoryService.accumulatePointAtReview(anyLong(), anyBoolean())).thenReturn(1);
        when(pointPolicyService.getCurrentPointPolicy(anyInt())).thenReturn(mock(
            PointPolicyReadResponseDto.class));
        doNothing().when(memberPointService).memberPointUpdate(anyLong(), anyLong());

        reviewFacade.createReviewAndUpdatePoint(1L, dto,
            List.of(new MockMultipartFile("image", "image".getBytes())));

        verify(fileService, times(1)).saveThumbnail(any());
        verify(reviewService, times(1)).createReview(anyLong(), any(), anyList());
        verify(pointHistoryService, times(1)).accumulatePointAtReview(anyLong(), anyBoolean());
        verify(pointPolicyService, times(1)).getCurrentPointPolicy(anyInt());
        verify(memberPointService, times(1)).memberPointUpdate(anyLong(), anyLong());
    }

    @Test
    void updateReview() {
        when(fileService.saveThumbnail(any())).thenReturn(mock(File.class));
        doNothing().when(reviewService).updateReview(anyLong(), anyLong(), any(), anyList());

        reviewFacade.updateReview(1L, 1L, ReviewUpdateRequestDto.builder().build(),
            List.of(new MockMultipartFile("image", "image".getBytes())));

        verify(fileService, times(1)).saveThumbnail(any());
        verify(reviewService, times(1)).updateReview(anyLong(), anyLong(), any(), anyList());
    }
}