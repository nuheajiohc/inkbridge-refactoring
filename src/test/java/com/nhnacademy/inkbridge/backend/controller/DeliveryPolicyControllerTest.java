package com.nhnacademy.inkbridge.backend.controller;


import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.DeliveryPolicyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: DeliveryPolicyControllerTest.
 *
 * @author jangjaehun
 * @version 2024/03/01
 */
@AutoConfigureRestDocs
@WebMvcTest(DeliveryPolicyController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class DeliveryPolicyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DeliveryPolicyService deliveryPolicyService;

    @Test
    @DisplayName("적용중인 배송비 정책 조회")
    void testGetCurrentDeliveryPolicy() throws Exception {
        DeliveryPolicyReadResponseDto responseDto = new DeliveryPolicyReadResponseDto(1L, 5000L, 50000L);

        given(deliveryPolicyService.getCurrentDeliveryPolicy()).willReturn(responseDto);

        mockMvc.perform(get("/api/delivery-policies/current"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.deliveryPolicyId").value(responseDto.getDeliveryPolicyId()))
            .andExpect(jsonPath("$.deliveryPrice").value(responseDto.getDeliveryPrice()))
            .andExpect(jsonPath("$.freeDeliveryPrice").value(responseDto.getFreeDeliveryPrice()))
            .andDo(document("deliverypolicy/delivery-policy-get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("deliveryPolicyId").description("배송비 정책 번호"),
                    fieldWithPath("deliveryPrice").description("배송비"),
                    fieldWithPath("freeDeliveryPrice").description("무료 배송 기준 금액")
                )));
    }
}
