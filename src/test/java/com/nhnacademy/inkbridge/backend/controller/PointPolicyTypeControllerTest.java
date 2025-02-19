package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.PointPolicyTypeService;
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
 * class: PointPolicyTypeControllerTest.
 *
 * @author jangjaehun
 * @version 2024/02/16
 */
@AutoConfigureRestDocs
@WebMvcTest(PointPolicyTypeController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class PointPolicyTypeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointPolicyTypeService pointPolicyTypeService;

    @Test
    @DisplayName("포인트 정책 유형 전체 조회")
    void testGetPointPolicyTypes() throws Exception {
        PointPolicyTypeReadResponseDto responseDto = new PointPolicyTypeReadResponseDto(1,
            "REGISTER");

        List<PointPolicyTypeReadResponseDto> list = List.of(responseDto);

        given(pointPolicyTypeService.getPointPolicyTypes()).willReturn(list);

        mockMvc.perform(get("/api/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].pointPolicyTypeId", equalTo(1)))
            .andExpect(jsonPath("$[0].policyType", equalTo("REGISTER")))
            .andDo(document("pointpolicytype/point-policy-type-get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].pointPolicyTypeId").description("포인트 정책 유형 번호"),
                    fieldWithPath("[].policyType").description("포인트 정책 유형 이름")
                )));
    }
}