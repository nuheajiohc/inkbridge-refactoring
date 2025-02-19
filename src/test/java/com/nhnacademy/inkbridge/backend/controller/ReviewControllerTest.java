package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewBookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewDetailByMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewDetailOnBookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.facade.ReviewFacade;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

/**
 *  class: ReviewControllerTest.
 *
 *  @author minm063
 *  @version 2024/03/22
 */
@AutoConfigureRestDocs
@WebMvcTest(ReviewController.class)
@ExtendWith(RestDocumentationExtension.class)
class ReviewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReviewFacade reviewFacade;

    Map<Long, List<String>> reviewFiles;

    @BeforeEach
    void setup() {
        reviewFiles = new HashMap<>();
        reviewFiles.put(1L, Collections.emptyList());
    }

    @Test
    @DisplayName("회원 번호로 리뷰 조회")
    void getReviewsByMember() throws Exception {
        ReviewDetailByMemberReadResponseDto dto = ReviewDetailByMemberReadResponseDto.builder()
            .reviewId(1L).reviewTitle("reviewTitle").reviewContent("reviewContent")
            .registeredAt(LocalDateTime.now()).score(5).bookId(123L).bookTitle("bookTitle")
            .thumbnail("thumbnail").build();
        Page<ReviewDetailByMemberReadResponseDto> reviewDto = new PageImpl<>(List.of(dto));

        when(reviewFacade.getReviewsByMember(any(), anyLong())).thenReturn(
            ReviewMemberReadResponseDto.builder().reviewDetailReadResponseDtos(reviewDto).count(1L)
                .reviewFiles(reviewFiles).build());

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/auth/reviews")
                .param("memberId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.reviewDetailReadResponseDtos.content.[0].reviewId", equalTo(1)))
            .andExpect(jsonPath("$.reviewDetailReadResponseDtos.content.[0].reviewTitle",
                equalTo("reviewTitle")))
            .andExpect(jsonPath("$.reviewDetailReadResponseDtos.content.[0].reviewContent",
                equalTo("reviewContent")))
            .andExpect(jsonPath("$.reviewDetailReadResponseDtos.content.[0].score", equalTo(5)))
            .andExpect(jsonPath("$.reviewDetailReadResponseDtos.content.[0].bookId", equalTo(123)))
            .andExpect(jsonPath("$.reviewDetailReadResponseDtos.content.[0].bookTitle",
                equalTo("bookTitle")))
            .andExpect(jsonPath("$.reviewDetailReadResponseDtos.content.[0].thumbnail",
                equalTo("thumbnail")))
            .andExpect(jsonPath("$.reviewFiles.size()", equalTo(1)))
            .andExpect(jsonPath("$.count", equalTo(1)))
            .andDo(document("review/review-byMember",
                preprocessResponse(prettyPrint()),
                requestParameters(parameterWithName("memberId").description("회원 번호")),
                relaxedResponseFields(
                    fieldWithPath("reviewDetailReadResponseDtos.content[].reviewId").description(
                        "리뷰 번호"),
                    fieldWithPath("reviewDetailReadResponseDtos.content[].reviewTitle").description(
                        "리뷰 제목"),
                    fieldWithPath(
                        "reviewDetailReadResponseDtos.content[].reviewContent").description(
                        "리뷰 내용"),
                    fieldWithPath(
                        "reviewDetailReadResponseDtos.content[].registeredAt").description(
                        "리뷰 등록일자"),
                    fieldWithPath("reviewDetailReadResponseDtos.content[].score").description(
                        "리뷰 평점"),
                    fieldWithPath("reviewDetailReadResponseDtos.content[].bookId").description(
                        "도서 번호"),
                    fieldWithPath("reviewDetailReadResponseDtos.content[].bookTitle").description(
                        "도서 제목"),
                    fieldWithPath("reviewDetailReadResponseDtos.content[].thumbnail").description(
                        "도서 썸네일"),
                    subsectionWithPath("reviewFiles").description("리뷰 사진"),
                    fieldWithPath("count").description("리뷰 총 개수"),
                    fieldWithPath("reviewDetailReadResponseDtos.totalPages").description("총 페이지"),
                    fieldWithPath("reviewDetailReadResponseDtos.totalElements").description("총 개수"),
                    fieldWithPath("reviewDetailReadResponseDtos.size").description("화면에 출력할 개수"),
                    fieldWithPath("reviewDetailReadResponseDtos.number").description("현재 페이지"),
                    fieldWithPath("reviewDetailReadResponseDtos.numberOfElements").description(
                        "현재 페이지 개수"))));
    }

    @Test
    @DisplayName("도서 번호로 리뷰 조회")
    void getReviewsByBookId() throws Exception {
        ReviewDetailOnBookReadResponseDto dto = ReviewDetailOnBookReadResponseDto.builder()
            .reviewId(1L).reviewerEmail("email")
            .reviewTitle("reviewTitle").reviewContent("reviewContent")
            .registeredAt(LocalDateTime.now()).score(1).build();
        PageImpl<ReviewDetailOnBookReadResponseDto> page = new PageImpl<>(
            List.of(dto));

        when(reviewFacade.getReviewsByBookId(any(), anyLong())).thenReturn(
            ReviewBookReadResponseDto.builder().reviewDetailReadResponseDtos(page)
                .reviewFiles(reviewFiles).build());

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/reviews/books/{bookId}", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.reviewDetailReadResponseDtos.content[0].reviewId", equalTo(1)))
            .andExpect(jsonPath("$.reviewDetailReadResponseDtos.content[0].reviewerEmail",
                equalTo("email")))
            .andExpect(jsonPath("$.reviewDetailReadResponseDtos.content[0].reviewTitle",
                equalTo("reviewTitle")))
            .andExpect(jsonPath("$.reviewDetailReadResponseDtos.content[0].score", equalTo(1)))
            .andExpect(jsonPath("$.reviewFiles.size()", equalTo(1)))
            .andDo(document("review/review-byBook",
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("bookId").description("도서 번호")),
                relaxedResponseFields(
                    fieldWithPath("reviewDetailReadResponseDtos.content[].reviewId").description(
                        "리뷰 번호"),
                    fieldWithPath(
                        "reviewDetailReadResponseDtos.content[].reviewerEmail").description(
                        "리뷰 작성자 이메일"),
                    fieldWithPath("reviewDetailReadResponseDtos.content[].reviewTitle").description(
                        "리뷰 제목"),
                    fieldWithPath(
                        "reviewDetailReadResponseDtos.content[].reviewContent").description(
                        "리뷰 내용"),
                    fieldWithPath(
                        "reviewDetailReadResponseDtos.content[].registeredAt").description(
                        "리뷰 작성일"),
                    fieldWithPath("reviewDetailReadResponseDtos.content[].score").description(
                        "리뷰 평점"),
                    subsectionWithPath("reviewFiles").description("리뷰 사진"),
                    fieldWithPath("reviewDetailReadResponseDtos.totalPages").description("총 페이지"),
                    fieldWithPath("reviewDetailReadResponseDtos.totalElements").description("총 개수"),
                    fieldWithPath("reviewDetailReadResponseDtos.size").description("화면에 출력할 개수"),
                    fieldWithPath("reviewDetailReadResponseDtos.number").description("현재 페이지"),
                    fieldWithPath("reviewDetailReadResponseDtos.numberOfElements").description(
                        "현재 페이지 개수"))));
    }

    @Test
    @DisplayName("리뷰 생성")
    void createReview() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        ReviewCreateRequestDto reviewCreateRequestDto = ReviewCreateRequestDto.builder().bookId(1L)
            .orderDetailId(1L).reviewTitle("reviewTitle").reviewContent("reviewContent").score(1)
            .build();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("images", "images",
            MediaType.IMAGE_PNG_VALUE, "images".getBytes());
        String asString = objectMapper.writeValueAsString(reviewCreateRequestDto);
        MockMultipartFile mockReview = new MockMultipartFile("review", "review", "application/json",
            asString.getBytes());

        doNothing().when(reviewFacade)
            .createReviewAndUpdatePoint(anyLong(), any(ReviewCreateRequestDto.class), anyList());

        mockMvc.perform(multipart("/api/auth/reviews")
                .file(mockMultipartFile)
                .file(mockReview)
                .param("memberId", "1")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(objectMapper.writeValueAsString(reviewCreateRequestDto))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(document("review/review-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(parameterWithName("memberId").description("회원 번호")),
                requestFields(
                    fieldWithPath("bookId").description("도서 번호"),
                    fieldWithPath("orderDetailId").description("주문 상세 번호"),
                    fieldWithPath("reviewTitle").description("리뷰 제목"),
                    fieldWithPath("reviewContent").description("리뷰 설명"),
                    fieldWithPath("score").description("평점")
                ),
                requestParts(
                    partWithName("review").description("리뷰"),
                    partWithName("images").description("리뷰 이미지")
                )
            ));
    }

    @Test
    @DisplayName("리뷰 생성 - 검증 실패")
    void givenInvalidInput_whenCreateReview_thenThrowValidationException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        ReviewCreateRequestDto reviewCreateRequestDto = ReviewCreateRequestDto.builder().build();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("images", "images",
            MediaType.IMAGE_PNG_VALUE, "images".getBytes());
        String asString = objectMapper.writeValueAsString(reviewCreateRequestDto);
        MockMultipartFile mockReview = new MockMultipartFile("review", "review", "application/json",
            asString.getBytes());

        doNothing().when(reviewFacade)
            .createReviewAndUpdatePoint(anyLong(), any(ReviewCreateRequestDto.class), anyList());

        mockMvc.perform(RestDocumentationRequestBuilders.multipart("/api/auth/reviews")
                .file(mockMultipartFile)
                .file(mockReview)
                .param("memberId", "1")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(objectMapper.writeValueAsString(reviewCreateRequestDto))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                result -> assertTrue(result.getResolvedException() instanceof ValidationException))
            .andDo(document("review/review-create-fail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(parameterWithName("memberId").description("회원 번호")),
                requestFields(
                    fieldWithPath("bookId").description("도서 번호"),
                    fieldWithPath("orderDetailId").description("주문 상세 번호"),
                    fieldWithPath("reviewTitle").description("리뷰 제목"),
                    fieldWithPath("reviewContent").description("리뷰 설명"),
                    fieldWithPath("score").description("평점")
                ),
                requestParts(
                    partWithName("review").description("리뷰"),
                    partWithName("images").description("리뷰 이미지")
                ),
                responseFields(
                    fieldWithPath("message").description("오류 메세지")
                )
            ));
    }

    @Test
    @DisplayName("리뷰 수정")
    void updateReview() throws Exception {
        MockMultipartHttpServletRequestBuilder builders = RestDocumentationRequestBuilders.multipart(
            "/api/auth/reviews/{reviewId}", 1L);
        builders.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        ObjectMapper objectMapper = new ObjectMapper();

        ReviewUpdateRequestDto reviewUpdateRequestDto = ReviewUpdateRequestDto.builder()
            .reviewTitle("reviewTitle").reviewContent("reviewContent").score(1).build();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("images", "images",
            MediaType.IMAGE_PNG_VALUE, "images".getBytes());
        String asString = objectMapper.writeValueAsString(reviewUpdateRequestDto);
        MockMultipartFile mockReview = new MockMultipartFile("review", "review", "application/json",
            asString.getBytes());

        doNothing().when(reviewFacade).updateReview(anyLong(), anyLong(), any(), any());

        mockMvc.perform(builders.file(mockMultipartFile).file(mockReview).param("memberId", "1")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(objectMapper.writeValueAsString(reviewUpdateRequestDto))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("review/review-update",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(parameterWithName("memberId").description("회원 번호")),
                pathParameters(parameterWithName("reviewId").description("리뷰 번호")),
                requestFields(fieldWithPath("reviewTitle").description("리뷰 제목"),
                    fieldWithPath("reviewContent").description("리뷰 설명"),
                    fieldWithPath("score").description("리뷰 평점")),
                requestParts(partWithName("review").description("리뷰"),
                    partWithName("images").description("리뷰 이미지"))
            ));
    }

    @Test
    @DisplayName("리뷰 수정 - 검증 실패")
    void givenInvalidInput_whenUpdateReview_thenThrowValidationException() throws Exception {
        MockMultipartHttpServletRequestBuilder builders = RestDocumentationRequestBuilders.multipart(
            "/api/auth/reviews/{reviewId}", 1L);
        builders.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        ObjectMapper objectMapper = new ObjectMapper();

        ReviewUpdateRequestDto reviewUpdateRequestDto = ReviewUpdateRequestDto.builder().build();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("images", "images",
            MediaType.IMAGE_PNG_VALUE, "images".getBytes());
        String asString = objectMapper.writeValueAsString(reviewUpdateRequestDto);
        MockMultipartFile mockReview = new MockMultipartFile("review", "review", "application/json",
            asString.getBytes());

        doNothing().when(reviewFacade).updateReview(anyLong(), anyLong(), any(), any());

        mockMvc.perform(builders.file(mockMultipartFile).file(mockReview).param("memberId", "1")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(objectMapper.writeValueAsString(reviewUpdateRequestDto))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                result -> assertTrue(result.getResolvedException() instanceof ValidationException))
            .andDo(document("review/review-update-fail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(parameterWithName("memberId").description("회원 번호")),
                pathParameters(parameterWithName("reviewId").description("리뷰 번호")),
                requestFields(fieldWithPath("reviewTitle").description("리뷰 제목"),
                    fieldWithPath("reviewContent").description("리뷰 설명"),
                    fieldWithPath("score").description("리뷰 평점")),
                requestParts(partWithName("review").description("리뷰"),
                    partWithName("images").description("리뷰 이미지")),
                responseFields(fieldWithPath("message").description("오류 메세지"))
            ));
    }
}