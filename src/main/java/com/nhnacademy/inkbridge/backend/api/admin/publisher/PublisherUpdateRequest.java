package com.nhnacademy.inkbridge.backend.api.admin.publisher;

import javax.validation.constraints.NotBlank;

import com.nhnacademy.inkbridge.backend.domain.DomainStatus;
import com.nhnacademy.inkbridge.backend.domain.admin.publisher.Publisher;

import lombok.Getter;

@Getter
public class PublisherUpdateRequest {

	@NotBlank
	private String name;
	@NotBlank
	@EnumValid(target = DomainStatus.class, ignoreCase = true)
	private String status;

	public Publisher toPublisher() {
		return Publisher.builder()
			.name(name)
			.status(DomainStatus.valueOf(status.toUpperCase()))
			.build();
	}
}


