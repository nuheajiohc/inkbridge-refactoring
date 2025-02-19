package com.nhnacademy.inkbridge.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberAuthLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberEmailRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberIdNoRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberInfoResponseDto;
import com.nhnacademy.inkbridge.backend.facade.MemberFacade;
import com.nhnacademy.inkbridge.backend.facade.OrderFacade;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import com.nhnacademy.inkbridge.backend.service.MemberService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: MemberControllerTest.
 *
 * @author devminseo
 * @version 3/20/24
 */
@AutoConfigureRestDocs
@WebMvcTest(MemberController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private MemberFacade memberFacade;
    @MockBean
    private MemberService memberService;
    @MockBean
    private OrderFacade orderFacade;
    @MockBean
    private CouponService couponService;
    private ObjectMapper objectMapper;
    MemberCreateRequestDto memberCreateRequestDto;
    MemberAuthLoginRequestDto memberAuthLoginRequestDto;
    MemberEmailRequestDto memberEmailRequestDto;
    MemberAuthLoginResponseDto memberAuthLoginResponseDto;
    MemberInfoResponseDto memberInfoResponseDto;
    MemberIdNoRequestDto memberIdNoRequestDto;
    LocalDate today = LocalDate.now();
    @MockBean
    HttpServletRequest request;

    String uri = "/api/members";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        memberIdNoRequestDto = new MemberIdNoRequestDto();
        memberCreateRequestDto = new MemberCreateRequestDto();
        memberAuthLoginRequestDto = new MemberAuthLoginRequestDto();
        memberEmailRequestDto = new MemberEmailRequestDto();
        memberAuthLoginResponseDto = new MemberAuthLoginResponseDto(
                1L,
                "sa4777@naver.com",
                "$2a$10$ILNBmH6tPNBa8/WeZ4hvi.BHj4bcpUKWcCM/Zc2SLIHBgvForZdHq",
                List.of("MEMBER")
        );

        memberInfoResponseDto = new MemberInfoResponseDto(
                1L,
                "이민서",
                "sa4777@naver.com",
                "01012345678",
                "1999-07-04",
                5000L,
                List.of("MEMBER")
        );

        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("멤버 회원가입 성공")
    void member_create_success() throws Exception {
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto, "password",
                "$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto, "memberName", "이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto, "birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto, "phoneNumber", "01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("member/member-create",
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("패스워드"),
                                fieldWithPath("memberName").description("이름"),
                                fieldWithPath("birthday").description("생일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호")
                        )));
    }

    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 이메일 널")
    void member_create_email_null() throws Exception {
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", null);
        ReflectionTestUtils.setField(memberCreateRequestDto, "password",
                "$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto, "memberName", "이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto, "birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto, "phoneNumber", "01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이메일은 필수 입력 값 입니다.]"))
                .andDo(print())
                .andDo(document("member/member-create-email-null-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("패스워드"),
                                fieldWithPath("memberName").description("이름"),
                                fieldWithPath("birthday").description("생일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호")),
                        responseFields(
                                fieldWithPath("message").description("실패 사유")
                        )));
    }

    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 비밀번호 널")
    void member_create_password_null() throws Exception {
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto, "password", null);
        ReflectionTestUtils.setField(memberCreateRequestDto, "memberName", "이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto, "birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto, "phoneNumber", "01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[비밀번호는 필수 입력 값 입니다.]"))
                .andDo(print())
                .andDo(document("member/member-create-password-null-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("패스워드"),
                                fieldWithPath("memberName").description("이름"),
                                fieldWithPath("birthday").description("생일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호")),
                        responseFields(
                                fieldWithPath("message").description("실패 사유")
                        )));
    }

    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 비밀번호 공백")
    void member_create_password_blank() throws Exception {
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto, "password", "");
        ReflectionTestUtils.setField(memberCreateRequestDto, "memberName", "이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto, "birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto, "phoneNumber", "01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[비밀번호는 필수 입력 값 입니다.]"))
                .andDo(print())
                .andDo(document("member/member-create-password-blank-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("패스워드"),
                                fieldWithPath("memberName").description("이름"),
                                fieldWithPath("birthday").description("생일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호")),
                        responseFields(
                                fieldWithPath("message").description("실패 사유")
                        )));
    }


    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 이름 널")
    void member_create_memberName_null() throws Exception {
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto, "password",
                "$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto, "memberName", null);
        ReflectionTestUtils.setField(memberCreateRequestDto, "birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto, "phoneNumber", "01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이름은 필수 입력 값 입니다.]"))
                .andDo(print())
                .andDo(document("member/member-create-memberName-null-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("패스워드"),
                                fieldWithPath("memberName").description("이름"),
                                fieldWithPath("birthday").description("생일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호")),
                        responseFields(
                                fieldWithPath("message").description("실패 사유")
                        )));
    }

    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 이름 공백")
    void member_create_memberName_blank() throws Exception {
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto, "password",
                "$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto, "memberName", "");
        ReflectionTestUtils.setField(memberCreateRequestDto, "birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto, "phoneNumber", "01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이름은 필수 입력 값 입니다.]"))
                .andDo(print())
                .andDo(document("member/member-create-memberName-blank-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("패스워드"),
                                fieldWithPath("memberName").description("이름"),
                                fieldWithPath("birthday").description("생일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호")),
                        responseFields(
                                fieldWithPath("message").description("실패 사유")
                        )));
    }

    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 생일 널")
    void member_create_birthday_null() throws Exception {
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto, "password",
                "$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto, "memberName", "이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto, "birthday", null);
        ReflectionTestUtils.setField(memberCreateRequestDto, "phoneNumber", "01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[생일은 필수 입력 값 입니다.]"))
                .andDo(print())
                .andDo(document("member/member-create-birthday-null-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("패스워드"),
                                fieldWithPath("memberName").description("이름"),
                                fieldWithPath("birthday").description("생일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호")),
                        responseFields(
                                fieldWithPath("message").description("실패 사유")
                        )));
    }

    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 핸드폰 번호 널")
    void member_create_phoneNumber_null() throws Exception {
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto, "password",
                "$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto, "memberName", "이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto, "birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto, "phoneNumber", null);

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[핸드폰 번호는 필수 입력 값 입니다.]"))
                .andDo(print())
                .andDo(document("member/member-create-phoneNumber-null-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("패스워드"),
                                fieldWithPath("memberName").description("이름"),
                                fieldWithPath("birthday").description("생일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호")),
                        responseFields(
                                fieldWithPath("message").description("실패 사유")
                        )));
    }

    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 핸드폰 번호 공백")
    void member_create_phoneNumber_blank() throws Exception {
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto, "password",
                "$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto, "memberName", "이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto, "birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto, "phoneNumber", "");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[핸드폰 번호는 필수 입력 값 입니다.]"))
                .andDo(print()).andDo(document("member/member-create-phoneNumber-blank-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("패스워드"),
                                fieldWithPath("memberName").description("이름"),
                                fieldWithPath("birthday").description("생일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호")),
                        responseFields(
                                fieldWithPath("message").description("실패 사유")
                        )));

    }


    @Test
    @DisplayName("로그인 정보 가져오기 성공")
    void auth_login_success() throws Exception {
        ReflectionTestUtils.setField(memberAuthLoginRequestDto, "email", "sa4777@naver.com");

        when(memberService.loginInfoMember(any())).thenReturn(memberAuthLoginResponseDto);

        mockMvc.perform(post(uri + "/login")
                        .content(objectMapper.writeValueAsString(memberAuthLoginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.memberId").value(memberAuthLoginResponseDto.getMemberId()))
                .andExpect(jsonPath("$.email").value(memberAuthLoginResponseDto.getEmail()))
                .andExpect(jsonPath("$.password").value(memberAuthLoginResponseDto.getPassword()))
                .andExpect(jsonPath("$.role[0]").value(memberAuthLoginResponseDto.getRole().get(0)))
                .andDo(document("member/member-auth-login-success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일")),
                        responseFields(
                                fieldWithPath("memberId").description("멤버 아이디"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("패스워드"),
                                fieldWithPath("role.[]").description("권한")
                        )));

    }

    @Test
    @DisplayName("로그인 정보 가져오기 실패 - 유효성 검사 실패 - 이메일 패턴")
    void auth_login_email_pattern() throws Exception {
        ReflectionTestUtils.setField(memberAuthLoginRequestDto, "email", "sa4777naver.com");

        when(memberService.loginInfoMember(any())).thenReturn(memberAuthLoginResponseDto);

        mockMvc.perform(post(uri + "/login")
                        .content(objectMapper.writeValueAsString(memberAuthLoginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이메일 형식이 틀렸습니다.]"))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 정보 가져오기 실패 - 유효성 검사 실패 - 이메일 널")
    void auth_login_email_null() throws Exception {
        ReflectionTestUtils.setField(memberAuthLoginRequestDto, "email", null);

        when(memberService.loginInfoMember(any())).thenReturn(memberAuthLoginResponseDto);

        mockMvc.perform(post(uri + "/login")
                        .content(objectMapper.writeValueAsString(memberAuthLoginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이메일은 필수 입력 값 입니다.]"))
                .andDo(print())
                .andDo(document("member/member-auth-login-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일")),
                        responseFields(
                                fieldWithPath("message").description("실패 사유")
                        )));
    }

    @Test
    @DisplayName("이메일 중복체크 성공")
    void is_duplicated_email_success() throws Exception {
        ReflectionTestUtils.setField(memberEmailRequestDto, "email", "sa4777@naver.com");

        when(memberService.checkDuplicatedEmail(memberEmailRequestDto.getEmail())).thenReturn(true);

        mockMvc.perform(post(uri + "/checkEmail")
                        .content(objectMapper.writeValueAsString(memberEmailRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"))
                .andDo(document("member/member-duplicated-email-success",
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일")
                        )));
    }

    @Test
    @DisplayName("이메일 중복체크 실패")
    void is_duplicated_email_fail() throws Exception {
        ReflectionTestUtils.setField(memberEmailRequestDto, "email", "sa4777@naver.com");

        when(memberService.checkDuplicatedEmail(memberEmailRequestDto.getEmail())).thenReturn(
                false);

        mockMvc.perform(post(uri + "/checkEmail")
                        .content(objectMapper.writeValueAsString(memberEmailRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"))
                .andDo(document("member/member-duplicated-email-fail",
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일")
                        )));
    }

    @Test
    @DisplayName("이메일 중복체크 실패 - 유효성 검사 실패 - 이메일 패턴")
    void is_duplicated_email_pattern() throws Exception {
        ReflectionTestUtils.setField(memberEmailRequestDto, "email", "sa4777avcom.com");

        when(memberService.checkDuplicatedEmail(memberEmailRequestDto.getEmail())).thenReturn(
                false);

        mockMvc.perform(post(uri + "/checkEmail")
                        .content(objectMapper.writeValueAsString(memberEmailRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이메일이 형식에 맞지 않습니다.]"))
                .andDo(print())
                .andDo(document("member/member-duplicated-email-pattern-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일")),
                        responseFields(
                                fieldWithPath("message").description("실패 사유")
                        )));
    }

    @Test
    @DisplayName("이메일 중복체크 실패 - 유효성 검사 실패 - 이메일 블랭크")
    void is_duplicated_email_blank() throws Exception {
        ReflectionTestUtils.setField(memberEmailRequestDto, "email", "");

        when(memberService.checkDuplicatedEmail(memberEmailRequestDto.getEmail())).thenReturn(
                false);

        mockMvc.perform(post(uri + "/checkEmail")
                        .content(objectMapper.writeValueAsString(memberEmailRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이메일은 필수 입력 값 입니다.]"))
                .andDo(print())
                .andDo(document("member/member-duplicated-email-blank-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일")),
                        responseFields(
                                fieldWithPath("message").description("실패 사유")
                        )));
    }

    @Test
    @DisplayName("회원 정보 가져오기 성공")
    void get_memberInfo_success() throws Exception {
        Long memberId = 1L;

        when(memberService.getMemberInfo(memberId)).thenReturn(memberInfoResponseDto);

        mockMvc.perform(get("/api/auth/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization-Id", memberId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.memberId").value(memberInfoResponseDto.getMemberId()))
                .andExpect(jsonPath("$.memberName").value(memberInfoResponseDto.getMemberName()))
                .andExpect(jsonPath("$.email").value(memberInfoResponseDto.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(memberInfoResponseDto.getPhoneNumber()))
                .andExpect(jsonPath("$.birthday").value(memberInfoResponseDto.getBirthday()))
                .andExpect(jsonPath("$.memberPoint").value(memberInfoResponseDto.getMemberPoint()))
                .andExpect(jsonPath("$.roles[0]").value(memberInfoResponseDto.getRoles().get(0)))
                .andDo(document("member/member-memberInfo-success",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("memberId").description("멤버 아이디"),
                                fieldWithPath("memberName").description("멤버 이름"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호"),
                                fieldWithPath("birthday").description("생일"),
                                fieldWithPath("memberPoint").description("포인트"),
                                fieldWithPath("roles.[]").description("권한")
                        )));
    }

    @Test
    @DisplayName("소셜멤버 체크 성공")
    void check_oauthMember_success() throws Exception {
        ReflectionTestUtils.setField(memberIdNoRequestDto, "id", "thisisID");

        when(memberService.checkOAuthMember(memberIdNoRequestDto.getId())).thenReturn(true);

        mockMvc.perform(post("/api/oauth/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberIdNoRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(document("member/member-check-oauthMember-success",
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("아이디")
                        )));
    }

    @Test
    @DisplayName("소셜멤버 체크 실패")
    void check_oauthMember_fail() throws Exception {
        ReflectionTestUtils.setField(memberIdNoRequestDto, "id", "thisisID");

        when(memberService.checkOAuthMember(memberIdNoRequestDto.getId())).thenReturn(false);

        mockMvc.perform(post("/api/oauth/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberIdNoRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andDo(document("member/member-check-oauthMember-fail",
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("아이디")
                        )));
    }

    @Test
    @DisplayName("소셜멤버 체크 - 유효성 검사 실패")
    void check_oauthMember_fail_blank() throws Exception {
        ReflectionTestUtils.setField(memberIdNoRequestDto, "id", "");

        when(memberService.checkOAuthMember(memberIdNoRequestDto.getId())).thenReturn(false);

        mockMvc.perform(post("/api/oauth/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberIdNoRequestDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("[아이디는 필수 입력 값 입니다.]"))
                .andDo(document("member/member-check-oauthMember-pattern-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("아이디")),
                        responseFields(
                                fieldWithPath("message").description("실패 사유")
                        )));
    }

    @Test
    @DisplayName("소셜멤버 이메일 가져오기 성공")
    void get_oauthEmail_success() throws Exception {
        String email = "sa4777@naver.com";
        ReflectionTestUtils.setField(memberIdNoRequestDto, "id", "thisisID");

        when(memberService.getOAuthMemberEmail(memberIdNoRequestDto.getId())).thenReturn(email);

        mockMvc.perform(post("/api/oauth")
                        .content(objectMapper.writeValueAsString(memberIdNoRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(email))
                .andDo(document("member/member-getOauthEmail-success",
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("아이디")
                        )
                ));
    }

    @Test
    @DisplayName("소셜멤버 이메일 가져오기 - 유효성 검사 실패")
    void get_oauthEmail_fail() throws Exception {
        ReflectionTestUtils.setField(memberIdNoRequestDto, "id", "");

        mockMvc.perform(post("/api/oauth")
                        .content(objectMapper.writeValueAsString(memberIdNoRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[아이디는 필수 입력 값 입니다.]"))
                .andDo(document("member/member-getOauthEmail-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("아이디")),
                        responseFields(
                                fieldWithPath("message").description("실패 사유")
                        )
                ));

    }


}