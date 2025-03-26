package com.nhnacademy.inkbridge.backend.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AddressJpaRepository extends JpaRepository<AddressEntity, Long> {

	@Modifying
	@Query("UPDATE AddressEntity a SET a.isDefault = false WHERE a.memberEntity.memberId =: userId AND a.isDefault = true")
	void unmarkDefaultAddress(Long userId);

	// @Query("SELECT COUNT()")
	// Long countByMemberId(Long memberId);
}
