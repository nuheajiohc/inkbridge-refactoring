package com.nhnacademy.inkbridge.backend.dto.tag;

import com.nhnacademy.inkbridge.backend.entity.Tag;
import lombok.Builder;
import lombok.Getter;

/**
 * class: TagReadResponseDto.
 *
 * @author jeongbyeonghun
 * @version 2/18/24
 */
@Getter
public class TagReadResponseDto {

    private final Long tagId;
    private final String tagName;

    @Builder
    public TagReadResponseDto(Tag tag) {
        this.tagId = tag.getTagId();
        this.tagName = tag.getTagName();
    }
}
