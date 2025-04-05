package com.nhnacademy.inkbridge.backend.storage.admin.publisher;

import static com.nhnacademy.inkbridge.backend.storage.admin.publisher.QPublisherEntity.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.nhnacademy.inkbridge.backend.domain.DomainStatus;
import com.nhnacademy.inkbridge.backend.domain.admin.publisher.Publisher;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PublisherQuerydslRepository {

	private final JPAQueryFactory queryFactory;

	public Page<Publisher> findPublishersByPage(DomainStatus status, Integer page, Integer size) {
		page = page == null ? 0 : page;
		size = size == null ? 10 : size;

		int offset = page * size;

		List<Publisher> publishers = queryFactory.select(Projections.constructor(Publisher.class,
				publisherEntity.id,
				publisherEntity.name,
				publisherEntity.status
			))
			.from(publisherEntity)
			.where(eqDomainStatus(status))
			.orderBy(publisherEntity.id.asc())
			.offset(offset)
			.limit(size)
			.fetch();

		Long count = queryFactory.select(publisherEntity.count())
			.from(publisherEntity)
			.where(eqDomainStatus(status))
			.fetchFirst();
		System.out.println("count: " + count);
		return new PageImpl<>(publishers, PageRequest.of(page, size), count);
	}

	private BooleanExpression eqDomainStatus(DomainStatus status) {
		if (status == null) {
			return null;
		}

		return publisherEntity.status.eq(status);
	}
}
