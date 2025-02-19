package com.nhnacademy.inkbridge.backend.dto.search;

import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * class: TagBySearch.
 *
 * @author choijaehun
 * @version 2024/03/16
 */

@Getter
public class TagBySearch {
    @Field("tag_id")
    private Long tagId;
    @Field("tag_name")
    private String tagName;
}
