package com.nhnacademy.inkbridge.backend.storage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.nhnacademy.inkbridge.backend.domain.Grade;
import com.nhnacademy.inkbridge.backend.domain.GradeRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GradeCoreRepository implements GradeRepository {

	private final GradeJpaRepository gradeJpaRepository;

	@Override
	public List<Grade> findAll() {
		List<GradeEntity> gradeEntities = gradeJpaRepository.findAll();
		return gradeEntities.stream()
			.map(GradeEntity::toGrade)
			.collect(Collectors.toList());
	}

	@Override
	public Optional<Grade> findByDefaultGrade() {
		Optional<GradeEntity> gradeEntity =gradeJpaRepository.findByName("STANDARD");
		return gradeEntity.map(GradeEntity::toGrade);
	}
}
