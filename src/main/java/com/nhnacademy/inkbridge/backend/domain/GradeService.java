package com.nhnacademy.inkbridge.backend.domain;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GradeService {

	private final GradeRepository gradeRepository;

	@Transactional(readOnly = true)
	public List<Grade> getGrades() {
		return gradeRepository.findAll();
	}
}
