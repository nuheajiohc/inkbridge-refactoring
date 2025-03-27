package com.nhnacademy.inkbridge.backend.api;

import java.util.List;

import com.nhnacademy.inkbridge.backend.domain.Address;

import lombok.Getter;

@Getter
public class AddressesResponse {

	private List<Address> addresses;

	public AddressesResponse(List<Address> addresses) {
		this.addresses = addresses;
	}
}
