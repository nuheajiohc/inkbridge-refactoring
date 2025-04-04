package com.nhnacademy.inkbridge.backend.domain.admin.publisher;

import lombok.Getter;

@Getter
public class Publisher {

	private String name;

	public Publisher(String name) {
		this.name = name;
	}
}
