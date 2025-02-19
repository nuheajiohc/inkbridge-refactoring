package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: TagRepository.
 *
 * @author jeongbyeonghun
 * @version 2/15/24
 */

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByTagName(String tagName);
}

