package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Tag;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookTagRepository;
import com.nhnacademy.inkbridge.backend.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * class: TagServiceImplTest.
 *
 * @author jeongbyeonghun
 * @version 2/16/24
 */
@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private BookTagRepository bookTagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    private static Tag testTag1;
    private static Tag testTag2;

    private static String testTagName1;
    private static String testTagName2;
    private static Long testTagId1;
    private static Long testTagId2;

    @BeforeAll
    static void setTest() {
        testTagName1 = "testTag1";
        testTagName2 = "testTag2";
        testTagId1 = 1L;
        testTagId2 = 2L;
        testTag1 = Tag.builder().tagId(testTagId1).tagName(testTagName1).build();
        testTag2 = Tag.builder().tagId(testTagId2).tagName(testTagName2).build();
    }


    @Test
    void createTag() {

        TagCreateRequestDto newTestTag = new TagCreateRequestDto();
        newTestTag.setTagName(testTagName1);
        when(tagRepository.existsByTagName(testTagName1)).thenReturn(false);
        when(tagRepository.save(any(Tag.class))).thenReturn(testTag1);
        TagCreateResponseDto tagCreateResponseDto = tagService.createTag(newTestTag);
        assertEquals(testTagName1, tagCreateResponseDto.getTagName());
        assertEquals(testTagId1, tagCreateResponseDto.getTagId());
    }

    @Test
    void createTagWhenAlreadyExist() {
        TagCreateRequestDto newTestTag = new TagCreateRequestDto();
        newTestTag.setTagName(testTagName1);
        when(tagRepository.existsByTagName(testTagName1)).thenReturn(true);
        assertThrows(AlreadyExistException.class, () -> tagService.createTag(newTestTag));
    }

    @Test
    void getTafList() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(testTag1);
        tagList.add(testTag2);
        when(tagRepository.findAll()).thenReturn(tagList);
        List<TagReadResponseDto> result = tagService.getTagList();
        assertEquals(result.size(), tagList.size());
        assertEquals(result.get(0).getTagId(), testTagId1);
        assertEquals(result.get(0).getTagName(), testTagName1);
        assertEquals(result.get(1).getTagId(), testTagId2);
        assertEquals(result.get(1).getTagName(), testTagName2);
    }

    @Test
    void updateTag() {
        TagUpdateRequestDto tagUpdateRequestDto = new TagUpdateRequestDto();
        tagUpdateRequestDto.setTagName(testTagName1);
        when(tagRepository.existsById(testTagId1)).thenReturn(true);
        when(tagRepository.existsByTagName(testTagName1)).thenReturn(false);
        when(tagRepository.save(any(Tag.class))).thenReturn(
            Tag.builder().tagId(testTagId1).tagName("updatedTagName").build());
        TagUpdateResponseDto tagUpdateResponseDto = tagService.updateTag(testTagId1,
            tagUpdateRequestDto);
        assertEquals("updatedTagName", tagUpdateResponseDto.getTagName());
    }

    @Test
    void updateTagWhenNotFoundTag() {
        TagUpdateRequestDto tagUpdateRequestDto = new TagUpdateRequestDto();
        when(tagRepository.existsById(testTagId1)).thenReturn(false);
        assertThrows(NotFoundException.class,
            () -> tagService.updateTag(testTagId1, tagUpdateRequestDto));
    }

    @Test
    void updateTagWhenAlreadyExist() {
        TagUpdateRequestDto tagUpdateRequestDto = new TagUpdateRequestDto();
        tagUpdateRequestDto.setTagName(testTagName1);
        when(tagRepository.existsById(testTagId1)).thenReturn(true);
        when(tagRepository.existsByTagName(testTagName1)).thenReturn(true);
        assertThrows(AlreadyExistException.class,
            () -> tagService.updateTag(testTagId1, tagUpdateRequestDto));
    }

    @Test
    void deleteTag() {
        when(tagRepository.existsById(testTagId1)).thenReturn(true);
        doNothing().when(bookTagRepository).deleteAllByPk_TagId(anyLong());
        doNothing().when(tagRepository).deleteById(anyLong());
        assertEquals(testTagId1 + " is deleted", tagService.deleteTag(testTagId1).getMessage());

        verify(bookTagRepository, times(1)).deleteAllByPk_TagId(anyLong());
        verify(tagRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteTagWhenNotFound() {
        when(tagRepository.existsById(testTagId1)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> tagService.deleteTag(testTagId1));
    }
}