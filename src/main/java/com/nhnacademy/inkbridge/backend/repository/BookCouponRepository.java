package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookCoupon;
import com.nhnacademy.inkbridge.backend.entity.BookCoupon.Pk;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookCouponRepository.
 *
 * @author JBum
 * @version 2024/02/20
 */
public interface BookCouponRepository extends JpaRepository<BookCoupon, Pk> {
    
}
