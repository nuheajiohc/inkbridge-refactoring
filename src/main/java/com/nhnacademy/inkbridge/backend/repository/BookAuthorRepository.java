package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookAuthor;
import com.nhnacademy.inkbridge.backend.entity.BookAuthor.Pk;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookAuthorRepository.
 *
 * @author minm063
 * @version 2024/02/15
 */
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Pk> {

    void deleteAllByBook_BookId(Long bookId);
}
