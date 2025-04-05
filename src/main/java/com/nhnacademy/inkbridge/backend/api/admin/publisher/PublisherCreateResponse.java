package com.nhnacademy.inkbridge.backend.api.admin.publisher;

import lombok.Getter;

@Getter
public class PublisherCreateResponse {

	private Integer publisherId;

	public PublisherCreateResponse(Integer publisherId) {
		this.publisherId = publisherId;
	}
}
