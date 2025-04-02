package com.nhnacademy.inkbridge.backend.domain;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class AddressValidator {

	public void validateSameAddress(Address oldAddress, Address updatedAddress) {
		if(!Objects.equals(oldAddress.getAddressId(), updatedAddress.getAddressId())) {
			throw new BusinessException(ErrorMessage.ADDRESS_NOT_OWNED);
		}
	}

	public void validateMyAddress(Long memberId, Address address) {
		if(!Objects.equals(memberId, address.getMemberId())) {
			throw new BusinessException(ErrorMessage.ADDRESS_NOT_OWNED);
		}
	}
}
