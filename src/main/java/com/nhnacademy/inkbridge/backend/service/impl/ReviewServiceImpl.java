package com.nhnacademy.inkbridge.backend.service.impl;

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
import com.nhnacademy.inkbridge.backend.entity.ReviewFile;
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
import com.nhnacademy.inkbridge.backend.service.ReviewService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: ReviewServiceImpl.
 *
 * @author minm063
 * @version 2024/03/19
 */
@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BookOrderDetailRepository bookOrderDetailRepository;
    private final FileRepository fileRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, MemberRepository memberRepository,
        ReviewFileRepository reviewFileRepository, BookRepository bookRepository,
        BookOrderDetailRepository bookOrderDetailRepository, FileRepository fileRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.reviewFileRepository = reviewFileRepository;
        this.bookRepository = bookRepository;
        this.bookOrderDetailRepository = bookOrderDetailRepository;
        this.fileRepository = fileRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public ReviewMemberReadResponseDto getReviewsByMember(Pageable pageable, Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
        }

        Page<ReviewDetailByMemberReadResponseDto> page = reviewRepository.findByMemberId(pageable,
            memberId);
        Map<Long, List<String>> files = fileRepository.getAllFileByReviewId(
            page.getContent().stream().map(ReviewDetailByMemberReadResponseDto::getReviewId)
                .collect(
                    Collectors.toList()));
        Long count = reviewRepository.countByMember_MemberId(memberId);

        return ReviewMemberReadResponseDto.builder().reviewDetailReadResponseDtos(page).reviewFiles(
            files).count(count).build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public ReviewBookReadResponseDto getReviewsByBookId(Pageable pageable, Long bookId) {
        Page<ReviewDetailOnBookReadResponseDto> page = reviewRepository.findByBookId(pageable,
            bookId);
        Map<Long, List<String>> files = fileRepository.getAllFileByReviewId(
            page.getContent().stream().map(ReviewDetailOnBookReadResponseDto::getReviewId)
                .collect(
                    Collectors.toList()));

        return ReviewBookReadResponseDto.builder().reviewDetailReadResponseDtos(page).reviewFiles(
            files).build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, Boolean> isReviewed(List<Long> orderDetailIdList) {
        return orderDetailIdList.stream().collect(Collectors.toMap(id -> id,
            reviewRepository::existsByBookOrderDetail_OrderDetailId));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void createReview(Long memberId, ReviewCreateRequestDto reviewCreateRequestDto,
        List<File> files) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(
                () -> new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage()));
        Book book = bookRepository.findById(reviewCreateRequestDto.getBookId())
            .orElseThrow(() -> new NotFoundException(
                BookMessageEnum.BOOK_NOT_FOUND.getMessage()));
        BookOrderDetail bookOrderDetail = bookOrderDetailRepository.findById(
                reviewCreateRequestDto.getOrderDetailId())
            .orElseThrow(() -> new NotFoundException(
                BookOrderDetailMessageEnum.BOOK_ORDER_DETAIL_NOT_FOUND.getMessage()));

        Review review = Review.builder().member(member).book(book).bookOrderDetail(bookOrderDetail)
            .reviewTitle(reviewCreateRequestDto.getReviewTitle()).reviewContent(
                reviewCreateRequestDto.getReviewContent()).registeredAt(LocalDateTime.now())
            .score(reviewCreateRequestDto.getScore()).build();
        reviewRepository.save(review);

        if (!files.isEmpty()) {
            List<ReviewFile> reviewFiles = files.stream().map(
                    file -> ReviewFile.builder().fileId(file.getFileId()).review(review)
                        .build())
                .collect(
                    Collectors.toList());
            reviewFileRepository.saveAll(reviewFiles);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateReview(Long memberId, Long reviewId,
        ReviewUpdateRequestDto reviewUpdateRequestDto, List<File> files) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException(
            ReviewMessageEnum.REVIEW_NOT_FOUND.getMessage()));

        if (!memberRepository.existsById(memberId)) {
            throw new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
        }

        review.updateReview(reviewUpdateRequestDto.getReviewTitle(),
            reviewUpdateRequestDto.getReviewContent(), LocalDateTime.now(),
            reviewUpdateRequestDto.getScore());

        if (!files.isEmpty()) {
            if (reviewFileRepository.countByReview_ReviewId(reviewId) > 0) {
                reviewFileRepository.deleteByFile_FileIdInAndReview_ReviewId(
                    files.stream().map(File::getFileId).collect(Collectors.toList()), reviewId);
            }
            List<ReviewFile> reviewFiles = files.stream().map(
                    file -> ReviewFile.builder().fileId(file.getFileId()).review(review)
                        .build())
                .collect(Collectors.toList());
            reviewFileRepository.saveAll(reviewFiles);
        }

    }
}
