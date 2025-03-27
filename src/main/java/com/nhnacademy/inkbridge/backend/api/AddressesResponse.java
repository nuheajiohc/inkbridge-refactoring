package com.nhnacademy.inkbridge.backend.api;

import java.util.ArrayList;
import java.util.List;

import com.nhnacademy.inkbridge.backend.domain.Address;

import lombok.Getter;

@Getter
public class AddressesResponse {

	private List<AddressResponse> addresses;

	public AddressesResponse(List<Address> addresses) {
		this.addresses = new ArrayList<>();
		addresses.forEach(address ->
			this.addresses.add(AddressResponse.from(address))
		);
	}
}
