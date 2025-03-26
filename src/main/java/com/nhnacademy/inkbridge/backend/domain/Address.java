package com.nhnacademy.inkbridge.backend.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Address {

	private String receiverName;
	private String receiverPhone;

	private String roadName;
	private String addressDetail;
	private String zipCode;
	private Boolean isDefault;

	@Builder
	public Address(String receiverName, String receiverPhone, String roadName, String addressDetail, String zipCode,
		Boolean isDefault) {
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.roadName = roadName;
		this.addressDetail = addressDetail;
		this.zipCode = zipCode;
		this.isDefault = isDefault;
	}

	public boolean isDefault(){
		return isDefault;
	}
}
