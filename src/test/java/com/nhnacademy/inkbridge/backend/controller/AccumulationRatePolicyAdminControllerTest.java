package com.nhnacademy.inkbridge.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.AccumulationRatePolicyService;
import java.time.LocalDate;
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
 * class: AccumulationRatePolicyAdminControllerTest.
 *
 * @author jangjaehun
 * @version 2024/03/05
 */
@AutoConfigureRestDocs
@WebMvcTest(AccumulationRatePolicyAdminController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class AccumulationRatePolicyAdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccumulationRatePolicyService accumulationRatePolicyService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("기본 적립율 전체 내역 조회 테스트")
    void testGetAccumulationRatePolicies() throws Exception {
        AccumulationRatePolicyAdminReadResponseDto responseDto =
            new AccumulationRatePolicyAdminReadResponseDto(1L, 1, LocalDate.of(2024, 1, 1));

        given(accumulationRatePolicyService.getAccumulationRatePolicies()).willReturn(
            List.of(responseDto));

        mockMvc.perform(get("/api/admin/accumulation-rate-policies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].accumulationRatePolicyId").value(1L))
            .andExpect(jsonPath("$[0].accumulationRate", equalTo(1)))
            .andExpect(jsonPath("$[0].createdAt", equalTo("2024-01-01")))
            .andDo(document("accumulationratepolicy/accumulation-rate-policy-admin-get",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].accumulationRatePolicyId").description("적립률 정책 번호"),
                    fieldWithPath("[].accumulationRate").description("적립률"),
                    fieldWithPath("[].createdAt").description("변경일자")
                )));

        verify(accumulationRatePolicyService, times(1)).getAccumulationRatePolicies();
    }

    @Test
    @DisplayName("적립율 정책 생성 - 유효성 검사 실패")
    void testCreateAccumulationRatePolicy_valid_failed() throws Exception {
        AccumulationRatePolicyCreateRequestDto requestDto = new AccumulationRatePolicyCreateRequestDto();
        requestDto.setAccumulationRate(-5);

        mockMvc.perform(post("/api/admin/accumulation-rate-policies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(ValidationException.class))
            .andDo(document("accumulationratepolicy/accumulation-rate-policy-admin-post-422",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("적립율 정책 생성 - 생성 성공")
    void testCreateAccumulationRatePolicy_success() throws Exception {
        AccumulationRatePolicyCreateRequestDto requestDto = new AccumulationRatePolicyCreateRequestDto();
        requestDto.setAccumulationRate(5);

        mockMvc.perform(post("/api/admin/accumulation-rate-policies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andDo(document("accumulationratepolicy/accumulation-rate-policy-admin-post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("accumulationRate").description("적립률")
                )));

        verify(accumulationRatePolicyService, times(1)).createAccumulationRatePolicy(any());
    }
}