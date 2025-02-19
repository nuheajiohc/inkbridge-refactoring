package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.repository.custom.BookOrderRepositoryCustom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookOrderRepository.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
public interface BookOrderRepository extends JpaRepository<BookOrder, Long>,
    BookOrderRepositoryCustom {

    Optional<BookOrder> findByOrderCode(String orderCode);

    boolean existsByOrderCode(String orderCode);
}
