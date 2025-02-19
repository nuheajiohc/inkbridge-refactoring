package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.CategoryCoupon;
import com.nhnacademy.inkbridge.backend.entity.CategoryCoupon.Pk;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: CategoryCouponRepository.
 *
 * @author JBum
 * @version 2024/02/19
 */
public interface CategoryCouponRepository extends JpaRepository<CategoryCoupon, Pk> {

}
