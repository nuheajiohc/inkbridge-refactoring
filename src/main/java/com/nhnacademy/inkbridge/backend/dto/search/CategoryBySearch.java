package com.nhnacademy.inkbridge.backend.dto.search;

import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * class: CategoryBySearch.
 *
 * @author choijaehun
 * @version 2024/03/23
 */

@Getter
public class CategoryBySearch {
    @Field("category_id")
    private Long categoryId;

    @Field("category_name")
    private String categoryName;
}
