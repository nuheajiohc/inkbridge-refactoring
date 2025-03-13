package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewBookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.facade.ReviewFacade;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: ReviewController.
 *
 * @author minm063
 * @version 2024/03/19
 */
@RequestMapping("/api")
@RestController
public class ReviewController {

    private final ReviewFacade reviewFacade;

    public ReviewController(ReviewFacade reviewFacade) {
        this.reviewFacade = reviewFacade;
    }

    /**
     * 회원 번호로 리뷰를 조회하는 api입니다.
     *
     * @param memberId Long
     * @param pageable Pageable
     * @return ReviewReadResponseDto
     */
    @GetMapping("/auth/reviews")
    public ResponseEntity<ReviewMemberReadResponseDto> getReviewsByMember(
        @RequestParam(name = "memberId") Long memberId, Pageable pageable) {
        ReviewMemberReadResponseDto reviews = reviewFacade.getReviewsByMember(pageable, memberId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    /**
     * 도서 번호로 리뷰를 조회하는 api입니다.
     *
     * @param bookId Long
     * @param pageable Pageable
     * @return ReviewReadResponseDto
     */
    @GetMapping("/reviews/books/{bookId}")
    public ResponseEntity<ReviewBookReadResponseDto> getReviewsByBookId(@PathVariable Long bookId,
        @PageableDefault(size = 5) Pageable pageable) {
        ReviewBookReadResponseDto reviews = reviewFacade.getReviewsByBookId(pageable, bookId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    /**
     * 리뷰를 등록하는 api입니다.
     *
     * @param reviewImages MultipartFile List
     * @param memberId Long
     * @param reviewCreateRequestDto ReviewCreateRequestDto
     * @param bindingResult BindingResult
     * @return ResponseEntity HttpStatus
     */
    @PostMapping("/auth/reviews")
    public ResponseEntity<HttpStatus> createReview(
        @RequestPart(value = "images", required = false) List<MultipartFile> reviewImages,
        @RequestParam(value = "memberId") Long memberId,
        @Valid @RequestPart(value = "review") ReviewCreateRequestDto reviewCreateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        reviewFacade.createReviewAndUpdatePoint(memberId, reviewCreateRequestDto, reviewImages);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 리뷰를 수정하는 api입니다.
     *
     * @param reviewId Long
     * @param memberId Long
     * @param reviewImages MultipartFile List
     * @param reviewUpdateRequestDto ReviewUpdateRequestDto
     * @param bindingResult BindingResult
     * @return HttpStatus
     */
    @PutMapping("/auth/reviews/{reviewId}")
    public ResponseEntity<HttpStatus> updateReview(@PathVariable(name = "reviewId") Long reviewId,
        @RequestParam(value = "memberId") Long memberId,
        @RequestPart(value = "images", required = false) List<MultipartFile> reviewImages,
        @Valid @RequestPart(value = "review") ReviewUpdateRequestDto reviewUpdateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        reviewFacade.updateReview(memberId, reviewId, reviewUpdateRequestDto, reviewImages);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
