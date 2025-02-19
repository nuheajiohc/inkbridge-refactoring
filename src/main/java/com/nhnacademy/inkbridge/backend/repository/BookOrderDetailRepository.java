package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookOrderDetail;
import com.nhnacademy.inkbridge.backend.repository.custom.BookOrderDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookOrderDetailRepository.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
public interface BookOrderDetailRepository extends JpaRepository<BookOrderDetail, Long>,
    BookOrderDetailRepositoryCustom {

}
