package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.DeliveryPolicy;
import com.nhnacademy.inkbridge.backend.repository.custom.DeliveryPolicyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: DeliveryPolicyRepository.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
public interface DeliveryPolicyRepository extends JpaRepository<DeliveryPolicy, Long>,
    DeliveryPolicyRepositoryCustom {

}
