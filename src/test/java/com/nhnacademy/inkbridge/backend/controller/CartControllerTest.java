package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.cart.CartCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.cart.CartReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.CartService;
import java.util.List;
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
 * class: CartControllerTest.
 *
 * @author minm063
 * @version 2024/03/22
 */
@AutoConfigureRestDocs
@WebMvcTest(CartController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class CartControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CartService cartService;


    @Test
    void getCart() throws Exception {
        CartReadResponseDto cartReadResponseDto = CartReadResponseDto.builder().bookId(1L).amount(1)
            .build();
        when(cartService.getCart(anyLong())).thenReturn(List.of(cartReadResponseDto));

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/carts/{memberId}", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].amount", equalTo(1)))
            .andExpect(jsonPath("$.[0].bookId", equalTo(1)))
            .andDo(document("cart/cart-get",
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("memberId").description("회원 번호")),
                responseFields(
                    fieldWithPath("[].amount").description("수량"),
                    fieldWithPath("[].bookId").description("도서 번호"))
            ));
    }

    @Test
    void createCart() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        doNothing().when(cartService).createCart(anyList());

        List<CartCreateRequestDto> cartCreateRequestDto = List.of(
            CartCreateRequestDto.builder().bookId(1L).memberId(1L).amount(1).build());

        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/carts")
                .content(objectMapper.writeValueAsString(cartCreateRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(document("cart/cart-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("[].bookId").description("도서 번호"),
                    fieldWithPath("[].memberId").description("회원 번호"),
                    fieldWithPath("[].amount").description("수량")
                )
            ));
    }
}