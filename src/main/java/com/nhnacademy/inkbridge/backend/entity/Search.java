package com.nhnacademy.inkbridge.backend.entity;

import com.nhnacademy.inkbridge.backend.dto.search.AuthorBySearch;
import com.nhnacademy.inkbridge.backend.dto.search.CategoryBySearch;
import com.nhnacademy.inkbridge.backend.dto.search.TagBySearch;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * class: BookSearch.
 *
 * @author choijaehun
 * @version 2024/03/10
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Document(indexName="#{elasticsearch.index-name}")
//@Document(indexName = "inkbridge_book_dev")
@Document(indexName = "inkbridge_book_prod")
@Builder
@AllArgsConstructor
public class Search {

    @Id
    private Long id;
    @Field("book_title")
    private String bookTitle;
    @Field("publicated_at")
    private LocalDateTime publicatedAt;
    @Field("regular_price")
    private Long regularPrice;
    @Field("price")
    private Long price;
    @Field("discount_ratio")
    private Double discountRatio;
    @Field("view")
    private Long view;
    @Field("score")
    private Double score;
    @Field("review_quantity")
    private Long reviewQuantity;
    @Field("publisher_id")
    private Long publisherId;
    @Field("publisher_name")
    private String publisherName;
    @Field("state_name")
    private String statusName;
    @Field("file_url")
    private String fileUrl;
    @Field(name = "authors", type = FieldType.Nested)
    private List<AuthorBySearch> authors;
    @Field("tags")
    private List<TagBySearch> tags;
    @Field("categories")
    private List<CategoryBySearch> categories;
}
