package com.nhnacademy.inkbridge.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberInfoResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAuth;
import com.nhnacademy.inkbridge.backend.entity.MemberGrade;
import com.nhnacademy.inkbridge.backend.entity.MemberStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * class: MemberRepositoryTest.
 *
 * @author devminseo
 * @version 3/24/24
 */
@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    MemberRepository memberRepository;

    Member member;
    MemberAuth memberAuth;
    MemberGrade memberGrade;
    MemberStatus memberStatus;

    @BeforeEach
    void setUp() {
        memberAuth = MemberAuth.create()
                .memberAuthId(1)
                .memberAuthName("MEMBER")
                .build();
        entityManager.persist(memberAuth);

        memberStatus = MemberStatus.create()
                .memberStatusId(1)
                .memberStatusName("ACTIVE")
                .build();

        entityManager.persist(memberStatus);

        memberGrade = MemberGrade.create()
                .gradeId(1)
                .grade("STANDARD")
                .pointRate(BigDecimal.valueOf(1L))
                .standardAmount(100000L)
                .build();

        entityManager.persist(memberGrade);

        member = Member.create()
                .memberName("이민서")
                .email("sa4777@naver.com")
                .memberPoint(0L)
                .memberStatus(memberStatus)
                .phoneNumber("01011112222")
                .password("password")
                .birthday(LocalDate.of(1999,7,4))
                .memberGrade(memberGrade)
                .memberAuth(memberAuth)
                .createdAt(LocalDateTime.now())
                .build();
        entityManager.persist(member);
    }

    @Test
    @DisplayName("로그인에 필요한 정보 가져오기")
    void testFindByMemberAuth() {
        MemberAuthLoginResponseDto result = memberRepository.findByMemberAuth(member.getEmail());

        assertThat(result.getRole()).isEqualTo(List.of(member.getMemberAuth().getMemberAuthName()));
        assertThat(result.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(result.getPassword()).isEqualTo(member.getPassword());
        assertThat(result.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    @DisplayName("로그인후 회원 정보 가져오기")
    void testFindyByMemberInfo() {
        Optional<MemberInfoResponseDto> result = memberRepository.findByMemberInfo(member.getMemberId());

        assertThat(result).isPresent();
        assertThat(result.get().getMemberId()).isEqualTo(result.get().getMemberId());
        assertThat(result.get().getMemberName()).isEqualTo(result.get().getMemberName());
        assertThat(result.get().getMemberPoint()).isEqualTo(result.get().getMemberPoint());
        assertThat(result.get().getBirthday()).isEqualTo(result.get().getBirthday());
        assertThat(result.get().getEmail()).isEqualTo(result.get().getEmail());
        assertThat(result.get().getPhoneNumber()).isEqualTo(result.get().getPhoneNumber());
        assertThat(result.get().getRoles()).isEqualTo(result.get().getRoles());

    }
}