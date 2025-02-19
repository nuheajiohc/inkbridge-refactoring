package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.membergrade.MemberGradeReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.membergrade.MemberGradeUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.MemberGrade;
import com.nhnacademy.inkbridge.backend.enums.MemberGradeMessageEnum;
import com.nhnacademy.inkbridge.backend.service.MemberGradeService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: MemberGradeControllerTest.
 *
 * @author jeongbyeonghun
 * @version 3/6/24
 */
@AutoConfigureRestDocs
@WebMvcTest(MemberGradeController.class)
class MemberGradeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    MemberGradeService memberGradeService;

    ObjectMapper objectMapper = new ObjectMapper();

    private static MemberGrade memberGrade;
    private static Integer gradeId;
    private static String grade;
    private static BigDecimal pointRate;
    private static Long standardAmount;

    @BeforeAll
    static void setTest() {
        gradeId = 1;
        grade = "TEST";
        pointRate = BigDecimal.valueOf(1.5);
        standardAmount = 10000L;
        memberGrade = MemberGrade.create().grade(grade).gradeId(gradeId)
            .standardAmount(standardAmount)
            .pointRate(pointRate).build();
    }

    @Test
    void getGradeList() throws Exception {
        MemberGradeReadResponseDto testGrade = MemberGradeReadResponseDto.builder()
            .memberGrade(memberGrade).build();
        List<MemberGradeReadResponseDto> testList = new ArrayList<>();
        testList.add(testGrade);
        when(memberGradeService.getGradeList()).thenReturn(testList);
        mvc.perform(get("/api/admin/member/grade")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].gradeId", equalTo(gradeId)))
            .andExpect(jsonPath("$[0].grade", equalTo(grade)))
            .andExpect(jsonPath("$[0].standardAmount", equalTo(standardAmount.intValue())))
            .andExpect(jsonPath("$[0].pointRate", closeTo(pointRate.doubleValue(), 0.001)))
            .andDo(document("grade-list",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].gradeId").description("등급 ID"),
                    fieldWithPath("[].grade").description("등급 명칭"),
                    fieldWithPath("[].standardAmount").description("기준 금액"),
                    fieldWithPath("[].pointRate").description("포인트 비율")
                )));
    }

    @Test
    void updateGrade() throws Exception {
        MemberGradeUpdateRequestDto memberGradeUpdateRequestDto = new MemberGradeUpdateRequestDto();
        memberGradeUpdateRequestDto.setPointRate(BigDecimal.valueOf(2.0)); // 포인트 비율 업데이트
        memberGradeUpdateRequestDto.setStandardAmount(20000L); // 기준 금액 업데이트

        doNothing().when(memberGradeService)
            .updateGrade(eq(gradeId), any(MemberGradeUpdateRequestDto.class));

        mvc.perform(
                RestDocumentationRequestBuilders.put("/api/admin/member/grade/{gradeId}", gradeId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(memberGradeUpdateRequestDto)))
            .andExpect(status().isOk())
            .andDo(document("member-grade-update",
                preprocessRequest(prettyPrint()), // 요청 예쁘게 출력
                preprocessResponse(prettyPrint()), // 응답 예쁘게 출력
                pathParameters(
                    parameterWithName("gradeId").description("업데이트할 회원 등급의 ID") // 경로 변수 문서화
                ),
                requestFields(
                    fieldWithPath("pointRate").description("업데이트할 포인트 비율"),
                    fieldWithPath("standardAmount").description("업데이트할 기준 금액") // 요청 본문 필드 문서화
                )));
    }

    @Test
    void updateGradeWhenGradeNouFound() throws Exception {
        MemberGradeUpdateRequestDto memberGradeUpdateRequestDto = new MemberGradeUpdateRequestDto();
        doThrow(new com.nhnacademy.inkbridge.backend.exception.NotFoundException(
            MemberGradeMessageEnum.MEMBER_GRADE_NOT_FOUND_ERROR.getMessage())).when(
            memberGradeService).updateGrade(eq(gradeId), any(MemberGradeUpdateRequestDto.class));

        mvc.perform(put("/api/admin/member/grade/{gradeId}", gradeId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberGradeUpdateRequestDto)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("message",
                equalTo(MemberGradeMessageEnum.MEMBER_GRADE_NOT_FOUND_ERROR.getMessage())));
    }

}