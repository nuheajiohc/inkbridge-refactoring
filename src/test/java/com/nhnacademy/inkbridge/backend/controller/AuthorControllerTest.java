package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.author.AuthorCreateUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.AuthorService;
import com.nhnacademy.inkbridge.backend.service.FileService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

/**
 * class: AuthorControllerTest.
 *
 * @author minm063
 * @version 2024/03/16
 */
@AutoConfigureRestDocs
@WebMvcTest(AuthorController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class AuthorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthorService authorService;

    @MockBean
    FileService fileService;

    @MockBean
    Pageable pageable;

    AuthorInfoReadResponseDto authorInfoReadResponseDto;

    @BeforeEach
    void setup() {
        authorInfoReadResponseDto = AuthorInfoReadResponseDto.builder()
            .authorId(1L).authorName("name").authorIntroduce("introduce").fileUrl("url").build();

    }

    @Test
    @DisplayName("작가 아이디로 작가 정보 조회")
    void getAuthor() throws Exception {
        when(authorService.getAuthor(anyLong())).thenReturn(authorInfoReadResponseDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/authors/{authorId}", 1L))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.authorId", equalTo(1)))
            .andExpect(jsonPath("$.authorName", equalTo("name")))
            .andExpect(jsonPath("$.authorIntroduce", equalTo("introduce")))
            .andExpect(jsonPath("$.fileUrl", equalTo("url")))
            .andDo(document("author/author-get",
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("authorId").description("작가 번호")
                ),
                responseFields(
                    fieldWithPath("authorId").description("작가 번호"),
                    fieldWithPath("authorName").description("작가 이름"),
                    fieldWithPath("authorIntroduce").description("작가 설명"),
                    fieldWithPath("fileUrl").description("작가 사진")
                )));
    }

    @Test
    @DisplayName("작가 이름으로 작가 정보 조회")
    void getAuthorByName() throws Exception {
        when(authorService.getAuthorsByName(anyString())).thenReturn(
            List.of(authorInfoReadResponseDto));

        mockMvc.perform(get("/api/authors")
                .param("authorName", "name"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[0].authorId", equalTo(1)))
            .andExpect(jsonPath("$.[0].authorName", equalTo("name")))
            .andExpect(jsonPath("$.[0].authorIntroduce", equalTo("introduce")))
            .andExpect(jsonPath("$.[0].fileUrl", equalTo("url")))
            .andDo(document("author/author-get-byName",
                preprocessResponse(prettyPrint()),
                requestParameters(parameterWithName("authorName").description("작가 이름")),
                responseFields(
                    fieldWithPath("[].authorId").description("작가 번호"),
                    fieldWithPath("[].authorName").description("작가 이름"),
                    fieldWithPath("[].authorIntroduce").description("작가 설명"),
                    fieldWithPath("[].fileUrl").description("작가 사진")
                )));
    }

    @Test
    @DisplayName("전체 작가 목록 조회")
    void getAuthors() throws Exception {
        Page<AuthorInfoReadResponseDto> page = new PageImpl<>(List.of(authorInfoReadResponseDto));

        when(authorService.getAuthors(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/admin/authors"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content[0].authorId", equalTo(1)))
            .andExpect(jsonPath("$.content[0].authorName", equalTo("name")))
            .andExpect(jsonPath("$.content[0].authorIntroduce", equalTo("introduce")))
            .andExpect(jsonPath("$.content[0].fileUrl", equalTo("url")))
            .andDo(document("author/authors-get",
                preprocessResponse(prettyPrint()),
                relaxedResponseFields(
                    fieldWithPath("content[].authorId").description("작가 번호"),
                    fieldWithPath("content[].authorName").description("작가 이름"),
                    fieldWithPath("content[].authorIntroduce").description("작가 설명"),
                    fieldWithPath("content[].fileUrl").description("작가 사진"),
                    fieldWithPath("totalPages").description("총 페이지"),
                    fieldWithPath("totalElements").description("총 개수"),
                    fieldWithPath("size").description("화면에 출력할 개수"),
                    fieldWithPath("number").description("현재 페이지"),
                    fieldWithPath("numberOfElements").description("현재 페이지 개수")
                )));
    }

    @Test
    @DisplayName("작가 등록")
    void createAuthor() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto = new AuthorCreateUpdateRequestDto();
        ReflectionTestUtils.setField(authorCreateUpdateRequestDto, "authorName", "authorName");
        ReflectionTestUtils.setField(authorCreateUpdateRequestDto, "authorIntroduce",
            "authorIntroduce");
        String asString = objectMapper.writeValueAsString(authorCreateUpdateRequestDto);
        MockMultipartFile author = new MockMultipartFile("author", "author", "application/json",
            asString.getBytes());

        MockMultipartFile authorFile = new MockMultipartFile(
            "authorFile",
            "authorFile",
            MediaType.IMAGE_PNG_VALUE,
            "authorFile".getBytes()
        );

        when(fileService.saveThumbnail(any())).thenReturn(File.builder().build());
        doNothing().when(authorService).createAuthor(any(File.class), any(
            AuthorCreateUpdateRequestDto.class));

        mockMvc.perform(multipart("/api/admin/authors")
                .file(authorFile)
                .file(author)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(objectMapper.writeValueAsString(authorCreateUpdateRequestDto))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(document("author/author-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParts(
                    partWithName("author").description("작가"),
                    partWithName("authorFile").description("작가 사진")
                )
            ));
    }

    @Test
    @DisplayName("작가 등록 유효성 검증 실패")
    void givenInvalidInput_whenCreateAuthor_thenThrowException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto = new AuthorCreateUpdateRequestDto();
        String asString = objectMapper.writeValueAsString(authorCreateUpdateRequestDto);
        MockMultipartFile author = new MockMultipartFile("author", "author", "application/json",
            asString.getBytes());

        MockMultipartFile authorFile = new MockMultipartFile(
            "authorFile",
            "authorFile",
            MediaType.IMAGE_PNG_VALUE,
            "authorFile".getBytes()
        );

        when(fileService.saveThumbnail(any())).thenReturn(File.builder().build());
        doNothing().when(authorService).createAuthor(any(File.class), any(
            AuthorCreateUpdateRequestDto.class));

        mockMvc.perform(multipart("/api/admin/authors")
                .file(authorFile)
                .file(author)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(objectMapper.writeValueAsString(authorCreateUpdateRequestDto))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                result -> assertTrue(result.getResolvedException() instanceof ValidationException))
            .andDo(document("author/author-create-fail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("message").description("오류 메세지")
                )
            ));
    }

    @Test
    @DisplayName("작가 수정")
    void updateAuthor() throws Exception {
        MockMultipartHttpServletRequestBuilder builders = RestDocumentationRequestBuilders.multipart(
            "/api/admin/authors/{authorId}", 1L);
        builders.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        ObjectMapper objectMapper = new ObjectMapper();
        AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto = new AuthorCreateUpdateRequestDto();
        ReflectionTestUtils.setField(authorCreateUpdateRequestDto, "authorName", "authorName");
        ReflectionTestUtils.setField(authorCreateUpdateRequestDto, "authorIntroduce",
            "authorIntroduce");

        String asString = objectMapper.writeValueAsString(authorCreateUpdateRequestDto);
        MockMultipartFile author = new MockMultipartFile("author", "author", "application/json",
            asString.getBytes());
        MockMultipartFile authorFile = new MockMultipartFile("authorFile", "authorFile",
            MediaType.IMAGE_PNG_VALUE, "authorFile".getBytes());

        when(fileService.saveThumbnail(any())).thenReturn(File.builder().build());
        doNothing().when(authorService).updateAuthor(any(File.class), any(
            AuthorCreateUpdateRequestDto.class), anyLong());

        mockMvc.perform(builders
                .file(authorFile)
                .file(author)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(objectMapper.writeValueAsString(authorCreateUpdateRequestDto))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("author/author-update",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("authorId").description("작가 번호")),
                requestFields(
                    fieldWithPath("authorName").description("작가 이름"),
                    fieldWithPath("authorIntroduce").description("작가 소개")
                ),
                requestParts(
                    partWithName("author").description("작가"),
                    partWithName("authorFile").description("작가 사진")
                )));
    }

    @Test
    @DisplayName("작가 수정 유효성 검증 실패")
    void givenInvalidInput_whenUpdateAuthor_thenThrowException() throws Exception {
        MockMultipartHttpServletRequestBuilder builders = RestDocumentationRequestBuilders.multipart(
            "/api/admin/authors/{authorId}", 1L);
        builders.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        ObjectMapper objectMapper = new ObjectMapper();
        AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto = new AuthorCreateUpdateRequestDto();

        String asString = objectMapper.writeValueAsString(authorCreateUpdateRequestDto);
        MockMultipartFile author = new MockMultipartFile("author", "author", "application/json",
            asString.getBytes());
        MockMultipartFile authorFile = new MockMultipartFile("authorFile", "authorFile",
            MediaType.IMAGE_PNG_VALUE, "authorFile".getBytes());

        when(fileService.saveThumbnail(any())).thenReturn(File.builder().build());
        doNothing().when(authorService).updateAuthor(any(File.class), any(
            AuthorCreateUpdateRequestDto.class), anyLong());

        mockMvc.perform(builders
                .file(authorFile)
                .file(author))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                result -> assertTrue(result.getResolvedException() instanceof ValidationException))
            .andDo(document("author/author-update-fail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("authorId").description("작가 번호")),
                responseFields(
                    fieldWithPath("message").description("오류 메세지")
                ),
                requestParts(
                    partWithName("author").description("작가"),
                    partWithName("authorFile").description("작가 사진")
                )));
    }
}