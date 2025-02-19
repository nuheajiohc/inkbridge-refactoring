package com.nhnacademy.inkbridge.backend.dto.search;

import com.nhnacademy.inkbridge.backend.entity.Search;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * class: BookSearchReadResponseDto.
 *
 * @author choijaehun
 * @version 2024/03/11
 */

@Getter
@Builder
@AllArgsConstructor
public class BookSearchResponseDto {

    private Long id;
    private String bookTitle;
    private LocalDateTime publicatedAt;
    private Long regularPrice;
    private Long price;
    private Double discountRatio;
    private Long view;
    private Double score;
    private Long reviewQuantity;
    private Long publisherId;
    private String publisherName;
    private String statusName;
    private String fileUrl;
    private List<AuthorBySearch> authors;
    private List<TagBySearch> tags;
    private List<CategoryBySearch> categories;


    /**
     * search인덱스를 dto로 변환하는 메소드
     *
     * @param search 인덱스
     * @return dto
     */
    public static BookSearchResponseDto toBookSearchResponseDto(Search search) {
        return BookSearchResponseDto.builder()
            .id(search.getId())
            .bookTitle(search.getBookTitle())
            .publicatedAt(search.getPublicatedAt())
            .regularPrice(search.getRegularPrice())
            .price(search.getPrice())
            .discountRatio(search.getDiscountRatio())
            .view(search.getView())
            .score(search.getScore())
            .reviewQuantity(search.getReviewQuantity())
            .publisherId(search.getPublisherId())
            .publisherName(search.getPublisherName())
            .statusName(search.getStatusName())
            .fileUrl(search.getFileUrl())
            .authors(search.getAuthors())
            .tags(search.getTags())
            .categories(search.getCategories())
            .build();
    }
}
