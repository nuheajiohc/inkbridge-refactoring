package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookOrderStatusRepository.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
public interface BookOrderStatusRepository extends JpaRepository <BookOrderStatus, Long> {

}
