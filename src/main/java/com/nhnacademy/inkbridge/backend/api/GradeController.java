package com.nhnacademy.inkbridge.backend.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.inkbridge.backend.api.support.ApiSuccessResponse;
import com.nhnacademy.inkbridge.backend.api.support.ResponseMessage;
import com.nhnacademy.inkbridge.backend.domain.Grade;
import com.nhnacademy.inkbridge.backend.domain.GradeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my/grades")
public class GradeController {

	private final GradeService gradeService;

	@GetMapping
	public ApiSuccessResponse<GradesResponse> getGrades() {
		List<Grade> grades = gradeService.getGrades();
		return ApiSuccessResponse.success(new GradesResponse(grades), ResponseMessage.GRADE_READ_SUCCESS);
	}
}
