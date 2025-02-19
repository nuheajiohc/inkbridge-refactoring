package com.nhnacademy.inkbridge.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.dto.book.BookIdNameReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.CouponStatus;
import com.nhnacademy.inkbridge.backend.entity.CouponType;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: CouponControllerTest.
 *
 * @author JBum
 * @version 2024/03/25
 */
@AutoConfigureRestDocs
@WebMvcTest(CouponController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class CouponControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CouponService couponService;
    
    @Test
    void getCoupons() throws Exception {
        CouponReadResponseDto coupon1 = new CouponReadResponseDto("1", "쿠폰1", 10000L, 5000L, 0L,
            LocalDate.now(), LocalDate.now().plusDays(7), 7,
            CouponType.builder().couponTypeId(1).typeName("%").build(), true,
            CouponStatus.builder().couponStatusId(1).couponStatusName("정상").build());
        CouponReadResponseDto coupon2 = new CouponReadResponseDto("2", "쿠폰2", 20000L, 10000L, 0L,
            LocalDate.now(), LocalDate.now().plusDays(7), 7,
            CouponType.builder().couponTypeId(1).typeName("%").build(), true,
            CouponStatus.builder().couponStatusId(1).couponStatusName("정상").build());
        Page<CouponReadResponseDto> coupons = new PageImpl<>(Collections.singletonList(coupon1));

        given(couponService.getIssuableCoupons(any(Pageable.class))).willReturn(coupons);

        mockMvc.perform(get("/api/coupons").param("page", "0"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].couponId").value("1"))
            .andExpect(jsonPath("$.content[0].couponName").value("쿠폰1"))
            .andExpect(jsonPath("$.content[0].minPrice").value(10000))
            .andExpect(jsonPath("$.content[0].discountPrice").value(5000))
            .andExpect(jsonPath("$.content[0].maxDiscountPrice").value(0))
            .andExpect(jsonPath("$.content[0].basicIssuedDate").exists())
            .andExpect(jsonPath("$.content[0].basicExpiredDate").exists())
            .andExpect(jsonPath("$.content[0].validity").value(7))
            .andExpect(jsonPath("$.content[0].couponTypeId").value(1))
            .andExpect(jsonPath("$.content[0].isBirth").value(true))
            .andExpect(jsonPath("$.content[0].couponStatusId").value(1))
            .andExpect(jsonPath("$.pageable").value("INSTANCE"))
            .andExpect(jsonPath("$.last").value(true))
            .andExpect(jsonPath("$.totalPages").value(1))
            .andExpect(jsonPath("$.totalElements").value(1))
            .andExpect(jsonPath("$.number").value(0))
            .andExpect(jsonPath("$.sort.empty").value(true))
            .andExpect(jsonPath("$.sort.sorted").value(false))
            .andExpect(jsonPath("$.sort.unsorted").value(true))
            .andExpect(jsonPath("$.first").value(true))
            .andExpect(jsonPath("$.size").value(1))
            .andExpect(jsonPath("$.numberOfElements").value(1))
            .andExpect(jsonPath("$.empty").value(false))
            .andDo(document("coupons",
                preprocessRequest(prettyPrint()), // 요청 데이터 예쁘게 출력
                preprocessResponse(prettyPrint()), // 응답 데이터 예쁘게 출력
                responseFields(
                    fieldWithPath("content[].couponId").description("쿠폰 ID"),
                    fieldWithPath("content[].couponName").description("쿠폰 이름"),
                    fieldWithPath("content[].minPrice").description("최소 주문 가격"),
                    fieldWithPath("content[].discountPrice").description("할인 가격"),
                    fieldWithPath("content[].maxDiscountPrice").description("최대 할인 가격"),
                    fieldWithPath("content[].basicIssuedDate").description("쿠폰 발급일"),
                    fieldWithPath("content[].basicExpiredDate").description("쿠폰 만료일"),
                    fieldWithPath("content[].validity").description("쿠폰 유효 기간"),
                    fieldWithPath("content[].couponTypeId").description("쿠폰 유형 ID"),
                    fieldWithPath("content[].isBirth").description
                        ("생일 쿠폰 여부"),
                    fieldWithPath("content[].couponStatusId").description("쿠폰 상태 ID"),
                    fieldWithPath("pageable").description("페이지 정보"),
                    fieldWithPath("last").description("마지막 페이지 여부"),
                    fieldWithPath("totalPages").description("전체 페이지 수"),
                    fieldWithPath("totalElements").description("전체 요소 수"),
                    fieldWithPath("number").description("현재 페이지 번호 (0부터 시작)"),
                    fieldWithPath("size").description("페이지 크기"),
                    fieldWithPath("numberOfElements").description("현재 페이지의 요소 수"),
                    fieldWithPath("empty").description("비어 있는지 여부"),
                    fieldWithPath("sort.empty").description("정렬 여부: 비어 있음"),
                    fieldWithPath("sort.sorted").description("정렬 여부: 정렬됨"),
                    fieldWithPath("sort.unsorted").description("정렬 여부: 정렬되지 않음"),
                    fieldWithPath("first").description("처음")
                )
            ));
        verify(couponService, times(1)).getIssuableCoupons(PageRequest.of(0, 20));

    }

    @Test
    void getCoupon() throws Exception {
        // 테스트에 사용할 쿠폰 정보
        CouponDetailReadResponseDto couponDetailReadResponseDto = new CouponDetailReadResponseDto(
            "1", "쿠폰1", 10000L, 5000L, 0L, LocalDate.now().toString(),
            LocalDate.now().plusDays(7).toString(),
            7, "%", true, "정상");

        // 관련 카테고리 및 책 목록 설정
        List<CategoryReadResponseDto> categories = new ArrayList<>();
        List<BookIdNameReadResponseDto> books = new ArrayList<>();
        books.add(new BookIdNameReadResponseDto(1L, "테스트책"));
        couponDetailReadResponseDto.setRelation(categories, books);

        // couponService.getDetailCoupon() 메서드가 호출될 때 반환할 값 설정
        given(couponService.getDetailCoupon(any())).willReturn(couponDetailReadResponseDto);

        // API 엔드포인트 호출 및 응답 확인
        mockMvc.perform(get("/api/coupons/{couponId}", "1"))
            .andExpect(status().isOk()) // HTTP 상태코드가 200인지 확인
            .andExpect(jsonPath("$.couponId").exists())
            .andExpect(jsonPath("$.couponId").value("1"))
            .andExpect(jsonPath("$.couponName").exists())
            .andExpect(jsonPath("$.couponName").value("쿠폰1"))
            .andExpect(jsonPath("$.minPrice").exists())
            .andExpect(jsonPath("$.minPrice").value(10000L))
            .andExpect(jsonPath("$.discountPrice").exists())
            .andExpect(jsonPath("$.discountPrice").value(5000L))
            .andExpect(jsonPath("$.maxDiscountPrice").exists())
            .andExpect(jsonPath("$.maxDiscountPrice").value(0L))
            .andExpect(jsonPath("$.basicIssuedDate").exists())
            .andExpect(jsonPath("$.basicExpiredDate").exists())
            .andExpect(jsonPath("$.validity").exists())
            .andExpect(jsonPath("$.validity").value(7))
            .andExpect(jsonPath("$.couponTypeName").exists())
            .andExpect(jsonPath("$.couponTypeName").value("%"))
            .andExpect(jsonPath("$.isBirth").exists())
            .andExpect(jsonPath("$.isBirth").value(true))
            .andExpect(jsonPath("$.couponStatusName").exists())
            .andExpect(jsonPath("$.couponStatusName").value("정상"))
            .andExpect(jsonPath("$.categories").exists())
            .andExpect(jsonPath("$.books[0].bookId").value(1L))
            .andExpect(jsonPath("$.books[0].bookTitle").value("테스트책"))
            .andDo(document("coupons/couponId", // 문서의 이름
                responseFields( // 응답 필드에 대한 문서화
                    fieldWithPath("couponId").description("쿠폰 ID"),
                    fieldWithPath("couponName").description("쿠폰 이름"),
                    fieldWithPath("minPrice").description("최소 가격"),
                    fieldWithPath("discountPrice").description("할인 가격"),
                    fieldWithPath("maxDiscountPrice").description("최대 할인 가격"),
                    fieldWithPath("basicIssuedDate").description("기본 발급 날짜"),
                    fieldWithPath("basicExpiredDate").description("기본 만료 날짜"),
                    fieldWithPath("validity").description("유효 기간"),
                    fieldWithPath("couponTypeName").description("쿠폰 타입"),
                    fieldWithPath("isBirth").description("생일 쿠폰 여부"),
                    fieldWithPath("couponStatusName").description("쿠폰 상태"),
                    fieldWithPath("categories").description("관련 카테고리 목록"),
                    fieldWithPath("books.[].bookId").description("관련 책 id"),
                    fieldWithPath("books.[].bookTitle").description("관련 책 이름")
                )
            ));
    }
}