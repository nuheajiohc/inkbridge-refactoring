package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookFileRepository.
 *
 * @author minm063
 * @version 2024/02/21
 */
public interface BookFileRepository extends JpaRepository<BookFile, Long> {

    void deleteAllByBook_BookId(Long bookId);
}
