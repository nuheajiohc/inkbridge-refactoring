package com.nhnacademy.inkbridge.backend.api;

import com.nhnacademy.inkbridge.backend.domain.Address;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressCreateRequest {

	private String receiverName;
	private String receiverPhone;
	private String roadName;
	private String addressDetail;
	private String zipCode;
	private Boolean isDefault;

	public Address toAddress() {
		return Address.builder()
			.receiverName(receiverName)
			.receiverPhone(receiverPhone)
			.roadName(roadName)
			.addressDetail(addressDetail)
			.zipCode(zipCode)
			.isDefault(isDefault)
			.build();
	}
}
