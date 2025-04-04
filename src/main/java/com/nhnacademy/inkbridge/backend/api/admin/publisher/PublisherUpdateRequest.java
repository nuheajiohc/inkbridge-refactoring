package com.nhnacademy.inkbridge.backend.api.admin.publisher;

import com.nhnacademy.inkbridge.backend.domain.admin.publisher.Publisher;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PublisherUpdateRequest {

	private String name;

	public Publisher toPublisher() {
		return new Publisher(name);
	}
}
