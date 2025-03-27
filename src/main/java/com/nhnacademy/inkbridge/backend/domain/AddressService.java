package com.nhnacademy.inkbridge.backend.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

	private final AddressPolicyHandler addressPolicyHandler;
	private final AddressCommandHandler addressCommandHandler;

	public void createAddress(Long userId, Address address) {
		addressPolicyHandler.validateAddressLimit(userId);
		addressPolicyHandler.processDefaultAddressOnCreate(userId, address);
		addressCommandHandler.save(userId, address);
	}

	public void updateAddress(Long userId, Address address) {
		addressPolicyHandler.processDefaultAddressOnUpdate(userId, address);
		addressCommandHandler.update(address);
	}
}
