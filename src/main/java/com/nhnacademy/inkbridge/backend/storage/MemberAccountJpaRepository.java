package com.nhnacademy.inkbridge.backend.storage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAccountJpaRepository extends JpaRepository<MemberEntity, Long> {
}
