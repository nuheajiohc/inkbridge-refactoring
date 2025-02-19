package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.PointPolicy;
import com.nhnacademy.inkbridge.backend.entity.QPointPolicy;
import com.nhnacademy.inkbridge.backend.entity.QPointPolicyType;
import com.nhnacademy.inkbridge.backend.repository.custom.PointPolicyRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: PointPolicyRepositoryImpl.
 *
 * @author jangjaehun
 * @version 2024/02/16
 */
public class PointPolicyRepositoryImpl extends QuerydslRepositorySupport implements
    PointPolicyRepositoryCustom {

    public PointPolicyRepositoryImpl() {
        super(PointPolicy.class);
    }

    /**
     * {@inheritDoc}
     *
     * @return List - PointPolicyReadResponseDto
     */
    @Override
    public List<PointPolicyAdminReadResponseDto> findAllPointPolicyBy() {
        QPointPolicy pointPolicy = QPointPolicy.pointPolicy;
        QPointPolicyType pointPolicyType = QPointPolicyType.pointPolicyType;

        return from(pointPolicy)
            .innerJoin(pointPolicyType)
            .on(pointPolicy.pointPolicyType.eq(pointPolicyType))
            .select(Projections.constructor(PointPolicyAdminReadResponseDto.class,
                pointPolicy.pointPolicyId,
                pointPolicyType.policyType,
                pointPolicy.accumulatePoint,
                pointPolicy.createdAt))
            .orderBy(pointPolicy.pointPolicyId.asc())
            .fetch();
    }

    /**
     * {@inheritDoc}
     *
     * @param pointPolicyTypeId Integer
     * @return PointPolicyReadResponseDto
     */
    @Override
    public List<PointPolicyAdminReadResponseDto> findAllPointPolicyByTypeId(
        Integer pointPolicyTypeId) {
        QPointPolicy pointPolicy = QPointPolicy.pointPolicy;
        QPointPolicyType pointPolicyType = QPointPolicyType.pointPolicyType;

        return from(pointPolicy)
            .innerJoin(pointPolicyType)
            .on(pointPolicy.pointPolicyType.eq(pointPolicyType))
            .select(Projections.constructor(PointPolicyAdminReadResponseDto.class,
                pointPolicy.pointPolicyId,
                pointPolicyType.policyType,
                pointPolicy.accumulatePoint,
                pointPolicy.createdAt))
            .where(pointPolicyType.pointPolicyTypeId.eq(pointPolicyTypeId))
            .fetch();
    }


    /**
     * {@inheritDoc}
     *
     * @return List - PointPolicyReadResponseDto
     */
    @Override
    public List<PointPolicyAdminReadResponseDto> findAllCurrentPointPolicies() {
        QPointPolicy pointPolicy = QPointPolicy.pointPolicy;
        QPointPolicy subPointPolicy = new QPointPolicy("subPointPolicy");
        QPointPolicyType pointPolicyType = QPointPolicyType.pointPolicyType;

        return from(pointPolicy)
            .select(Projections.constructor(PointPolicyAdminReadResponseDto.class,
                pointPolicy.pointPolicyId,
                pointPolicyType.policyType,
                pointPolicy.accumulatePoint,
                pointPolicy.createdAt))
            .innerJoin(pointPolicyType)
            .on(pointPolicy.pointPolicyType.eq(pointPolicyType))
            .where(Expressions.list(pointPolicy.pointPolicyType, pointPolicy.pointPolicyId).in(
                JPAExpressions.select(subPointPolicy.pointPolicyType,
                        subPointPolicy.pointPolicyId.max())
                    .from(subPointPolicy)
                    .groupBy(subPointPolicy.pointPolicyType)
            ))
            .orderBy(pointPolicyType.pointPolicyTypeId.asc())
            .fetch();
    }

    /**
     * {@inheritDoc}
     *
     * @param pointPolicyTypeId Integer
     * @return PointPolicyReadResponseDto
     */
    @Override
    public PointPolicyReadResponseDto findCurrentPointPolicy(Integer pointPolicyTypeId) {
        QPointPolicy pointPolicy = QPointPolicy.pointPolicy;
        QPointPolicyType pointPolicyType = QPointPolicyType.pointPolicyType;

        return from(pointPolicy)
            .select(Projections.constructor(PointPolicyReadResponseDto.class,
                pointPolicy.pointPolicyId,
                pointPolicyType.policyType,
                pointPolicy.accumulatePoint))
            .innerJoin(pointPolicyType)
            .on(pointPolicy.pointPolicyType.eq(pointPolicyType))
            .where(pointPolicyType.pointPolicyTypeId.eq(pointPolicyTypeId))
            .orderBy(pointPolicy.pointPolicyId.desc())
            .limit(1L)
            .fetchOne();
    }


}
