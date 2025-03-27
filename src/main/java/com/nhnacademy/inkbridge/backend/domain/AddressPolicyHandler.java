package com.nhnacademy.inkbridge.backend.domain;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressPolicyHandler {

	private final AddressRepository addressRepository;

	public void validateAddressLimit(Long userId) {
		if (addressRepository.countAddressesByUserId(userId) == Address.ADDRESS_LIMIT) {
			throw new BusinessException(ErrorMessage.ADDRESS_LIMIT_EXCEEDED);
		}
	}

	public void processDefaultAddressOnCreate(Long userId, Address address) {
		if (address.isDefault()) {
			addressRepository.unmarkDefaultAddress(userId);
		}
	}

	public void processDefaultAddressOnUpdate(Long userId, Address updated) {
		Address oldAddress = addressRepository.findByAddressId(updated.getId())
			.orElseThrow(() -> new BusinessException(ErrorMessage.ADDRESS_NOT_EXISTS));

		if (updated.isSameDefaultStatus(oldAddress)) {
			return;
		}

		if (updated.unmarkFromDefault(oldAddress)) {
			updated.changeIsDefault(oldAddress.isDefault());
			return;
		}

		if (updated.isChangedToDefault(oldAddress)) {
			addressRepository.unmarkDefaultAddress(userId);
		}

	}
}
