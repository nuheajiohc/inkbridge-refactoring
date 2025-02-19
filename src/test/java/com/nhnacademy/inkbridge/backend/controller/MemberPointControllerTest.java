package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.controller.header.HeaderConstants;
import com.nhnacademy.inkbridge.backend.service.MemberPointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: MemberPointControllerTest.
 *
 * @author jeongbyeonghun
 * @version 3/19/24
 */

@AutoConfigureRestDocs
@WebMvcTest(MemberPointController.class)
class MemberPointControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    MemberPointService memberPointService;

    @Test
    void getPoint() throws Exception {

        when(memberPointService.getMemberPoint(anyLong())).thenReturn(100L);

        mvc.perform(get("/api/mypage/points")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HeaderConstants.MEMBER_ID_HEADER, 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("point", equalTo(100)))
            .andDo(document("docs"));
    }
}