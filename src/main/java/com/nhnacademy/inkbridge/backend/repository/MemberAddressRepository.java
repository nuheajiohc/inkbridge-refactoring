package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAddress;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: MemberAddressRepository.
 *
 * @author jeongbyeonghun
 * @version 3/9/24
 */
public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {

    List<MemberAddress> findAllByMemberMemberId(Long memberId);

    Optional<MemberAddress> findByMemberAndAddressId(Member member, Long addressId);

    Optional<MemberAddress> findByMemberMemberIdAndAddressId(Long memberId, Long addressId);
}
