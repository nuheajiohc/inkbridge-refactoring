package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.repository.custom.AuthorRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: AuthorRepository.
 *
 * @author JBum
 * @version 2024/02/29
 */
public interface AuthorRepository extends JpaRepository<Author, Long>, AuthorRepositoryCustom {

}
