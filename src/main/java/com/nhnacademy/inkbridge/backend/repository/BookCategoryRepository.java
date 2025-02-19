package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookCategoryRepository.
 *
 * @author choijaehun
 * @version 2/17/24
 * @modifiedBy minm063
 * @modifiedAt 2/28/24
 * @modificationReason - deleteByPk_BookId 추가
 */
public interface BookCategoryRepository extends JpaRepository<BookCategory, BookCategory.Pk> {

    List<BookCategory> findByPk_BookId(Long bookId);

    boolean existsByPk_BookId(Long bookId);

    void deleteByPk(BookCategory.Pk pk);

    void deleteByPk_BookId(Long bookId);
    
}
