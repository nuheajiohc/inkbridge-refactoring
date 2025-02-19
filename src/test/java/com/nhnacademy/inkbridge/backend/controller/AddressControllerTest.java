package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.address.AddressCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.address.AddressUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.address.MemberAddressReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.MemberAddressService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberAddressService memberAddressService;

    ObjectMapper objectMapper = new ObjectMapper();

    private static final Long USER_ID = 1L;
    private static final String AUTH_HEADER = "Authorization-Id";

    @Test
    @DisplayName("주소 목록 조회")
    void testGetAddresses() throws Exception {
        MemberAddressReadResponseDto dto = MemberAddressReadResponseDto.builder().addressId(1L)
            .zipCode("12345").address("testAddress").alias("test").addressDetail("home")
            .receiverName("testName").receiverNumber("01012340123").build();
        given(memberAddressService.getAddressByUserId(anyLong())).willReturn(List.of(dto));

        mockMvc.perform(get("/api/mypage/addresses")
                .header(AUTH_HEADER, USER_ID)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].addressId", equalTo(1)))
            .andExpect(jsonPath("$[0].zipCode", equalTo("12345")))
            .andExpect(jsonPath("$[0].address", equalTo("testAddress")))
            .andExpect(jsonPath("$[0].alias", equalTo("test")))
            .andExpect(jsonPath("$[0].addressDetail", equalTo("home")))
            .andExpect(jsonPath("$[0].receiverName", equalTo("testName")))
            .andExpect(jsonPath("$[0].receiverNumber", equalTo("01012340123")))
            .andDo(document("get-addresses",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(AUTH_HEADER).description("사용자 식별을 위한 헤더")
                ),
                responseFields(
                    fieldWithPath("[].addressId").description("주소 식별자"),
                    fieldWithPath("[].zipCode").description("우편번호"),
                    fieldWithPath("[].address").description("기본 주소"),
                    fieldWithPath("[].alias").description("주소 별칭"),
                    fieldWithPath("[].addressDetail").description("상세 주소"),
                    fieldWithPath("[].receiverName").description("수령인 이름"),
                    fieldWithPath("[].receiverNumber").description("수령인 연락처")
                )));
    }

    @Test
    @DisplayName("새 주소 생성")
    void testCreateAddress() throws Exception {
        AddressCreateRequestDto createRequestDto = AddressCreateRequestDto.builder()
            .zipCode("12345").address("Test Address").alias("Home")
            .addressDetail("Test Address Detail").receiverName("John Doe")
            .receiverNumber("01012345678").build();

        mockMvc.perform(post("/api/mypage/addresses")
                .header(AUTH_HEADER, USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequestDto)))
            .andExpect(status().isCreated())
            .andDo(document("create-address",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(AUTH_HEADER).description("사용자 식별을 위한 헤더")
                ),
                requestFields(
                    fieldWithPath("zipCode").description("우편번호"),
                    fieldWithPath("address").description("기본 주소"),
                    fieldWithPath("alias").description("주소 별칭"),
                    fieldWithPath("addressDetail").description("상세 주소"),
                    fieldWithPath("receiverName").description("수령인 이름"),
                    fieldWithPath("receiverNumber").description("수령인 연락처")
                )));

        verify(memberAddressService).createAddress(eq(USER_ID), any(AddressCreateRequestDto.class));
    }

    @Test
    @DisplayName("주소 정보 수정")
    void testModifyAddress() throws Exception {
        AddressUpdateRequestDto updateRequestDto = AddressUpdateRequestDto.builder()
            .addressId(1L).zipCode("54321").address("New Test Address")
            .alias("Office").addressDetail("New Test Address Detail")
            .receiverName("Jane Doe").receiverNumber("01087654321").build();

        mockMvc.perform(put("/api/mypage/addresses")
                .header(AUTH_HEADER, USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequestDto)))
            .andExpect(status().isOk())
            .andDo(document("modify-address",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(AUTH_HEADER).description("사용자 식별을 위한 헤더")
                ),
                requestFields(
                    fieldWithPath("addressId").description("수정할 주소의 식별자"),
                    fieldWithPath("zipCode").description("새 우편번호"),
                    fieldWithPath("address").description("새 기본 주소"),
                    fieldWithPath("alias").description("새 주소 별칭"),
                    fieldWithPath("addressDetail").description("새 상세 주소"),
                    fieldWithPath("receiverName").description("새 수령인 이름"),
                    fieldWithPath("receiverNumber").description("새 수령인 연락처")
                )));

        verify(memberAddressService).updateAddress(eq(USER_ID), any(AddressUpdateRequestDto.class));
    }

    @Test
    @DisplayName("주소 삭제")
    void testDeleteAddress() throws Exception {
        Long addressIdToDelete = 1L;

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/mypage/addresses/{addressId}",
                    addressIdToDelete)
                .header(AUTH_HEADER, USER_ID))
            .andExpect(status().isOk())
            .andDo(document("delete-address",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(AUTH_HEADER).description("사용자 식별을 위한 헤더")
                ),
                pathParameters(
                    parameterWithName("addressId").description("삭제할 주소의 식별자")
                )));

        verify(memberAddressService).deleteAddress(USER_ID, addressIdToDelete);
    }
}
