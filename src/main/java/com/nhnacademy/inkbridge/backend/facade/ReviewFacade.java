package com.nhnacademy.inkbridge.backend.facade;

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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *  class: ReviewFacade.
 *
 *  @author minm063
 *  @version 2024/03/25
 */
@Service
public class ReviewFacade {

    private final MemberPointService memberPointService;
    private final ReviewService reviewService;
    private final PointHistoryService pointHistoryService;
    private final FileService fileService;
    private final PointPolicyService pointPolicyService;


    public ReviewFacade(MemberPointService memberPointService, ReviewService reviewService,
        PointHistoryService pointHistoryService, FileService fileService,
        PointPolicyService pointPolicyService) {
        this.memberPointService = memberPointService;
        this.reviewService = reviewService;
        this.pointHistoryService = pointHistoryService;
        this.fileService = fileService;
        this.pointPolicyService = pointPolicyService;
    }

    /**
     * 회원 번호로 리뷰 목록을 조회하는 메서드입니다.
     *
     * @param pageable Pageable
     * @param memberId member id
     * @return {@link ReviewMemberReadResponseDto}
     */
    public ReviewMemberReadResponseDto getReviewsByMember(Pageable pageable, Long memberId) {
        return reviewService.getReviewsByMember(pageable, memberId);
    }

    /**
     * 도서 번호로 리뷰 목록을 조회하는 메서드입니다.
     *
     * @param pageable Pageable
     * @param bookId book id
     * @return {@link ReviewBookReadResponseDto}
     */
    public ReviewBookReadResponseDto getReviewsByBookId(Pageable pageable, Long bookId) {
        return reviewService.getReviewsByBookId(pageable, bookId);
    }

    /**
     * 리뷰 등록 후 회원에 포인트를 수정하고 포인트 내역에 기록을 추가하는 메서드입니다.
     *
     * @param memberId member id
     * @param reviewCreateRequestDto {@link ReviewCreateRequestDto}
     * @param reviewImages MultipartFile List
     */
    @Transactional
    public void createReviewAndUpdatePoint(Long memberId,
        ReviewCreateRequestDto reviewCreateRequestDto,
        List<MultipartFile> reviewImages) {
        List<File> files =
            Objects.isNull(reviewImages) ? Collections.emptyList() : reviewImages.stream()
                .map(fileService::saveThumbnail).collect(
                    Collectors.toList());
        reviewService.createReview(memberId, reviewCreateRequestDto, files);
        Integer pointPolicyId = pointHistoryService.accumulatePointAtReview(memberId,
            files.isEmpty());
        PointPolicyReadResponseDto currentPointPolicy = pointPolicyService.getCurrentPointPolicy(
            pointPolicyId);
        memberPointService.memberPointUpdate(memberId, currentPointPolicy.getAccumulatePoint());
    }

    /**
     * 리뷰를 수정하는 메서드입니다.
     *
     * @param memberId member id
     * @param reviewId review id
     * @param reviewUpdateRequestDto {@link ReviewUpdateRequestDto}
     * @param reviewImages MultipartFile List
     */
    @Transactional
    public void updateReview(Long memberId, Long reviewId,
        ReviewUpdateRequestDto reviewUpdateRequestDto, List<MultipartFile> reviewImages) {
        List<File> files =
            Objects.isNull(reviewImages) ? Collections.emptyList() : reviewImages.stream()
                .map(fileService::saveThumbnail).collect(
                    Collectors.toList());
        reviewService.updateReview(memberId, reviewId, reviewUpdateRequestDto, files);
    }
}
