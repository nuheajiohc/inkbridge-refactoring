package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * class: MemberStatusRepository.
 *
 * @author minseo
 * @version 2/15/24
 */
public interface MemberStatusRepository extends JpaRepository<MemberStatus, Integer> {
}