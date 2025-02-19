package com.nhnacademy.inkbridge.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.PointPolicyService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: PointPolicyControllerTest.
 *
 * @author jangjaehun
 * @version 2024/02/16
 */
@AutoConfigureRestDocs
@WebMvcTest(PointPolicyAdminController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class PointPolicyAdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointPolicyService pointPolicyService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("포인트 정책 전체 조회 테스트")
    void testGetPointPolicies() throws Exception {
        PointPolicyAdminReadResponseDto responseDto = new PointPolicyAdminReadResponseDto(1L,
            "REGISTER",
            1000L, LocalDate.of(2024, 1, 1));

        List<PointPolicyAdminReadResponseDto> list = new ArrayList<>();
        list.add(responseDto);

        given(pointPolicyService.getPointPolicies()).willReturn(list);

        mockMvc.perform(get("/api/admin/point-policies"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].pointPolicyId", equalTo(1)))
            .andExpect(jsonPath("$[0].policyType", equalTo("REGISTER")))
            .andExpect(jsonPath("$[0].accumulatePoint", equalTo(1000)))
            .andExpect(jsonPath("$[0].createdAt", equalTo("2024-01-01")))
            .andDo(document("pointpolicy/point-policy-admin-get",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].pointPolicyId").description("포인트 정책 번호"),
                    fieldWithPath("[].policyType").description("포인트 정책 유형 이름"),
                    fieldWithPath("[].accumulatePoint").description("적립 포인트"),
                    fieldWithPath("[].createdAt").description("변경일자")
                )));
    }

    @Test
    @DisplayName("포인트 정책 유형 전체 조회 - 존재하지 않는 포인트 정책 유형")
    void testGetPointPoliciesByTypeId_not_found() throws Exception {
        NotFoundException notFoundException = new NotFoundException(PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage());

        given(pointPolicyService.getPointPoliciesByTypeId(1)).willThrow(notFoundException);

        mockMvc.perform(get("/api/admin/point-policies/{pointPolicyTypeId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class))
            .andDo(document("pointpolicy/point-policy-admin-get-path-404",
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("포인트 정책 유형 전체 조회 - 조회 성공")
    void testGetPointPoliciesByTypeId_success() throws Exception {
        PointPolicyAdminReadResponseDto responseDto = new PointPolicyAdminReadResponseDto(1L,
            "REGISTER",
            1000L, LocalDate.of(2024, 1, 1));

        given(pointPolicyService.getPointPoliciesByTypeId(1)).willReturn(List.of(responseDto));

        mockMvc.perform(get("/api/admin/point-policies/{pointPolicyTypeId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].pointPolicyId").value(1L))
            .andExpect(jsonPath("$[0].accumulatePoint").value(1000L))
            .andExpect(jsonPath("$[0].createdAt", equalTo("2024-01-01")))
            .andExpect(jsonPath("$[0].policyType", equalTo("REGISTER")))
            .andDo(document("pointpolicy/point-policy-admin-get-path",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].pointPolicyId").description("포인트 정책 번호"),
                    fieldWithPath("[].policyType").description("포인트 정책 유형 이름"),
                    fieldWithPath("[].accumulatePoint").description("적립 포인트"),
                    fieldWithPath("[].createdAt").description("변경일자")
                )));
    }

    @Test
    @DisplayName("포인트 정책 현재 적용중인 정책 목록 조회")
    void testGetCurrentPointPolicies() throws Exception {
        PointPolicyAdminReadResponseDto responseDto = new PointPolicyAdminReadResponseDto(1L,
            "REGISTER",
            1000L, LocalDate.of(2024, 1, 1));

        given(pointPolicyService.getCurrentPointPolicies()).willReturn(List.of(responseDto));

        mockMvc.perform(get("/api/admin/point-policies/current")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].pointPolicyId").value(1L))
            .andExpect(jsonPath("$[0].accumulatePoint").value(1000L))
            .andExpect(jsonPath("$[0].createdAt", equalTo("2024-01-01")))
            .andExpect(jsonPath("$[0].policyType", equalTo("REGISTER")))
            .andDo(document("pointpolicy/point-policy-admin-get-current",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].pointPolicyId").description("포인트 정책 번호"),
                    fieldWithPath("[].policyType").description("포인트 정책 유형 이름"),
                    fieldWithPath("[].accumulatePoint").description("적립 포인트"),
                    fieldWithPath("[].createdAt").description("변경일자")
                )));
    }

    @Test
    @DisplayName("포인트 정책 생성 - 유효성 검사 실패 테스트")
    void testCreatePointPolicy_validation_failed() throws Exception {
        PointPolicyCreateRequestDto requestDto = new PointPolicyCreateRequestDto();
        requestDto.setAccumulatePoint(-100L);
        requestDto.setPointPolicyTypeId(1);

        mockMvc.perform(post("/api/admin/point-policies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(ValidationException.class))
            .andDo(document("pointpolicy/point-policy-post-422",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("포인트 정책 생성 - 포인트 정책 유형이 존재하지 않는 경우 테스트")
    void testCreatePointPolicy_not_found_policy_type() throws Exception {
        PointPolicyCreateRequestDto requestDto = new PointPolicyCreateRequestDto();
        requestDto.setAccumulatePoint(100L);
        requestDto.setPointPolicyTypeId(1);

        doThrow(new NotFoundException(PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage()))
            .when(pointPolicyService).createPointPolicy(any());

        mockMvc.perform(post("/api/admin/point-policies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class))
            .andDo(document("pointpolicy/point-policy-post-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }


    @Test
    @DisplayName("포인트 정책 생성 - 성공")
    void testCreatePointPolicy_success() throws Exception {
        PointPolicyCreateRequestDto requestDto = new PointPolicyCreateRequestDto();
        requestDto.setAccumulatePoint(100L);
        requestDto.setPointPolicyTypeId(1);

        mockMvc.perform(post("/api/admin/point-policies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andDo(document("pointpolicy/point-policy-post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("accumulatePoint").description("적립 포인트"),
                    fieldWithPath("pointPolicyTypeId").description("포인트 정책 유형 번호")
                )));
    }


}