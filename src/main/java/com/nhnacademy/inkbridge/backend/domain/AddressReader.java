package com.nhnacademy.inkbridge.backend.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressReader {

	private final AddressRepository addressRepository;

	public Address read(Long addressId){
		return addressRepository.findByAddressId(addressId)
			.orElseThrow(() -> new BusinessException(ErrorMessage.ADDRESS_NOT_EXISTS));
	}

	public List<Address> readAll(Long userId){
		return addressRepository.findAllByUserId(userId);
	}
}
