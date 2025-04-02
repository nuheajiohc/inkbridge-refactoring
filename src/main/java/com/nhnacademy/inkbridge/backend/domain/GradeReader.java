package com.nhnacademy.inkbridge.backend.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GradeReader {

	private final GradeRepository gradeRepository;

	public List<Grade> getGrades() {
		return gradeRepository.findAll();
	}

	public Optional<Grade> getGrade(String name) {
		return gradeRepository.findByName(name);
	}

	public Optional<Grade> getDefaultGrade() {
		return gradeRepository.findByDefaultGrade();
	}
}
