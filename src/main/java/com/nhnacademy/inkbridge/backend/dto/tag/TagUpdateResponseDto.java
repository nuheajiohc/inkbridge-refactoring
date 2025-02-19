package com.nhnacademy.inkbridge.backend.dto.tag;

import com.nhnacademy.inkbridge.backend.entity.Tag;
import lombok.Builder;
import lombok.Getter;

/**
 * class: TagUpdateResponseDto.
 *
 * @author jeongbyeonghun
 * @version 2/15/24
 */

@Getter
public class TagUpdateResponseDto {

    private final Long tagId;
    private final String tagName;

    @Builder
    public TagUpdateResponseDto(Tag tag) {
        this.tagId = tag.getTagId();
        this.tagName = tag.getTagName();
    }
}
