package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.QFile;
import com.nhnacademy.inkbridge.backend.entity.QReview;
import com.nhnacademy.inkbridge.backend.entity.QReviewFile;
import com.nhnacademy.inkbridge.backend.repository.custom.FileRepositoryCustom;
import com.querydsl.core.Tuple;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: FileRepositoryImpl.
 *
 * @author jeongbyeonghun
 * @version 3/20/24
 */
public class FileRepositoryImpl extends QuerydslRepositorySupport implements FileRepositoryCustom {

    public FileRepositoryImpl() {
        super(File.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, List<String>> getAllFileByReviewId(List<Long> reviewIdList) {
        QFile file = QFile.file;
        QReviewFile reviewFile = QReviewFile.reviewFile;
        QReview review = QReview.review;

        List<Tuple> results = from(reviewFile)
            .join(reviewFile.file, file)
            .join(reviewFile.review, review)
            .where(review.reviewId.in(reviewIdList))
            .select(file.fileUrl, review.reviewId)
            .fetch();

        return results.stream()
            .collect(Collectors.groupingBy(
                result -> result.get(review.reviewId),
                Collectors.mapping(result -> result.get(file.fileUrl), Collectors.toList())
            ));
    }
}
