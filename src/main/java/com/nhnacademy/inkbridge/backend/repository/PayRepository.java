package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Pay;
import com.nhnacademy.inkbridge.backend.repository.custom.PayRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: PayRepository.
 *
 * @author jangjaehun
 * @version 2024/03/16
 */
public interface PayRepository extends JpaRepository<Pay, Long>, PayRepositoryCustom {

}
