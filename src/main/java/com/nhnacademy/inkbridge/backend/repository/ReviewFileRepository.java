package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.ReviewFile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: ReviewFileRepository.
 *
 * @author jeongbyeonghun
 * @version 2/22/24
 */
public interface ReviewFileRepository extends JpaRepository<ReviewFile, Long> {

    void deleteByFile_FileIdInAndReview_ReviewId(List<Long> fileId, Long reviewId);

    Long countByReview_ReviewId(Long reviewId);
}