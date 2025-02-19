package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.dto.member.response.MemberEmailResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.repository.custom.MemberCustomRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: MemberRepository.
 *
 * @author minseo
 * @version 2/15/24
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
    /**
     * 받아온 이메일이 db에 존재하는지 체크하는 메서드.
     *
     * @param email 회원 이메일
     * @return 존재하는지 여부
     */
    boolean existsByEmail(String email);

    /**
     * 받아온 oauth 식별 아이디가 db에 존재하는지 체크하는 메서드.
     *
     * @param password oauth 회원 식별 아이디
     * @return 존재하는지 여부
     */
    boolean existsByPassword(String password);

    Optional<Member> findByEmail(String email);

    Optional<MemberEmailResponseDto> findByPassword(String password);
}