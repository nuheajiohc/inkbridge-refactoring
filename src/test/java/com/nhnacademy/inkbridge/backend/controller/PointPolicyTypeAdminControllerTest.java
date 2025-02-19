package com.nhnacademy.inkbridge.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.PointPolicyTypeService;
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
 * class: PointPolicyTypeAdminControllerTest.
 *
 * @author jangjaehun
 * @version 2024/03/05
 */
@AutoConfigureRestDocs
@WebMvcTest(PointPolicyTypeAdminController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class PointPolicyTypeAdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointPolicyTypeService pointPolicyTypeService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("포인트 정책 유형 생성 - 유효성 검사 실패")
    void testCreatePointPolicyType_validation_failed() throws Exception {
        PointPolicyTypeCreateRequestDto requestDto = new PointPolicyTypeCreateRequestDto();
        requestDto.setPolicyType("");

        mockMvc.perform(post("/api/admin/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(ValidationException.class))
            .andDo(document("pointpolicytype/point-policy-type-post-422",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("포인트 정책 유형 생성 - 존재하는 포인트 정책 유형")
    void testCreatePointPolicyType_duplicate() throws Exception {
        PointPolicyTypeCreateRequestDto requestDto = new PointPolicyTypeCreateRequestDto();
        requestDto.setPolicyType("REGISTER");
        requestDto.setAccumulatePoint(1000L);

        doThrow(new AlreadyExistException(
            PointPolicyMessageEnum.POINT_POLICY_TYPE_ALREADY_EXIST.name()))
            .when(pointPolicyTypeService).createPointPolicyType(any());

        mockMvc.perform(post("/api/admin/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isConflict())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(AlreadyExistException.class))
            .andDo(document("pointpolicytype/point-policy-type-post-409",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("포인트 정책 유형 생성 - 성공")
    void testCreatePointPolicyType_success() throws Exception {
        PointPolicyTypeCreateRequestDto requestDto = new PointPolicyTypeCreateRequestDto();
        requestDto.setPolicyType("REGISTER");
        requestDto.setAccumulatePoint(1000L);

        mockMvc.perform(post("/api/admin/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andDo(document("pointpolicytype/point-policy-type-post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("policyType").description("포인트 정책 유형 이름"),
                    fieldWithPath("accumulatePoint").description("정책 적립 포인트")
                )));
    }

    @Test
    @DisplayName("포인트 정책 유형 수정 - 유효성 검사 실패")
    void testUpdatePointPolicyType_validation_failed() throws Exception {
        PointPolicyTypeUpdateRequestDto requestDto = new PointPolicyTypeUpdateRequestDto();
        requestDto.setPointPolicyTypeId(1);
        requestDto.setPolicyType("");

        mockMvc.perform(put("/api/admin/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(ValidationException.class))
            .andDo(document("pointpolicytype/point-policy-type-put-422",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("포인트 정책 유형 수정 - 존재하지 않는 포인트 정책 유형")
    void testUpdatePointPolicyType_not_found() throws Exception {
        PointPolicyTypeUpdateRequestDto requestDto = new PointPolicyTypeUpdateRequestDto();
        requestDto.setPointPolicyTypeId(1);
        requestDto.setPolicyType("REVIEW");

        doThrow(new NotFoundException(PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.name()))
            .when(pointPolicyTypeService).updatePointPolicyType(any());

        mockMvc.perform(put("/api/admin/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class))
            .andDo(document("pointpolicytype/point-policy-type-put-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("포인트 정책 유형 수정 - 성공")
    void testUpdatePointPolicyType_success() throws Exception {
        PointPolicyTypeUpdateRequestDto requestDto = new PointPolicyTypeUpdateRequestDto();
        requestDto.setPointPolicyTypeId(1);
        requestDto.setPolicyType("REVIEW");

        mockMvc.perform(put("/api/admin/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk())
            .andDo(document("pointpolicytype/point-policy-type-put",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("pointPolicyTypeId").description("포인트 정책 유형 번호"),
                    fieldWithPath("policyType").description("포인트 정책 유형 이름")
                )));
    }

    @Test
    @DisplayName("포인트 정책 유형 삭제 - 존재하지 않는 포인트 정책 유형")
    void testDeletePointPolicyType_not_found() throws Exception {
        doThrow(new NotFoundException(PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.name()))
            .when(pointPolicyTypeService).deletePointPolicyTypeById(anyInt());

        mockMvc.perform(delete("/api/admin/point-policy-types/{pointPolicyTypeId}", 1))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class))
            .andDo(document("pointpolicytype/point-policy-type-delete-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("포인트 정책 유형 삭제 - 성공")
    void testDeletePointPolicyType_success() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/admin/point-policy-types/{pointPolicyTypeId}", 1))
            .andExpect(status().isOk())
            .andDo(document("pointpolicytype/point-policy-type-delete",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("pointPolicyTypeId").description("포인트 정책 유형 번호")
                )));

    }
}