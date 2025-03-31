package com.nhnacademy.inkbridge.backend.storage.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminAccountJpaRepository extends JpaRepository<AdminEntity, Long> {
}
