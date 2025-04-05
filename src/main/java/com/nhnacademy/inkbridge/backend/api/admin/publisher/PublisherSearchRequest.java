package com.nhnacademy.inkbridge.backend.api.admin.publisher;

import javax.validation.constraints.Min;

import com.nhnacademy.inkbridge.backend.api.support.validation.constraints.EnumValid;
import com.nhnacademy.inkbridge.backend.domain.DomainStatus;

import lombok.Getter;

@Getter
public class PublisherSearchRequest {

	@EnumValid(target = DomainStatus.class, ignoreCase = true)
	private String status;
	@Min(0)
	private Integer page;
	@Min(1)
	private Integer size;

	public PublisherSearchRequest(String status, Integer page, Integer size) {
		this.status = status;
		this.page = page;
		this.size = size;
	}

	public DomainStatus getStatus() {
		return status == null ? null : DomainStatus.valueOf(status.toUpperCase());
	}
}
