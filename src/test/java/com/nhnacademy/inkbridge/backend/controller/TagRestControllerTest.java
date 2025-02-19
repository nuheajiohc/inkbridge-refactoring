package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagDeleteResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Tag;
import com.nhnacademy.inkbridge.backend.enums.TagMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.service.TagService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: TagRestControllerTest.
 *
 * @author jeongbyeonghun
 * @version 2/16/24
 */

@AutoConfigureRestDocs
@WebMvcTest(TagRestController.class)
@ExtendWith(RestDocumentationExtension.class)
class TagRestControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TagService tagService;

    ObjectMapper objectMapper = new ObjectMapper();

    private static Tag testTag1;
    private static Tag testTag2;

    private static String testTagName1;
    private static String testTagName2;
    private static Long testTagId1;

    @BeforeAll
    static void setTest() {
        testTagName1 = "testTag1";
        testTagName2 = "testTag2";
        testTagId1 = 1L;
        Long testTagId2 = 2L;
        testTag1 = Tag.builder().tagId(testTagId1).tagName(testTagName1).build();
        testTag2 = Tag.builder().tagId(testTagId2).tagName(testTagName2).build();
    }

    @Test
    void createTag() throws Exception {
        TagCreateRequestDto tagCreateRequestDto = new TagCreateRequestDto();
        tagCreateRequestDto.setTagName("testTag");
        TagCreateResponseDto tagCreateResponseDto = new TagCreateResponseDto(
            Tag.builder().tagName("testTag").tagId(testTagId1).build());
        given(tagService.createTag(any(TagCreateRequestDto.class))).willReturn(
            tagCreateResponseDto);

        mvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagCreateRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.tagName", equalTo("testTag")))
            .andExpect(jsonPath("$.tagId", equalTo(testTagId1.intValue())))
            .andDo(document("tag-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("tagName").description("생성할 태그의 이름")
                ),
                responseFields(
                    fieldWithPath("tagName").description("생성된 태그의 이름"),
                    fieldWithPath("tagId").description("생성된 태그의 ID")
                )));
    }

    @Test
    void createTagWhenValidationFailed() throws Exception {
        TagCreateRequestDto tagCreateRequestDto = new TagCreateRequestDto();
        tagCreateRequestDto.setTagName("");
        mvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagCreateRequestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                jsonPath("message", equalTo(TagMessageEnum.TAG_TYPE_VALID_FAIL.getMessage())))
            .andDo(document("docs"));
    }

    @Test
    void createTagWhenAlreadyExist() throws Exception {
        TagCreateRequestDto newTestTag = new TagCreateRequestDto();
        newTestTag.setTagName(testTagName1);
        when(tagService.createTag(newTestTag)).thenThrow(AlreadyExistException.class);
        doThrow(new AlreadyExistException(TagMessageEnum.TAG_ALREADY_EXIST.name()))
            .when(tagService).createTag(any());
        mvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTestTag)))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("message", equalTo(TagMessageEnum.TAG_ALREADY_EXIST.name())))
            .andDo(document("docs"));
    }

    @Test
    void getTagList() throws Exception {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(testTag1);
        tagList.add(testTag2);
        List<TagReadResponseDto> result = tagList.stream().map(TagReadResponseDto::new)
            .collect(Collectors.toList());
        when(tagService.getTagList()).thenReturn(result);
        mvc.perform(get("/api/tags")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].tagName", equalTo(tagList.get(0).getTagName())))
            .andExpect(jsonPath("$[0].tagId", equalTo(tagList.get(0).getTagId().intValue())))
            .andExpect(jsonPath("$[1].tagName", equalTo(tagList.get(1).getTagName())))
            .andExpect(jsonPath("$[1].tagId", equalTo(tagList.get(1).getTagId().intValue())))
            .andDo(document("tag-list",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].tagName").description("태그의 이름"),
                    fieldWithPath("[].tagId").description("태그의 식별자")
                )));
    }

    @Test
    void updateTag() throws Exception {
        TagUpdateRequestDto tagUpdateRequestDto = new TagUpdateRequestDto();
        tagUpdateRequestDto.setTagName(testTagName2);
        TagUpdateResponseDto tagUpdateResponseDto = new TagUpdateResponseDto(
            Tag.builder().tagId(testTagId1).tagName(testTagName2).build());
        when(tagService.updateTag(any(), any())).thenReturn(
            tagUpdateResponseDto);
        mvc.perform(RestDocumentationRequestBuilders.put("/api/tags/{tagId}", testTagId1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagUpdateRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tagId", equalTo(testTagId1.intValue())))
            .andExpect(jsonPath("$.tagName", equalTo(testTagName2)))
            .andDo(document("tag-update",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("tagId").description("업데이트할 태그의 ID")
                ),
                requestFields(
                    fieldWithPath("tagName").description("업데이트할 새 태그의 이름")
                ),
                responseFields(
                    fieldWithPath("tagId").description("업데이트된 태그의 ID"),
                    fieldWithPath("tagName").description("업데이트된 태그의 이름")
                )));
    }

    @Test
    void updateTagWhenValidationFailed() throws Exception {
        TagUpdateRequestDto tagUpdateRequestDto = new TagUpdateRequestDto();

        mvc.perform(put("/api/tags/{tagId}", testTagId1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagUpdateRequestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                jsonPath("message", equalTo(TagMessageEnum.TAG_TYPE_VALID_FAIL.getMessage())))
            .andDo(document("docs"));
    }

    @Test
    void updateTagWhenNotFoundTag() throws Exception {
        TagUpdateRequestDto tagUpdateRequestDto = new TagUpdateRequestDto();
        tagUpdateRequestDto.setTagName(testTagName2);
        when(tagService.updateTag(any(), any())).thenThrow(
            new NotFoundException(TagMessageEnum.TAG_NOT_FOUND.getMessage()));

        mvc.perform(put("/api/tags/{tagId}", testTagId1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagUpdateRequestDto)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("message", equalTo(TagMessageEnum.TAG_NOT_FOUND.getMessage())))
            .andDo(document("docs"));
    }

    @Test
    void updateTagWhenAlreadyExist() throws Exception {
        TagUpdateRequestDto tagUpdateRequestDto = new TagUpdateRequestDto();
        tagUpdateRequestDto.setTagName(testTagName1);
        when(tagService.updateTag(any(), any())).thenThrow(
            new AlreadyExistException(TagMessageEnum.TAG_ALREADY_EXIST.getMessage()));

        mvc.perform(put("/api/tags/{tagId}", testTagId1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagUpdateRequestDto)))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("message", equalTo(TagMessageEnum.TAG_ALREADY_EXIST.getMessage())))
            .andDo(document("docs"));
    }

    @Test
    void deleteTag() throws Exception {
        when(tagService.deleteTag(testTagId1)).thenReturn(
            new TagDeleteResponseDto(testTag1 + " is deleted"));
        mvc.perform(RestDocumentationRequestBuilders.delete("/api/tags/{tagId}", testTagId1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("message", equalTo(testTag1 + " is deleted")))
            .andDo(document("tag-delete",
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("tagId").description("삭제할 태그의 ID")
                ),
                responseFields(
                    fieldWithPath("message").description("삭제 성공 메시지")
                )));
    }

    @Test
    void deleteTagWhenNotFound() throws Exception {
        when(tagService.deleteTag(testTagId1)).thenThrow(
            new NotFoundException(TagMessageEnum.TAG_NOT_FOUND.name()));
        mvc.perform(delete("/api/tags/{tagId}", testTagId1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("message", equalTo(TagMessageEnum.TAG_NOT_FOUND.name())))
            .andDo(document("docs"));
    }
}