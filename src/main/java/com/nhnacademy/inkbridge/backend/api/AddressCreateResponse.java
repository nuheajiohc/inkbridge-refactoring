package com.nhnacademy.inkbridge.backend.api;

import lombok.Getter;

@Getter
public class AddressCreateResponse {

	private Long addressId;

	public AddressCreateResponse(Long addressId) {
		this.addressId = addressId;
	}
}
