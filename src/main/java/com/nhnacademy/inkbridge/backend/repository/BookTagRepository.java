package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookTag;
import com.nhnacademy.inkbridge.backend.entity.BookTag.Pk;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookTagRepository.
 *
 * @author minm063
 * @version 2024/02/21
 */
public interface BookTagRepository extends JpaRepository<BookTag, Pk> {

    void deleteAllByPk_BookId(Long bookId);
    void deleteAllByPk_TagId(Long tagId);
}
