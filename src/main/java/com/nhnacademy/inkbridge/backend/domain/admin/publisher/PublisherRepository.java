package com.nhnacademy.inkbridge.backend.domain.admin.publisher;

import org.springframework.data.domain.Page;

import com.nhnacademy.inkbridge.backend.domain.DomainStatus;

public interface PublisherRepository {
	Integer save(Publisher publisher);

	boolean existsByName(String name);

	void update(Integer publisherId, Publisher publisher);

	void delete(Integer publisherId);

	Publisher findById(Integer publishId);

	Page<Publisher> findAllByPage(DomainStatus domainStatus, Integer page, Integer size);
}
