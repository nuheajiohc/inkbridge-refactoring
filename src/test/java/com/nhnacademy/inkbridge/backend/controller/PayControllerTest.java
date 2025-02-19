package com.nhnacademy.inkbridge.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.inkbridge.backend.dto.pay.PayCreateRequestDto;
import com.nhnacademy.inkbridge.backend.facade.PayFacade;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * class: PayControllerTest.
 *
 * @author jangjaehun
 * @version 2024/03/21
 */
@WebMvcTest(PayController.class)
@AutoConfigureRestDocs
class PayControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PayFacade payFacade;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("결제 - 유효성 검사 실패")
    void testDoPay_valid_failed() throws Exception {
        PayCreateRequestDto request = new PayCreateRequestDto();

        mockMvc.perform(post("/api/pays")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpectAll(
                status().isBadRequest(),
                exception -> assertThat(exception.getResolvedException())
                    .isInstanceOf(MethodArgumentNotValidException.class))
            .andDo(document("pay/post-422",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("결제 - 성공")
    void testDoPay_success() throws Exception {
        String orderCode = UUID.randomUUID().toString().replace("-", "");
        PayCreateRequestDto request = new PayCreateRequestDto();
        ReflectionTestUtils.setField(request, "payKey", "payKey");
        ReflectionTestUtils.setField(request, "orderCode", orderCode);
        ReflectionTestUtils.setField(request, "totalAmount", 10000L);
        ReflectionTestUtils.setField(request, "balanceAmount", 10000L);
        ReflectionTestUtils.setField(request, "approvedAt", LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        ReflectionTestUtils.setField(request, "requestedAt", LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        ReflectionTestUtils.setField(request, "vat", 1000L);
        ReflectionTestUtils.setField(request, "isPartialCancelable", true);
        ReflectionTestUtils.setField(request, "method", "간편결제");
        ReflectionTestUtils.setField(request, "status", "DONE");
        ReflectionTestUtils.setField(request, "provider", "toss");

        mockMvc.perform(post("/api/pays")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(document("pay/post-201",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("payKey").description("결제 키"),
                    fieldWithPath("orderCode").description("주문 코드"),
                    fieldWithPath("totalAmount").description("결제 금액"),
                    fieldWithPath("balanceAmount").description("환불 가능 금액"),
                    fieldWithPath("approvedAt").description("결제 승인 시간"),
                    fieldWithPath("requestedAt").description("결제 요청 시간"),
                    fieldWithPath("vat").description("부가세"),
                    fieldWithPath("isPartialCancelable").description("부분 환불 여부"),
                    fieldWithPath("method").description("결제 방법"),
                    fieldWithPath("status").description("결제 상태"),
                    fieldWithPath("provider").description("결제 회사")
                )));

        verify(payFacade, times(1)).doPay(any());
    }
}