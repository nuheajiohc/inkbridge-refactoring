package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagDeleteResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateResponseDto;
import java.util.List;

/**
 * class: TagService.
 *
 * @author jeongbyeonghun
 * @version 2/15/24
 */
public interface TagService {

    TagCreateResponseDto createTag(TagCreateRequestDto newTag);

    List<TagReadResponseDto> getTagList();

    TagUpdateResponseDto updateTag(Long tagId, TagUpdateRequestDto newTag);

    TagDeleteResponseDto deleteTag(Long tagId);
}
