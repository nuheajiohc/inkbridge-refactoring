package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import com.nhnacademy.inkbridge.backend.entity.QPointPolicyType;
import com.nhnacademy.inkbridge.backend.repository.custom.PointPolicyTypeRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: PointPolicyTypeRepositoryImpl.
 *
 * @author jangjaehun
 * @version 2024/02/17
 */
public class PointPolicyTypeRepositoryImpl extends QuerydslRepositorySupport implements
    PointPolicyTypeRepositoryCustom {

    public PointPolicyTypeRepositoryImpl() {
        super(PointPolicyType.class);
    }

    /**
     * {@inheritDoc}
     *
     * @return List - PointPolicyTypeReadResponseDto
     */
    @Override
    public List<PointPolicyTypeReadResponseDto> findAllPointPolicyTypeBy() {
        QPointPolicyType pointPolicyType = QPointPolicyType.pointPolicyType;

        return from(pointPolicyType)
            .select(Projections.constructor(PointPolicyTypeReadResponseDto.class,
                pointPolicyType.pointPolicyTypeId,
                pointPolicyType.policyType))
            .fetch();
    }
}
