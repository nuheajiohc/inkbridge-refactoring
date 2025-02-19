package com.nhnacademy.inkbridge.backend.controller;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.nhnacademy.inkbridge.backend.dto.order.WrappingResponseDto;
import com.nhnacademy.inkbridge.backend.service.WrappingService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
 * class: WrappingControllerTest.
 *
 * @author JBum
 * @version 2024/03/26
 */

@AutoConfigureRestDocs
@WebMvcTest(WrappingController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class WrappingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WrappingService wrappingService;

    @Test
    void getWrappings() throws Exception {
        WrappingResponseDto wrappingResponseDto = new WrappingResponseDto(1L, "일반포장지", 100L, true);
        List<WrappingResponseDto> wrappingResponseDtoList = new ArrayList<>(
            Collections.singletonList(wrappingResponseDto));

        given(wrappingService.getWrappingList(anyBoolean())).willReturn(wrappingResponseDtoList);

        mockMvc.perform(get("/api/wrappings").param("is_active", "true"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].wrappingId").value(1L))
            .andExpect(jsonPath("$.[0].wrappingName").value("일반포장지"))
            .andExpect(jsonPath("$.[0].price").value(100L))
            .andExpect(jsonPath("$.[0].isActive").value(true))
            .andDo(document("wrappings", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].wrappingId").description("포장지 ID"),
                    fieldWithPath("[].wrappingName").description("포장지 이름"),
                    fieldWithPath("[].price").description("포장지 가격"),
                    fieldWithPath("[].isActive").description("포장지 상태")
                )));

    }

    @Test
    void getWrapping() throws Exception {
        WrappingResponseDto wrappingResponseDto = new WrappingResponseDto(1L, "일반포장지", 100L, true);
        Long wrappingId = 1L;
        given(wrappingService.getWrapping(anyLong())).willReturn(wrappingResponseDto);

        mockMvc.perform(get("/api/wrappings/{wrappingId)}", wrappingId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.wrappingId").value(1L))
            .andExpect(jsonPath("$.wrappingName").value("일반포장지"))
            .andExpect(jsonPath("$.price").value(100L))
            .andExpect(jsonPath("$.isActive").value(true))
            .andDo(document("wrappings/wrappingId", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("wrappingId").description("포장지 ID"),
                    fieldWithPath("wrappingName").description("포장지 이름"),
                    fieldWithPath("price").description("포장지 가격"),
                    fieldWithPath("isActive").description("포장지 상태")
                )));
    }
}