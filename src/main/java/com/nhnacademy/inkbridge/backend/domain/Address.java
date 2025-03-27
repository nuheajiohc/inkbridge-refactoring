package com.nhnacademy.inkbridge.backend.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Address {

	public static int ADDRESS_LIMIT = 10;

	private Long id;
	private String receiverName;
	private String receiverPhone;
	private String roadName;
	private String addressDetail;
	private String zipCode;
	private Boolean isDefault;

	@Builder
	public Address(Long id, String receiverName, String receiverPhone, String roadName, String addressDetail, String zipCode,
		Boolean isDefault) {
		this.id = id;
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

	public void changeIsDefault(Boolean isDefault){
		this.isDefault = isDefault;
	}

	public boolean isSameDefaultStatus(Address before){
		return this.isDefault == before.isDefault;
	}

	public boolean isChangedToDefault(Address before){
		return this.isDefault && !before.isDefault;
	}

	public boolean unmarkFromDefault(Address before){
		return !this.isDefault && before.isDefault;
	}
}
