package com.nhnacademy.inkbridge.backend.domain;

public interface AddressRepository {

	void save(Long userId, Address address);

	void unmarkDefaultAddress(Long userId);

	Long countAddressesByUserId(Long userId);
}
