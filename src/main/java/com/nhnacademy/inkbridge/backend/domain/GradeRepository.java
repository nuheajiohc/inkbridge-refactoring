package com.nhnacademy.inkbridge.backend.domain;

import java.util.List;
import java.util.Optional;

public interface GradeRepository {

	List<Grade> findAll();

	Optional<Grade> findByDefaultGrade();

	Optional<Grade> findByName(String name);
}
