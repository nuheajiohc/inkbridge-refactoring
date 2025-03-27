package com.nhnacademy.inkbridge.backend.api;

import com.nhnacademy.inkbridge.backend.domain.Address;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddressResponse {

	private final Long id;
	private final String receiverName;
	private final String receiverPhone;
	private final String roadName;
	private final String addressDetail;
	private final String zipCode;
	private final Boolean isDefault;

	@Builder
	public AddressResponse(Long id, String receiverName, String receiverPhone, String roadName, String addressDetail,
		String zipCode, boolean isDefault) {
		this.id = id;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.roadName = roadName;
		this.addressDetail = addressDetail;
		this.zipCode = zipCode;
		this.isDefault = isDefault;
	}

	public static AddressResponse from(Address address) {
		return AddressResponse.builder()
			.id(address.getId())
			.receiverName(address.getReceiverName())
			.receiverPhone(address.getReceiverPhone())
			.roadName(address.getRoadName())
			.addressDetail(address.getAddressDetail())
			.zipCode(address.getZipCode())
			.isDefault(address.isDefault())
			.build();
	}


}
