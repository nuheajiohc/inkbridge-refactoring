package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeReadResponseDto;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: PointPolicyTypeRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/02/17
 */
@NoRepositoryBean
public interface PointPolicyTypeRepositoryCustom {

    /**
     * 포인트 정책 유형 리스트를 조회하는 메소드입니다.
     *
     * @return List - PointPolicyTypeReadResponseDto
     */
    List<PointPolicyTypeReadResponseDto> findAllPointPolicyTypeBy();

}
