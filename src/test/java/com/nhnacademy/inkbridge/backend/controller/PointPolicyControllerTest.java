package com.nhnacademy.inkbridge.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.service.PointPolicyService;
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
 * @version 2024/03/05
 */
@AutoConfigureRestDocs
@WebMvcTest(PointPolicyController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class PointPolicyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointPolicyService pointPolicyService;

    @Test
    @DisplayName("포인트 정책 유형의 현재 적용중인 정책 조회 - 존재하지 않는 포인트 정책 유형")
    void testGetCurrentPointPolicy_not_found() throws Exception {
        NotFoundException notFoundException = new NotFoundException(
            PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage());
        given(pointPolicyService.getCurrentPointPolicy(1)).willThrow(notFoundException);

        mockMvc.perform(get("/api/point-policies/current/{pointPolicyTypeId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class))
            .andDo(document("pointpolicy/point-policy-get-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("포인트 정책 유형의 현재 적용중인 정책 조회 - 조회 성공")
    void testGetCurrentPointPolicy_success() throws Exception {
        PointPolicyReadResponseDto responseDto = new PointPolicyReadResponseDto(1L, "REGISTER",
            1000L);

        given(pointPolicyService.getCurrentPointPolicy(1)).willReturn(responseDto);

        mockMvc.perform(get("/api/point-policies/current/{pointPolicyTypeId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pointPolicyId").value(1L))
            .andExpect(jsonPath("$.policyType", equalTo("REGISTER")))
            .andExpect(jsonPath("$.accumulatePoint").value(1000L))
            .andDo(document("pointpolicy/point-policy-get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("pointPolicyId").description("포인트 정책 번호"),
                    fieldWithPath("policyType").description("포인트 정책 유형 이름"),
                    fieldWithPath("accumulatePoint").description("적립 포인트")
                )));
    }
}