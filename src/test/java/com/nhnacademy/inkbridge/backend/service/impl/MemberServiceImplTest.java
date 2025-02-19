package com.nhnacademy.inkbridge.backend.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberAuthLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberUpdatePasswordRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberEmailResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberInfoResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAuth;
import com.nhnacademy.inkbridge.backend.entity.MemberGrade;
import com.nhnacademy.inkbridge.backend.entity.MemberStatus;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.MemberAuthRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberGradeRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberStatusRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * class: MemberServiceImplTest.
 *
 * @author devminseo
 * @version 3/21/24
 */
@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    MemberRepository memberRepository;
    @Mock
    MemberAuthRepository memberAuthRepository;
    @Mock
    MemberStatusRepository memberStatusRepository;
    @Mock
    MemberGradeRepository memberGradeRepository;
    MemberCreateRequestDto memberCreateRequestDto;
    MemberAuthLoginRequestDto memberAuthLoginRequestDto;
    MemberAuthLoginResponseDto memberAuthLoginResponseDto;
    MemberUpdateRequestDto memberUpdateRequestDto;
    MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto;
    MemberAuth memberAuth;
    MemberStatus memberStatus;
    MemberGrade memberGrade;
    Member member;
    ArgumentCaptor<Member> captor;

    @BeforeEach
    void setUp() {
        captor = ArgumentCaptor.forClass(Member.class);
        memberCreateRequestDto = new MemberCreateRequestDto();
        memberAuthLoginRequestDto = new MemberAuthLoginRequestDto();
        memberUpdateRequestDto = new MemberUpdateRequestDto();
        memberUpdatePasswordRequestDto = new MemberUpdatePasswordRequestDto();

        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto, "password",
            "$2a$10$ILNBmH6tPNBa8/WeZ4hvi.BHj4bcpUKWcCM/Zc2SLIHBgvForZdHq");
        ReflectionTestUtils.setField(memberCreateRequestDto, "memberName", "이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto, "birthday", LocalDate.now());
        ReflectionTestUtils.setField(memberCreateRequestDto, "phoneNumber", "01012345678");

        memberAuth = new MemberAuth(1, "ROLE_MEMBER");
        memberStatus = new MemberStatus(1, "ACTIVE");
        memberGrade = new MemberGrade(1, "STANDARD", BigDecimal.ZERO, 0L);
        member = Member.create().createdAt(LocalDateTime.now()).memberAuth(memberAuth)
            .memberGrade(memberGrade)
            .memberName(memberCreateRequestDto.getMemberName())
            .birthday(memberCreateRequestDto.getBirthday())
            .password(memberCreateRequestDto.getPassword())
            .phoneNumber(memberCreateRequestDto.getPhoneNumber())
            .memberStatus(memberStatus).email(memberCreateRequestDto.getEmail()).memberPoint(0L)
            .build();

        memberAuthLoginResponseDto =
            new MemberAuthLoginResponseDto(1L, "sa4777@naver.com", "password", new ArrayList<>());
    }

    @Test
    @DisplayName("멤버 생성")
    void createMember_success() {
        when(memberRepository.existsByEmail(any())).thenReturn(false);
        when(memberAuthRepository.findById(any())).thenReturn(Optional.of(memberAuth));
        when(memberStatusRepository.findById(any())).thenReturn(Optional.of(memberStatus));
        when(memberGradeRepository.findById(any())).thenReturn(Optional.of(memberGrade));

        when(memberRepository.save(any())).thenReturn(member);

        memberService.createMember(memberCreateRequestDto);

        verify(memberRepository, times(1)).save(captor.capture());

        String email = captor.getValue().getEmail();

        assertThat(memberCreateRequestDto.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("멤버 생성 - 소셜 회원")
    void createMember_success_social() {
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "SOCIAL sa4777@naver.com");

        when(memberRepository.existsByEmail(any())).thenReturn(false);
        when(memberAuthRepository.findById(any())).thenReturn(
            Optional.of(memberAuth = new MemberAuth(3, "ROLE_SOCIAL")));
        when(memberStatusRepository.findById(any())).thenReturn(Optional.of(memberStatus));
        when(memberGradeRepository.findById(any())).thenReturn(Optional.of(memberGrade));

        if (memberCreateRequestDto.getEmail().startsWith("SOCIAL ")) {
            ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        }

        when(memberRepository.save(any())).thenReturn(member);

        memberService.createMember(memberCreateRequestDto);

        verify(memberRepository, times(1)).save(captor.capture());

        MemberAuth socialAuth = captor.getValue().getMemberAuth();

        assertThat(memberAuth.getMemberAuthName()).isEqualTo(socialAuth.getMemberAuthName());
    }

    @Test
    @DisplayName("멤버 생성 실패 - 이메일 이미 존재하는 경우")
    void createMember_fail() {
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");

        when(memberRepository.existsByEmail(any())).thenReturn(true);

        assertThatThrownBy(() -> memberService.createMember(memberCreateRequestDto)).isInstanceOf(
                NotFoundException.class)
            .hasMessageContaining(MemberMessageEnum.MEMBER_ALREADY_EXIST.getMessage());
    }

    @Test
    @DisplayName("로그인 정보 가져오기 성공")
    void login_InfoMember_success() {
        ReflectionTestUtils.setField(memberAuthLoginRequestDto, "email", "sa4777@naver.com");

        when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(member));
        when(memberStatusRepository.findById(any())).thenReturn(
            Optional.of(memberStatus = new MemberStatus(3, "CLOSE")));

        Assertions.assertNotEquals(member.getMemberStatus().getMemberStatusName(),
            memberStatus.getMemberStatusName());

        when(memberRepository.findByMemberAuth(anyString())).thenReturn(memberAuthLoginResponseDto);

        MemberAuthLoginResponseDto result = memberService.loginInfoMember(
            memberAuthLoginRequestDto);

        assertThat(result.getMemberId()).isEqualTo(memberAuthLoginResponseDto.getMemberId());
        assertThat(result.getPassword()).isEqualTo(memberAuthLoginResponseDto.getPassword());
        assertThat(result.getEmail()).isEqualTo(memberAuthLoginResponseDto.getEmail());
        assertThat(result.getRole()).isEqualTo(memberAuthLoginResponseDto.getRole());

        verify(memberRepository, times(1)).findByMemberAuth("sa4777@naver.com");

    }

    @Test
    @DisplayName("로그인 정보 가져오기 실패 - 회원을 찾을 수 없는 경우")
    void login_InfoMember_fail_memberNotFound() {
        when(memberRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.loginInfoMember(memberAuthLoginRequestDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("회원 정보 가져오기 성공")
    void getMemberInfo_success() {
        MemberInfoResponseDto memberInfoResponseDto = new MemberInfoResponseDto(
                1L,
                "이민서",
                "sa4777@naver.com",
                "01012345678",
                "1999-07-04",
                5000L,
                new ArrayList<>()
        );
        when(memberRepository.findByMemberInfo(anyLong())).thenReturn(Optional.of(memberInfoResponseDto));

        MemberInfoResponseDto result = memberService.getMemberInfo(1L);

        assertThat(result).isEqualTo(memberInfoResponseDto);
    }

    @Test
    @DisplayName("회원 정보 가져오기 실패")
    void getMemberInfo_fail() {
        when(memberRepository.findByMemberInfo(anyLong())).thenReturn(Optional.empty());

        assertThat(memberService.getMemberInfo(1L)).isNull();
    }

    @Test
    @DisplayName("소셜 회원 확인 성공")
    void checkOAuthMember_exists() {
        when(memberRepository.existsByPassword(anyString())).thenReturn(true);

        assertTrue(memberService.checkOAuthMember(anyString()));
    }

    @Test
    @DisplayName("소셜 회원 확인 실패")
    void checkOAuthMember_notExists() {
        when(memberRepository.existsByPassword(anyString())).thenReturn(false);

        assertFalse(memberService.checkOAuthMember(anyString()));
    }

    @Test
    @DisplayName("소셜 회원 이메일 가져오기 성공")
    void getOAuthMemberEmail_success() {
        MemberEmailResponseDto emailResponseDto = new MemberEmailResponseDto("sa4777@naver.com");
        when(memberRepository.findByPassword(anyString())).thenReturn(Optional.of(emailResponseDto));

        String email = memberService.getOAuthMemberEmail("sa4777@naver.com");

        assertThat(email).isEqualTo("sa4777@naver.com");
    }

    @Test
    @DisplayName("소셜 회원 이메일 가져오기 실패")
    void getOAuthMemberEmail_fail() {
        when(memberRepository.findByPassword(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.getOAuthMemberEmail("sa4777@naver.com"))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("중복되는 이메일 확인 성공")
    void checkDuplicatedEmail_success() {
        when(memberRepository.existsByEmail(anyString())).thenReturn(true);

        assertTrue(memberService.checkDuplicatedEmail("sa4777@naver.com"));
    }

    @Test
    @DisplayName("중복되는 이메일 확인 실패")
    void checkDuplicatedEmail_fail() {
        when(memberRepository.existsByEmail(anyString())).thenReturn(false);

        assertFalse(memberService.checkDuplicatedEmail(anyString()));
    }

    @Test
    @DisplayName("멤버 정보 업데이트 성공")
    void updateMember_success() {
        ReflectionTestUtils.setField(memberUpdateRequestDto,"memberName","minseo");
        ReflectionTestUtils.setField(memberUpdateRequestDto,"email","sa4777@naver.com");
        ReflectionTestUtils.setField(memberUpdateRequestDto,"phoneNumber","01011112222");

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        memberService.updateMember(memberUpdateRequestDto, 1L);

        assertAll(
                () -> assertEquals(member.getEmail(), memberUpdateRequestDto.getEmail()),
                () -> assertEquals(member.getMemberName(), memberUpdateRequestDto.getMemberName()),
                () -> assertEquals(member.getPhoneNumber(), memberUpdateRequestDto.getPhoneNumber())
        );
    }

    @Test
    @DisplayName("멤버 정보 업데이트 실패")
    void updateMember_fail() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.updateMember(memberUpdateRequestDto, 1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("비밀번호 업데이트 성공")
    void updatePassword_success() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        assertTrue(memberService.updatePassword(memberUpdatePasswordRequestDto, 1L));
    }

    @Test
    @DisplayName("비밀번호 업데이트 실패")
    void updatePassword_fail() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.updatePassword(memberUpdatePasswordRequestDto, 1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("비밀번호 가져오기 성공")
    void getPassword_success() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        String password = memberService.getPassword(1L);

        assertThat(password).isEqualTo("$2a$10$ILNBmH6tPNBa8/WeZ4hvi.BHj4bcpUKWcCM/Zc2SLIHBgvForZdHq");
    }

    @Test
    @DisplayName("비밀번호 가져오기 실패")
    void getPassword_fail() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.getPassword(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("회원 삭제 성공")
    void deleteMember_success() {
        MemberStatus close = new MemberStatus(1, "CLOSE");
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        when(memberStatusRepository.findById(any())).thenReturn(Optional.of(close));

        memberService.deleteMember(1L);

        assertEquals(member.getMemberStatus(), close);
    }

    @Test
    @DisplayName("회원 삭제 실패")
    void deleteMember_fail() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> memberService.deleteMember(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
    }
}