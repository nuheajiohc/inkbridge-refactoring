package com.nhnacademy.inkbridge.backend.dto.book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;

/**
 * class: BookCreateRequestDto.
 *
 * @author minm063
 * @version 2024/02/14
 */
@Getter
public class BookAdminCreateRequestDto {

    @NotBlank(message = "도서 제목은 한 글자 이상이여야 합니다.")
    private String bookTitle;

    private String bookIndex;

    private String description;

    private LocalDate publicatedAt;

    @NotBlank
    @Pattern(regexp = "^\\d{13}$", message = "isbn은 숫자 13자로 구성되어야 합니다.")
    private String isbn;

    private Long regularPrice;

    private Long price;

    private BigDecimal discountRatio;

    private Integer stock;

    private Boolean isPackagable;

    private Long publisherId;

    private Set<Long> categories;

    private List<Long> tags;

    private List<Long> authorIdList;

    private List<Long> fileIdList;
}
