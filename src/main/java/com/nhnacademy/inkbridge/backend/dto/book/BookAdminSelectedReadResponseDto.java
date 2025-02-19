package com.nhnacademy.inkbridge.backend.dto.book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * class: BookAdminReadResponseDto.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Getter
public class BookAdminSelectedReadResponseDto {

    private final String bookTitle;

    private final String bookIndex;

    private final String description;

    private final LocalDate publicatedAt;

    private final String isbn;

    private final Long regularPrice;

    private final Long price;

    private final BigDecimal discountRatio;

    private final Integer stock;

    private final Boolean isPackagable;

    private final List<Long> authorIdList;

    private final Long publisherId;

    private final Long statusId;

    private final String url;

    private final List<Long> categoryIdList;

    private final List<Long> tagIdList;

    @Builder
    public BookAdminSelectedReadResponseDto(String bookTitle, String bookIndex, String description,
        LocalDate publicatedAt, String isbn, Long regularPrice, Long price,
        BigDecimal discountRatio, Integer stock, Boolean isPackagable,
        List<Long> authorIdList, Long publisherId, Long statusId, String url,
        List<Long> categoryIdList,
        List<Long> tagIdList) {
        this.bookTitle = bookTitle;
        this.bookIndex = bookIndex;
        this.description = description;
        this.publicatedAt = publicatedAt;
        this.isbn = isbn;
        this.regularPrice = regularPrice;
        this.price = price;
        this.discountRatio = discountRatio;
        this.stock = stock;
        this.isPackagable = isPackagable;
        this.authorIdList = authorIdList;
        this.publisherId = publisherId;
        this.statusId = statusId;
        this.url = url;
        this.categoryIdList = categoryIdList;
        this.tagIdList = tagIdList;
    }
}
