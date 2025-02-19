package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.AccumulationRatePolicy;
import com.nhnacademy.inkbridge.backend.entity.QAccumulationRatePolicy;
import com.nhnacademy.inkbridge.backend.repository.custom.AccumulationRatePolicyRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: AccumulationRatePolicyRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
public class AccumulationRatePolicyRepositoryImpl extends QuerydslRepositorySupport implements
    AccumulationRatePolicyRepositoryCustom {

    public AccumulationRatePolicyRepositoryImpl() {
        super(AccumulationRatePolicy.class);
    }

    /**
     * {@inheritDoc}
     *
     * @return List - AccumulationRatePolicyReadResponseDto
     */
    @Override
    public List<AccumulationRatePolicyAdminReadResponseDto> findAllAccumulationRatePolicies() {
        QAccumulationRatePolicy accumulationRatePolicy = QAccumulationRatePolicy.accumulationRatePolicy;

        return from(accumulationRatePolicy)
            .select(Projections.constructor(AccumulationRatePolicyAdminReadResponseDto.class,
                accumulationRatePolicy.accumulationRatePolicyId,
                accumulationRatePolicy.accumulationRate,
                accumulationRatePolicy.createdAt))
            .orderBy(accumulationRatePolicy.accumulationRatePolicyId.asc())
            .fetch();
    }

    /**
     * {@inheritDoc}
     *
     * @param accumulationRatePolicyId Long
     * @return AccumulationRatePolicyReadResponseDto
     */
    @Override
    public AccumulationRatePolicyReadResponseDto findAccumulationRatePolicy(
        Long accumulationRatePolicyId) {
        QAccumulationRatePolicy accumulationRatePolicy = QAccumulationRatePolicy.accumulationRatePolicy;

        return from(accumulationRatePolicy)
            .select(Projections.constructor(AccumulationRatePolicyReadResponseDto.class,
                accumulationRatePolicy.accumulationRatePolicyId,
                accumulationRatePolicy.accumulationRate))
            .where(accumulationRatePolicy.accumulationRatePolicyId.eq(accumulationRatePolicyId))
            .fetchOne();
    }

    /**
     * {@inheritDoc}
     *
     * @return AccumulationRatePolicyReadResponseDto
     */
    @Override
    public AccumulationRatePolicyReadResponseDto findCurrentAccumulationRatePolicy() {
        QAccumulationRatePolicy accumulationRatePolicy = QAccumulationRatePolicy.accumulationRatePolicy;

        return from(accumulationRatePolicy)
            .select(Projections.constructor(AccumulationRatePolicyReadResponseDto.class,
                accumulationRatePolicy.accumulationRatePolicyId,
                accumulationRatePolicy.accumulationRate))
            .orderBy(accumulationRatePolicy.accumulationRatePolicyId.desc())
            .limit(1)
            .fetchOne();
    }

    /**
     * {@inheritDoc}
     *
     * @return 적립률
     */
    @Override
    public Integer findByCurrentAccumulationRate() {
        QAccumulationRatePolicy accumulationRatePolicy = QAccumulationRatePolicy.accumulationRatePolicy;

        return from(accumulationRatePolicy)
            .select(accumulationRatePolicy.accumulationRate)
            .orderBy(accumulationRatePolicy.accumulationRatePolicyId.desc())
            .limit(1)
            .fetchOne();
    }
}
