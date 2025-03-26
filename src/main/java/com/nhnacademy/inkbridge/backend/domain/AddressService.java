package com.nhnacademy.inkbridge.backend.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

	private final AddressPolicyHandler addressPolicyHandler;
	private final AddressRepository addressRepository;

	public void createAddress(Long userId, Address address) {
		addressPolicyHandler.validateAddressLimit(userId);
		addressPolicyHandler.unmarkDefaultAddress(userId, address);
		addressRepository.save(userId, address);
	}
}
