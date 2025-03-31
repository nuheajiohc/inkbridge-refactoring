package com.nhnacademy.inkbridge.backend.domain;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {

	private final AddressPolicyHandler addressPolicyHandler;
	private final AddressCommandHandler addressCommandHandler;
	private final AddressValidator addressValidator;
	private final AddressReader addressReader;

	public Long createAddress(Long userId, Address address) {
		addressPolicyHandler.validateAddressMaxLimit(userId);
		addressPolicyHandler.processDefaultAddressOnCreate(userId, address);
		return addressCommandHandler.save(userId, address);
	}

	public void updateAddress(Long userId, Address updatedAddress) {
		Address oldAddress = addressReader.read(updatedAddress.getAddressId());
		addressValidator.validateSameAddress(oldAddress, updatedAddress);

		addressPolicyHandler.processDefaultAddressOnUpdate(userId, oldAddress, updatedAddress);
		addressCommandHandler.update(updatedAddress);
	}

	public void deleteAddress(Long userId, Long addressId) {
		Address oldAddress = addressReader.read(addressId);
		addressValidator.validateMyAddress(userId, oldAddress);

		addressPolicyHandler.validateAddressMinLimit(userId);
		addressPolicyHandler.validateDefaultAddress(oldAddress);
		addressCommandHandler.delete(addressId);
	}

	@Transactional(readOnly = true)
	public List<Address> getAddresses(Long userId) {
		return addressReader.readAll(userId);
	}

	@Transactional(readOnly = true)
	public Address getAddress(Long addressId) {
		return addressReader.read(addressId);
	}
}
