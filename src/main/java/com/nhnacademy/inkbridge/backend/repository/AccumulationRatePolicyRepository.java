package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.AccumulationRatePolicy;
import com.nhnacademy.inkbridge.backend.repository.custom.AccumulationRatePolicyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: AccumulationRatePolicyRepository.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
public interface AccumulationRatePolicyRepository extends
    JpaRepository<AccumulationRatePolicy, Long>,
    AccumulationRatePolicyRepositoryCustom {

}
