package com.nhnacademy.inkbridge.backend.dto.tag;

import com.nhnacademy.inkbridge.backend.entity.Tag;
import lombok.Builder;
import lombok.Getter;

/**
 * class: TagCreateResponseDto.
 *
 * @author jeongbyeonghun
 * @version 2/15/24
 */
@Getter
public class TagCreateResponseDto {

    private final Long tagId;
    private final String tagName;

    @Builder
    public TagCreateResponseDto(Tag tag) {
        this.tagId = tag.getTagId();
        this.tagName = tag.getTagName();
    }
}
