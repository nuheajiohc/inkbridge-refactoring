package com.nhnacademy.inkbridge.backend.api;

import com.nhnacademy.inkbridge.backend.domain.Grade;

import lombok.Getter;

@Getter
public class GradeResponse {

	private final Integer id;
	private final String name;
	private final Integer earningRate;
	private final Integer minPurchaseAmount;

	public GradeResponse(Integer id, String name, Integer earningRate, Integer minPurchaseAmount) {
		this.id = id;
		this.name = name;
		this.earningRate = earningRate;
		this.minPurchaseAmount = minPurchaseAmount;
	}

	public static GradeResponse from(Grade grade) {
		return new GradeResponse(grade.getId(), grade.getName(), grade.getEarningRate(), grade.getMinPurchaseAmount());
	}
}
