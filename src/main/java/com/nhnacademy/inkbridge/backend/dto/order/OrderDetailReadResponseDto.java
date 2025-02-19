package com.nhnacademy.inkbridge.backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class: OrderDetailResponseDto.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
@Getter
@AllArgsConstructor
public class OrderDetailReadResponseDto {

    private Long orderDetailId;
    private Long bookPrice;
    private Long wrappingPrice;
    private Integer amount;
    private String wrappingName;
    private String orderStatus;
    private Long bookId;
    private String thumbnailUrl;
    private String bookTitle;
    private String couponTypeName;
    private String couponName;
    private Long maxDiscountPrice;
    private Long discountPrice;
}
