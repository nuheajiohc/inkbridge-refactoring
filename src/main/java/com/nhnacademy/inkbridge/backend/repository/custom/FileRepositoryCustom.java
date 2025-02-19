package com.nhnacademy.inkbridge.backend.repository.custom;

import java.util.List;
import java.util.Map;

/**
 * class: FileRepositoryCustom.
 *
 * @author jeongbyeonghun
 * @version 3/20/24
 */
public interface FileRepositoryCustom {


    /**
     * 주어진 리뷰 ID에 연결된 모든 파일 정보를 조회합니다.
     *
     * @param reviewId 조회할 리뷰의 ID
     * @return 파일 ID를 키로 하고, 해당 파일 정보를 값으로 하는 맵
     */
    Map<Long, List<String>> getAllFileByReviewId(List<Long> reviewId);
}
