package com.nhnacademy.inkbridge.backend.dto.coupon;

import com.nhnacademy.inkbridge.backend.dto.book.BookIdNameReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryReadResponseDto;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: CouponListResponseDto.
 *
 * @author JBum
 * @version 2024/02/22
 */
@Getter
@NoArgsConstructor
public class CouponDetailReadResponseDto {

    private String couponId;
    private String couponName;
    private Long minPrice;
    private Long discountPrice;
    private Long maxDiscountPrice;
    private String basicIssuedDate;
    private String basicExpiredDate;
    private Integer validity;
    private String couponTypeName;
    private Boolean isBirth;
    private String couponStatusName;
    private List<CategoryReadResponseDto> categories;
    private List<BookIdNameReadResponseDto> books;

    public CouponDetailReadResponseDto(
        String couponId, String couponName, Long minPrice,
        Long discountPrice, Long maxDiscountPrice, String basicIssuedDate,
        String basicExpiredDate, Integer validity, String couponTypeName,
        Boolean isBirth, String couponStatusName) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.minPrice = minPrice;
        this.discountPrice = discountPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.basicIssuedDate = basicIssuedDate;
        this.basicExpiredDate = basicExpiredDate;
        this.validity = validity;
        this.couponTypeName = couponTypeName;
        this.isBirth = isBirth;
        this.couponStatusName = couponStatusName;
    }

    public void setRelation(List<CategoryReadResponseDto> categories,
        List<BookIdNameReadResponseDto> books) {
        this.categories = categories;
        this.books = books;
    }
}
