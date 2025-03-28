package com.nhnacademy.inkbridge.backend.storage;

import static com.nhnacademy.inkbridge.backend.storage.QAddressEntity.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nhnacademy.inkbridge.backend.domain.Address;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AddressQuerydslRepository {

	private final JPAQueryFactory queryFactory;

	void unmarkDefaultAddress(Long userId) {
		queryFactory.update(addressEntity)
			.set(addressEntity.isDefault, false)
			.where(addressEntity.memberEntity.memberId.eq(userId).and(addressEntity.isDefault.eq(true)))
			.execute();
	}

	Long countByUserId(Long userId) {
		return queryFactory.select(addressEntity.count())
			.from(addressEntity)
			.where(addressEntity.memberEntity.memberId.eq(userId))
			.fetchFirst();
	}

	public List<Address> findAllByUserId(Long userId) {
		return queryFactory.select(Projections.constructor(Address.class,
				addressEntity.id,
				addressEntity.receiverName,
				addressEntity.receiverPhone,
				addressEntity.roadName,
				addressEntity.addressDetail,
				addressEntity.zipCode,
				addressEntity.isDefault
			))
			.from(addressEntity)
			.where(addressEntity.memberEntity.memberId.eq(userId))
			.orderBy(addressEntity.isDefault.desc(), addressEntity.updatedAt.desc())
			.fetch();
	}
}
