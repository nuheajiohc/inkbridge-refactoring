package com.nhnacademy.inkbridge.backend.storage.admin.publisher;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherJpaRepository extends JpaRepository<PublisherEntity, Integer> {

	boolean existsByName(String name);
}
