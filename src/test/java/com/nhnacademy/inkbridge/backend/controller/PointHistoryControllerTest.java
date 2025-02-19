package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.dto.member.PointHistoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.PointHistoryService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: PointHistoryControllerTest.
 *
 * @author jeongbyeonghun
 * @version 3/24/24
 */


@AutoConfigureRestDocs
@WebMvcTest(PointHistoryController.class)
class PointHistoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PointHistoryService pointHistoryService;

    private static List<PointHistoryReadResponseDto> testList;
    private static String reason;
    private static Long point;
    private static LocalDateTime accruedAt;

    @BeforeAll
    static void setUp() {
        testList = new ArrayList<>();
        reason = "test";
        point = 100L;
        accruedAt = LocalDateTime.now();
        testList.add(new PointHistoryReadResponseDto(reason, point, accruedAt));
    }

    @Test
    void getTestHistory() throws Exception {
        when(pointHistoryService.getPointHistory(anyLong())).thenReturn(testList);

        mvc.perform(get("/api/mypage/pointHistory")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization-Id", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].reason", equalTo(reason)))
            .andExpect(jsonPath("$[0].point", equalTo(point.intValue())))
            .andExpect(jsonPath("$[0].accruedAt",
                equalTo(accruedAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
            .andDo(document("point-history",
                preprocessRequest(prettyPrint()), // 요청 예쁘게 출력
                preprocessResponse(prettyPrint()), // 응답 예쁘게 출력
                requestHeaders(
                    headerWithName("Authorization-Id").description("사용자 식별자") // 요청 헤더 문서화
                ),
                responseFields(
                    fieldWithPath("[].reason").description("포인트 변경 이유"),
                    fieldWithPath("[].point").description("변경된 포인트 양"),
                    fieldWithPath("[].accruedAt").description("포인트가 적립된 시간")
                )));

    }
}