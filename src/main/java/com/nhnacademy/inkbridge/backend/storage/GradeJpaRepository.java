package com.nhnacademy.inkbridge.backend.storage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeJpaRepository extends JpaRepository<GradeEntity, Integer> {

	Optional<GradeEntity> findByName(String name);
}
