package com.nhnacademy.inkbridge.backend.domain;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressPolicyHandler {

	private final int ADDRESS_LIMIT = 10;
	private final AddressRepository addressRepository;

	void validateAddressLimit(Long userId) {
		if (addressRepository.countAddressesByUserId(userId) == ADDRESS_LIMIT) {
			throw new BusinessException(ErrorMessage.ADDRESS_LIMIT_EXCEEDED);
		}
	}

	void unmarkDefaultAddress(Long userId, Address address) {
		if(address.isDefault()){
			addressRepository.unmarkDefaultAddress(userId);
		}
	}
}
