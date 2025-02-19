package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Review;
import com.nhnacademy.inkbridge.backend.repository.custom.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: ReviewRepository.
 *
 * @author minm063
 * @version 2024/03/19
 */
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    Long countByMember_MemberId(Long memberId);

    Boolean existsByBookOrderDetail_OrderDetailId(Long orderDetailId);
}
