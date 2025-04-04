package com.nhnacademy.inkbridge.backend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class Address {

	public static int MAX_LIMIT = 10;
	public static int MIN_LIMIT = 1;

	private Long addressId;
	private String receiverName;
	private String receiverPhone;
	private String roadName;
	private String addressDetail;
	private String zipCode;
	private Boolean isDefault;
	private Long memberId;

	@Builder
	public Address(Long addressId, String receiverName, String receiverPhone, String roadName, String addressDetail,
		String zipCode, Boolean isDefault, Long memberId) {
		this.addressId = addressId;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.roadName = roadName;
		this.addressDetail = addressDetail;
		this.zipCode = zipCode;
		this.isDefault = isDefault;
		this.memberId = memberId;
	}

	public static boolean isMaxLimit(long count) {
		return count == MAX_LIMIT;
	}

	public static boolean isMinLimit(long count) {
		return count == MIN_LIMIT;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void changeIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean isSameDefaultStatus(Address before) {
		return this.isDefault == before.isDefault;
	}

	public boolean isChangedToDefault(Address before) {
		return this.isDefault && !before.isDefault;
	}

	public boolean unmarkFromDefault(Address before) {
		return !this.isDefault && before.isDefault;
	}
}
