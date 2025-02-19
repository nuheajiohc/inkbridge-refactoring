package com.nhnacademy.inkbridge.backend.repository;


import com.nhnacademy.inkbridge.backend.entity.PointPolicy;
import com.nhnacademy.inkbridge.backend.repository.custom.PointPolicyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: PointPolicyRepository.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
public interface PointPolicyRepository extends JpaRepository<PointPolicy, Long>,
    PointPolicyRepositoryCustom {

}
