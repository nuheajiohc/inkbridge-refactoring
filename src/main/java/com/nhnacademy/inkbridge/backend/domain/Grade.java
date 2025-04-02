package com.nhnacademy.inkbridge.backend.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Grade {

	private Integer id;
	private String name;
	private Integer earningRate;
	private Integer minPurchaseAmount;

	public Grade(Integer id, String name, Integer earningRate, Integer minPurchaseAmount) {
		this.id = id;
		this.name = name;
		this.earningRate = earningRate;
		this.minPurchaseAmount = minPurchaseAmount;
	}
}
