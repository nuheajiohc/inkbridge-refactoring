package com.nhnacademy.inkbridge.backend.domain.admin.publisher;

public interface PublisherRepository {
	Integer save(Publisher publisher);

	boolean existsByName(String name);

	void update(Integer publisherId, Publisher publisher);
}
