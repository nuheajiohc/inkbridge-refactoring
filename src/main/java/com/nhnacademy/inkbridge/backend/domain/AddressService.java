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
	private final AddressRepository addressRepository;

	public Long createAddress(Long userId, Address address) {
		addressPolicyHandler.validateAddressMaxLimit(userId);
		addressPolicyHandler.processDefaultAddressOnCreate(userId, address);
		return addressCommandHandler.save(userId, address);
	}

	public void updateAddress(Long userId, Address address) {
		addressPolicyHandler.processDefaultAddressOnUpdate(userId, address);
		addressCommandHandler.update(address);
	}

	public void deleteAddress(Long userId, Long addressId) {
		addressPolicyHandler.validateAddressMinLimit(userId);
		addressPolicyHandler.validateDefaultAddress(addressId);
		addressCommandHandler.delete(addressId);
	}

	@Transactional(readOnly = true)
	public List<Address> getAddresses(Long userId) {
		return addressRepository.findAllByUserId(userId);
	}

	@Transactional(readOnly = true)
	public Address getAddress(Long addressId) {
		return addressRepository.findByAddressId(addressId)
			.orElseThrow(() -> new BusinessException(ErrorMessage.ADDRESS_NOT_EXISTS));
	}
}
