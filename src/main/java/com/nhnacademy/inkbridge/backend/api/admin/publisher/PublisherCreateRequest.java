package com.nhnacademy.inkbridge.backend.api.admin.publisher;

import javax.validation.constraints.NotBlank;

import com.nhnacademy.inkbridge.backend.domain.admin.publisher.Publisher;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PublisherCreateRequest {

	@NotBlank
	private String name;

	public Publisher toPublisher() {
		return new Publisher(name);
	}
}
