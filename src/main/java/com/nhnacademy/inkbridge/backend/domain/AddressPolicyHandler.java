package com.nhnacademy.inkbridge.backend.domain;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressPolicyHandler {

	private final AddressRepository addressRepository;

	public void validateAddressMaxLimit(Long userId) {
		long totalAddressCount = addressRepository.countAddressesByUserId(userId);
		if (Address.isMaxLimit(totalAddressCount)) {
			throw new BusinessException(ErrorMessage.ADDRESS_LIMIT_EXCEEDED);
		}
	}

	public void validateAddressMinLimit(Long userId) {
		long totalAddressCount = addressRepository.countAddressesByUserId(userId);
		if (Address.isMinLimit(totalAddressCount)) {
			throw new BusinessException(ErrorMessage.ADDRESS_DELETE_FORBIDDEN);
		}
	}

	public void validateDefaultAddress(Address address) {
		if (address.isDefault()) {
			throw new BusinessException(ErrorMessage.ADDRESS_DELETE_FORBIDDEN);
		}
	}

	public void processDefaultAddressOnCreate(Long userId, Address address) {
		if (address.isDefault()) {
			addressRepository.unmarkDefaultAddress(userId);
		}
	}

	public void processDefaultAddressOnUpdate(Long userId, Address oldAddress, Address updated) {
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
