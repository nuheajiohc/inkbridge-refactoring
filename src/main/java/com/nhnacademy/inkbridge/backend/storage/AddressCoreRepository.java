package com.nhnacademy.inkbridge.backend.storage;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nhnacademy.inkbridge.backend.domain.Address;
import com.nhnacademy.inkbridge.backend.domain.AddressRepository;
import com.nhnacademy.inkbridge.backend.domain.BusinessException;
import com.nhnacademy.inkbridge.backend.domain.ErrorMessage;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AddressCoreRepository implements AddressRepository {

	private final MemberRepository memberRepository;
	private final AddressJpaRepository addressJpaRepository;
	private final AddressQuerydslRepository addressQuerydslRepository;

	@Override
	public void save(Long userId, Address address) {
		addressJpaRepository.save(
			AddressEntity.builder()
				.roadName(address.getRoadName())
				.addressDetail(address.getAddressDetail())
				.zipCode(address.getZipCode())
				.isDefault(address.getIsDefault())
				.receiverName(address.getReceiverName())
				.receiverPhone(address.getReceiverPhone())
				.memberEntity(memberRepository.getReferenceById(userId))
				.build()
		);
	}

	@Override
	public void unmarkDefaultAddress(Long userId) {
		addressQuerydslRepository.unmarkDefaultAddress(userId);
	}

	@Override
	public Long countAddressesByUserId(Long userId) {
		return addressQuerydslRepository.countByUserId(userId);
	}

	@Override
	public Optional<Address> findByAddressId(Long addressId){
		return addressJpaRepository.findById(addressId).map(AddressEntity::toAddress);
	}

	@Override
	public void update(Address address) {
		AddressEntity addressEntity = addressJpaRepository.findById(address.getId())
			.orElseThrow(() -> new BusinessException(ErrorMessage.ADDRESS_NOT_EXISTS));
		addressEntity.update(address);
	}

	@Override
	public void deleteById(Long addressId) {
		addressJpaRepository.deleteById(addressId);
	}
}
