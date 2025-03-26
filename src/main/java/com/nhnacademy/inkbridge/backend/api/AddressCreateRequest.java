package com.nhnacademy.inkbridge.backend.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nhnacademy.inkbridge.backend.domain.Address;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressCreateRequest {

	@NotBlank
	@Size(max=20)
	private String receiverName;
	@NotBlank
	@Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
	private String receiverPhone;
	@NotBlank
	@Size(max=50)
	private String roadName;
	@NotBlank
	@Size(max=50)
	private String addressDetail;
	@NotBlank
	@Size(min=5, max=5)
	private String zipCode;
	@NotNull
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
