package com.nhnacademy.inkbridge.backend.storage;

import static com.nhnacademy.inkbridge.backend.storage.QAddressEntity.*;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AddressQuerydslRepository {

	private final JPAQueryFactory queryFactory;

	void unmarkDefaultAddress(Long userId){
		queryFactory.update(addressEntity)
			.set(addressEntity.isDefault, false)
			.where(addressEntity.memberEntity.memberId.eq(userId).and(addressEntity.isDefault.eq(true)))
			.execute();
	}

	Long countByUserId(Long userId){
		return queryFactory.select(addressEntity.count())
			.from(addressEntity)
			.where(addressEntity.memberEntity.memberId.eq(userId))
			.fetchFirst();
	}
}
