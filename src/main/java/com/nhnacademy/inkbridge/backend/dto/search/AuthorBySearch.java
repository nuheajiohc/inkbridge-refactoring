package com.nhnacademy.inkbridge.backend.dto.search;

import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * class: AuthorBySearch.
 *
 * @author choijaehun
 * @version 2024/03/16
 */

@Getter
public class AuthorBySearch {

    @Field(name="author_id")
    private Long authorId;
    @Field(name="author_name")
    private String authorName;
}
