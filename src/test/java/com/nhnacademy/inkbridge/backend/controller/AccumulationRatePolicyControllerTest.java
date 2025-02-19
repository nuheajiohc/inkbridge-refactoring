package com.nhnacademy.inkbridge.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.AccumulationRatePolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.service.AccumulationRatePolicyService;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: AccumulationRatePolicyControllerTest.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
@AutoConfigureRestDocs
@WebMvcTest(AccumulationRatePolicyController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class AccumulationRatePolicyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccumulationRatePolicyService accumulationRatePolicyService;

    @Test
    @DisplayName("적립율 정책 id로 내역 조회 - 존재하지 않는 적립율 정책")
    void testGetAccumulationRatePolicy_not_found() throws Exception {
        NotFoundException notFoundException = new NotFoundException(
            AccumulationRatePolicyMessageEnum.ACCUMULATION_RATE_POLICY_NOT_FOUND.getMessage());
        given(accumulationRatePolicyService.getAccumulationRatePolicy(1L)).willThrow(
            notFoundException);

        mockMvc.perform(get("/api/accumulation-rate-policies/{accumulationRatePolicy}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class))
            .andDo(document("accumulationratepolicy/accumulation-rate-policy-get-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));

        verify(accumulationRatePolicyService, times(1)).getAccumulationRatePolicy(1L);
    }

    @Test
    @DisplayName("적립율 정책 id로 내역 조회 - 조회 성공")
    void testGetAccumulationRatePolicy_success() throws Exception {
        AccumulationRatePolicyReadResponseDto responseDto =
            new AccumulationRatePolicyReadResponseDto(1L, 1);

        given(accumulationRatePolicyService.getAccumulationRatePolicy(1L)).willReturn(responseDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/accumulation-rate-policies/{accumulationRatePolicyId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accumulationRatePolicyId").value(1L))
            .andExpect(jsonPath("$.accumulationRate", equalTo(1)))
            .andDo(document("accumulationratepolicy/accumulation-rate-policy-get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("accumulationRatePolicyId").description("적립률 정책 번호")
                ),
                responseFields(
                    fieldWithPath("accumulationRatePolicyId").description("적립률 정책 번호"),
                    fieldWithPath("accumulationRate").description("적립률")
                )));

        verify(accumulationRatePolicyService, times(1)).getAccumulationRatePolicy(1L);
    }

    @Test
    @DisplayName("현재 적용중인 적립율 정책 조회")
    void testGetCurrentAccumulationRatePolicy() throws Exception {
        AccumulationRatePolicyReadResponseDto responseDto =
            new AccumulationRatePolicyReadResponseDto(1L, 1);

        given(accumulationRatePolicyService.getCurrentAccumulationRatePolicy()).willReturn(
            responseDto);

        mockMvc.perform(get("/api/accumulation-rate-policies/current", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accumulationRatePolicyId").value(1L))
            .andExpect(jsonPath("$.accumulationRate", equalTo(1)))
            .andDo(document("accumulationratepolicy/accumulation-rate-policy-current",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("accumulationRatePolicyId").description("적립률 정책 번호"),
                    fieldWithPath("accumulationRate").description("적립률")
                )));

        verify(accumulationRatePolicyService, times(1)).getCurrentAccumulationRatePolicy();
    }
}