package com.nhnacademy.inkbridge.backend.api.admin.publisher;

import com.nhnacademy.inkbridge.backend.domain.DomainStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PublisherResponse {

	private Integer id;
	private String name;
	private DomainStatus status;

	@Builder
	public PublisherResponse(Integer id, String name, DomainStatus status) {
		this.id = id;
		this.name = name;
		this.status = status;
	}
}
