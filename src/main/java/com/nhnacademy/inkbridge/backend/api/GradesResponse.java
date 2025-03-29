package com.nhnacademy.inkbridge.backend.api;

import java.util.ArrayList;
import java.util.List;

import com.nhnacademy.inkbridge.backend.domain.Grade;

import lombok.Getter;

@Getter
public class GradesResponse {

	private final List<GradeResponse> grades;

	public GradesResponse(List<Grade> grades) {
		this.grades = new ArrayList<>();
		grades.forEach((grade) ->
			this.grades.add(GradeResponse.from(grade)));
	}
}
