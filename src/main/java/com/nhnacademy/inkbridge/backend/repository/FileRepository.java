package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.repository.custom.FileRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: FileRepository.
 *
 * @author minm063
 * @version 2024/02/14
 */
public interface FileRepository extends JpaRepository<File, Long>, FileRepositoryCustom {

}
