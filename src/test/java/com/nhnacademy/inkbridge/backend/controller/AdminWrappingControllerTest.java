package com.nhnacademy.inkbridge.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.order.WrappingCreateRequestDto;
import com.nhnacademy.inkbridge.backend.service.WrappingService;
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
 * class: AdminWrappingControllerTest.
 *
 * @author JBum
 * @version 2024/03/26
 */
@AutoConfigureRestDocs
@WebMvcTest(AdminWrappingController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class AdminWrappingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WrappingService wrappingService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateWrapping() throws Exception {
        WrappingCreateRequestDto wrappingCreateRequestDto = new WrappingCreateRequestDto("일반 포장지",
            1000L, true);
        doNothing().when(wrappingService).createWrapping(any());

        mockMvc.perform(post("/api/admin/wrappings").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wrappingCreateRequestDto)))
            .andExpect(status().isCreated())
            .andDo(document("admin/wrappings/create", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    void testUpdateWrapping() throws Exception {
        WrappingCreateRequestDto wrappingCreateRequestDto = new WrappingCreateRequestDto("일반 포장지",
            1000L, true);
        int wrappingsId = 1;
        doNothing().when(wrappingService).updateWrapping(anyLong(), any());

        mockMvc.perform(
                put("/api/admin/wrappings/{wrappingId}", wrappingsId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(wrappingCreateRequestDto)))
            .andExpect(status().isOk())
            .andDo(document("admin/wrappings/update", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    void testCreateWrapping_fail() throws Exception {
        WrappingCreateRequestDto wrappingCreateRequestDto = new WrappingCreateRequestDto("일반 포장지",
            -1000L, true);
        doNothing().when(wrappingService).createWrapping(any());

        mockMvc.perform(post("/api/admin/wrappings").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wrappingCreateRequestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("admin/wrappings/create_fail", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    void testUpdateWrapping_fail() throws Exception {
        WrappingCreateRequestDto wrappingCreateRequestDto = new WrappingCreateRequestDto("일반 포장지",
            -1000L, true);
        int wrappingsId = 1;
        doNothing().when(wrappingService).updateWrapping(anyLong(), any());

        mockMvc.perform(
                put("/api/admin/wrappings/{wrappingId}", wrappingsId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(wrappingCreateRequestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("admin/wrappings/update_fail", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }
}