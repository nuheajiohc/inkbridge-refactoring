package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewBookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.File;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;

/**
 * class: ReviewService.
 *
 * @author minm063
 * @version 2024/03/19
 */
public interface ReviewService {

    /**
     * 회원 아이디로 리뷰 목록을 조회하는 메서드입니다.
     *
     * @param memberId Long
     * @return ReviewReadResponseDto
     */
    ReviewMemberReadResponseDto getReviewsByMember(Pageable pageable, Long memberId);

    /**
     * 도서 번호로 리뷰 목록을 조회하는 메서드입니다.
     *
     * @param pageable Pageable
     * @param bookId Long
     * @return ReviewBookReadResponseDto
     */
    ReviewBookReadResponseDto getReviewsByBookId(Pageable pageable, Long bookId);

    /**
     * 리뷰를 작성한 주문 상세가 존재하는지 체크하는 메서드입니다.
     *
     * @param orderDetailIdList orderDetailIdList
     * @return orderDetailId: isReviewed
     */
    Map<Long, Boolean> isReviewed(List<Long> orderDetailIdList);

    /**
     * 리뷰를 등록하는 메서드입니다.
     *
     * @param memberId Long
     * @param reviewCreateRequestDto ReviewCreateRequestDto
     * @param fileIds File List
     */
    void createReview(Long memberId, ReviewCreateRequestDto reviewCreateRequestDto,
        List<File> fileIds);

    /**
     * 리뷰를 수정하는 메서드입니다.
     *
     * @param memberId Long
     * @param reviewId Long
     * @param reviewUpdateRequestDto ReviewUpdateRequestDto
     * @param files File List
     */
    void updateReview(Long memberId, Long reviewId, ReviewUpdateRequestDto reviewUpdateRequestDto,
        List<File> files);
}
