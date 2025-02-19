package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import com.nhnacademy.inkbridge.backend.repository.custom.PointPolicyTypeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: PointPolicyTypeRepository.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
public interface PointPolicyTypeRepository extends JpaRepository<PointPolicyType, Integer>,
    PointPolicyTypeRepositoryCustom {

    boolean existsByPolicyType(String policyType);

    Integer countAllBy();

}
