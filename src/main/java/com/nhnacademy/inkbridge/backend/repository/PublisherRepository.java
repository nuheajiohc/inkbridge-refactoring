package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: PublisherRepository.
 *
 * @author minm063
 * @version 2024/02/14
 */
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Page<Publisher> findAllBy(Pageable pageable);
}
