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
import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.DeliveryPolicyService;
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
 * class: DeliveryPolicyControllerTest.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
@AutoConfigureRestDocs
@WebMvcTest(DeliveryPolicyAdminController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class DeliveryPolicyAdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DeliveryPolicyService deliveryPolicyService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("배송비 정책 전체 조회 테스트")
    void testGetDeliveryPolicies() throws Exception {
        DeliveryPolicyAdminReadResponseDto responseDto = new DeliveryPolicyAdminReadResponseDto(1L,
            1000L,
            LocalDate.of(2024, 1, 1), 50000L);

        given(deliveryPolicyService.getDeliveryPolicies()).willReturn(List.of(responseDto));

        mockMvc.perform(get("/api/admin/delivery-policies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].deliveryPolicyId").value(1L))
            .andExpect(jsonPath("$[0].deliveryPrice").value(1000L))
            .andExpect(jsonPath("$[0].freeDeliveryPrice").value(50000L))
            .andExpect(jsonPath("$[0].createdAt", equalTo("2024-01-01")))
            .andDo(document("deliverypolicy/delivery-policy-admin-get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].deliveryPolicyId").description("배송비 정책 번호"),
                    fieldWithPath("[].deliveryPrice").description("배송비"),
                    fieldWithPath("[].freeDeliveryPrice").description("무료 배송 기준 금액"),
                    fieldWithPath("[].createdAt").description("변경일자")
                )));

        verify(deliveryPolicyService, times(1)).getDeliveryPolicies();
    }

    @Test
    @DisplayName("배송비 정책 생성 - 유효성 검사 실패")
    void testCreateDeliveryPolicy_valid_failed() throws Exception {
        DeliveryPolicyCreateRequestDto requestDto = new DeliveryPolicyCreateRequestDto();
        requestDto.setDeliveryPrice(-1000L);
        requestDto.setFreeDeliveryPrice(50000L);

        mockMvc.perform(post("/api/admin/delivery-policies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(ValidationException.class))
            .andDo(document("deliverypolicy/delivery-policy-admin-post-422",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("deliveryPrice").description("배송비"),
                    fieldWithPath("freeDeliveryPrice").description("무료 배송 기준 금액")
                )));
    }

    @Test
    @DisplayName("배송비 정책 생성 - 성공")
    void testCreateDeliveryPolicy_success() throws Exception {
        DeliveryPolicyCreateRequestDto requestDto = new DeliveryPolicyCreateRequestDto();
        requestDto.setDeliveryPrice(1000L);
        requestDto.setFreeDeliveryPrice(50000L);

        mockMvc.perform(post("/api/admin/delivery-policies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andDo(document("deliverypolicy/delivery-policy-admin-post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("deliveryPrice").description("배송비"),
                    fieldWithPath("freeDeliveryPrice").description("무료 배송 기준 금액")
                )));

        verify(deliveryPolicyService, times(1)).createDeliveryPolicy(any());
    }
}