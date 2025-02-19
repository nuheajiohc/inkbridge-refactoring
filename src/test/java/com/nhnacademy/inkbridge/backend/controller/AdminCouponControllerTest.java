package com.nhnacademy.inkbridge.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.inkbridge.backend.dto.coupon.BirthDayCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.BookCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CategoryCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.CouponStatus;
import com.nhnacademy.inkbridge.backend.entity.CouponType;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: AdminCouponControllerTest.
 *
 * @author JBum
 * @version 2024/03/24
 */
@AutoConfigureRestDocs
@WebMvcTest(AdminCouponController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class AdminCouponControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CouponService couponService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("관리자 쿠폰 리스트")
    void testGetCoupons() throws Exception {
        CouponReadResponseDto couponReadResponseDto = new CouponReadResponseDto("1", "테스트쿠폰",
            10000L, 1000L,
            1000L, LocalDate.now(), LocalDate.now(), 30, new CouponType(2, "가격쿠폰"), false,
            new CouponStatus(1, "보통"));
        Page<CouponReadResponseDto> responseDtos = new PageImpl<>(List.of(couponReadResponseDto));

        given(couponService.adminViewCoupons(any(Pageable.class), eq(1))).willReturn(
            responseDtos);

        mockMvc.perform(get("/api/admin/coupons").param("coupon-status-id", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].couponName").value("테스트쿠폰"))
            .andExpect(jsonPath("$.content[0].minPrice").value(10000))
            .andExpect(jsonPath("$.content[0].discountPrice").value(1000))
            .andExpect(jsonPath("$.content[0].maxDiscountPrice").value(1000))
            .andExpect(jsonPath("$.content[0].basicIssuedDate").exists())
            .andExpect(jsonPath("$.content[0].basicExpiredDate").exists())
            .andExpect(jsonPath("$.content[0].validity").value(30))
            .andExpect(jsonPath("$.content[0].couponTypeId").value(2))
            .andExpect(jsonPath("$.content[0].isBirth").value(false))
            .andExpect(jsonPath("$.content[0].couponStatusId").value(1))
            .andExpect(jsonPath("$.pageable").value("INSTANCE"))
            .andExpect(jsonPath("$.last").value(true))
            .andExpect(jsonPath("$.totalElements").value(1))
            .andExpect(jsonPath("$.totalPages").value(1))
            .andExpect(jsonPath("$.number").value(0))
            .andExpect(jsonPath("$.first").value(true))
            .andExpect(jsonPath("$.size").value(1))
            .andExpect(jsonPath("$.numberOfElements").value(1))
            .andExpect(jsonPath("$.empty").value(false))
            .andExpect(jsonPath("$.sort.empty").value(true))
            .andExpect(jsonPath("$.sort.unsorted").value(true))
            .andExpect(jsonPath("$.sort.sorted").value(false))
            .andDo(document("admin/coupons/get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("content[0].couponId").description("쿠폰 ID"),
                    fieldWithPath("content[0].couponName").description("쿠폰 이름"),
                    fieldWithPath("content[0].discountPrice").description("할인 금액"),
                    fieldWithPath("content[0].maxDiscountPrice").description("최대 할인 금액"),
                    fieldWithPath("content[0].minPrice").description("최소 주문 금액"),
                    fieldWithPath("content[0].basicIssuedDate").description("발급일"),
                    fieldWithPath("content[0].basicExpiredDate").description("만료일"),
                    fieldWithPath("content[0].validity").description("유효 기간"),
                    fieldWithPath("content[0].couponTypeId").description("쿠폰 유형 ID"),
                    fieldWithPath("content[0].isBirth").description("생일 쿠폰 여부"),
                    fieldWithPath("content[0].couponStatusId").description("쿠폰 상태 ID"),
                    fieldWithPath("pageable").description("페이지 정보"),
                    fieldWithPath("last").description("마지막 페이지 여부"),
                    fieldWithPath("totalPages").description("전체 페이지 수"),
                    fieldWithPath("totalElements").description("전체 요소 수"),
                    fieldWithPath("first").description("첫 페이지 여부"),
                    fieldWithPath("number").description("현재 페이지 번호 (0부터 시작)"),
                    fieldWithPath("size").description("페이지 크기"),
                    fieldWithPath("numberOfElements").description("현재 페이지 요소 수"),
                    fieldWithPath("empty").description("비어 있는지 여부"),
                    fieldWithPath("sort.empty").description("정렬 여부: 비어 있음"),
                    fieldWithPath("sort.sorted").description("정렬 여부: 정렬됨"),
                    fieldWithPath("sort.unsorted").description("정렬 여부: 정렬되지 않음")
                )));
        verify(couponService, times(1)).adminViewCoupons(any(Pageable.class), eq(1));
    }

    @Test
    @DisplayName("책쿠폰 생성 성공")
    void testCreateBookCoupon() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        BookCouponCreateRequestDto requestDto = BookCouponCreateRequestDto.builder()
            .couponName("Test Coupon")
            .minPrice(10000L)
            .maxDiscountPrice(5000L)
            .discountPrice(2000L)
            .basicIssuedDate(LocalDate.now())
            .basicExpiredDate(LocalDate.now().plusDays(30))
            .validity(30)
            .couponTypeId(1)
            .bookId(12345L)
            .build();
        doNothing().when(couponService).createBookCoupon(any());

        mockMvc.perform(post("/api/admin/coupons/book-coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andDo(document("admin/coupons/book-coupons",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
            ));
    }


    @Test
    @DisplayName("책쿠폰 생성 실패")
    void testCreateBookCoupon_ValidationException() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        BookCouponCreateRequestDto requestDto = BookCouponCreateRequestDto.builder()
            .couponName("asd")
            .minPrice(10000L)
            .maxDiscountPrice(5000L)
            .discountPrice(2000L)
            .basicIssuedDate(LocalDate.now().minusDays(30))
            .basicExpiredDate(LocalDate.now().plusDays(30))
            .validity(30)
            .couponTypeId(1)
            .bookId(12345L)
            .build();
        doNothing().when(couponService).createBookCoupon(any());

        mockMvc.perform(post("/api/admin/coupons/book-coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("admin/coupons/book-coupons-fail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
            ));
    }

    @Test
    @DisplayName("카테고리 쿠폰 생성 성공")
    void testCreateCategoryCoupon() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        CategoryCouponCreateRequestDto requestDto = CategoryCouponCreateRequestDto.builder()
            .couponName("Test Coupon")
            .minPrice(10000L)
            .maxDiscountPrice(5000L)
            .discountPrice(2000L)
            .basicIssuedDate(LocalDate.now())
            .basicExpiredDate(LocalDate.now().plusDays(30))
            .validity(30)
            .couponTypeId(1)
            .categoryId(12345L)
            .build();
        doNothing().when(couponService).createCategoryCoupon(any());

        mockMvc.perform(post("/api/admin/coupons/category-coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andDo(document("admin/coupons/category-coupons",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
            ));
    }


    @Test
    @DisplayName("카테고리 쿠폰 생성 실패")
    void testCreateCategoryCoupon_ValidationException() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        CategoryCouponCreateRequestDto requestDto = CategoryCouponCreateRequestDto.builder()
            .couponName("Test Coupon")
            .minPrice(10000L)
            .maxDiscountPrice(5000L)
            .discountPrice(2000L)
            .basicIssuedDate(LocalDate.now().minusDays(30))
            .basicExpiredDate(LocalDate.now().plusDays(30))
            .validity(30)
            .couponTypeId(1)
            .categoryId(12345L)
            .build();
        doNothing().when(couponService).createCategoryCoupon(any());

        mockMvc.perform(post("/api/admin/coupons/category-coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("admin/coupons/category-coupons-fail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
            ));
    }

    @Test
    @DisplayName("쿠폰 생성 성공")
    void testCreateCoupon() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        CouponCreateRequestDto requestDto = CouponCreateRequestDto.builder()
            .couponName("Test Coupon")
            .minPrice(10000L)
            .maxDiscountPrice(5000L)
            .discountPrice(2000L)
            .basicIssuedDate(LocalDate.now())
            .basicExpiredDate(LocalDate.now().plusDays(30))
            .validity(30)
            .couponTypeId(1)
            .build();
        doNothing().when(couponService).createCoupon(any());

        mockMvc.perform(post("/api/admin/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andDo(document("admin/coupons",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
            ));
    }


    @Test
    @DisplayName("쿠폰 생성 실패")
    void testCreateCoupon_ValidationException() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        CouponCreateRequestDto requestDto = CouponCreateRequestDto.builder()
            .couponName("Test Coupon")
            .minPrice(10000L)
            .maxDiscountPrice(5000L)
            .discountPrice(2000L)
            .basicIssuedDate(LocalDate.now().minusDays(30))
            .basicExpiredDate(LocalDate.now().plusDays(30))
            .validity(30)
            .couponTypeId(1)
            .build();
        doNothing().when(couponService).createCoupon(any());

        mockMvc.perform(post("/api/admin/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("admin/coupons-fail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
            ));
    }

    @Test
    @DisplayName("생일 쿠폰 생성 성공")
    void testCreateBirthdayCoupon() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        BirthDayCouponCreateRequestDto requestDto = BirthDayCouponCreateRequestDto.builder()
            .couponName("Test Coupon")
            .minPrice(10000L)
            .maxDiscountPrice(5000L)
            .discountPrice(2000L)
            .month(4)
            .build();

        doNothing().when(couponService).createBirthdayCoupon(any());
        mockMvc.perform(post("/api/admin/coupons/birthday-coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andDo(document("admin/coupons/birthday-coupons",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
            ));
    }


    @Test
    @DisplayName("생일 쿠폰 생성 실패")
    void testCreateBirthdayCoupon_ValidationException() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        BirthDayCouponCreateRequestDto requestDto = BirthDayCouponCreateRequestDto.builder()
            .couponName("Test Coupon")
            .minPrice(10000L)
            .maxDiscountPrice(5000L)
            .discountPrice(2000L)
            .month(0)
            .build();
        doNothing().when(couponService).createBirthdayCoupon(any());

        mockMvc.perform(post("/api/admin/coupons/birthday-coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("admin/coupons/birthday-coupons-fail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
            ));
    }

}