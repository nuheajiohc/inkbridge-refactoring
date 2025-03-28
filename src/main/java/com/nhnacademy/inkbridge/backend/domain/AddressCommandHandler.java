package com.nhnacademy.inkbridge.backend.domain;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressCommandHandler {

	private final AddressRepository addressRepository;

	public Long save(Long userId,Address address) {
		return addressRepository.save(userId, address);
	}

	public void update(Address address) {
		addressRepository.update(address);
	}

	public void delete(Long addressId) {
		addressRepository.deleteById(addressId);
	}
}
