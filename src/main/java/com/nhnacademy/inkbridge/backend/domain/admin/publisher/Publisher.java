package com.nhnacademy.inkbridge.backend.domain.admin.publisher;

import com.nhnacademy.inkbridge.backend.domain.DomainStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Publisher {

	private Integer id;
	private String name;
	private DomainStatus status;

	@Builder
	public Publisher(Integer id, String name, DomainStatus status) {
		this.id = id;
		this.name = name;
		this.status = status;
	}
}
