package com.nhnacademy.inkbridge.backend.domain;

import java.util.Optional;

public interface AddressRepository {

	void save(Long userId, Address address);

	void unmarkDefaultAddress(Long userId);

	Long countAddressesByUserId(Long userId);

	void update(Address address);

	Optional<Address> findByAddressId(Long id);
}
