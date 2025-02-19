package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.coupon.BirthDayCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.BookCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CategoryCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.MemberCouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.OrderCouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.MemberCouponStatusEnum;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.AlreadyUsedException;
import com.nhnacademy.inkbridge.backend.exception.InvalidPeriodException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: CouponService.
 *
 * @author JBum
 * @version 2024/02/15
 */
public interface CouponService {

    /**
     * 관리자가 쿠폰을 생성하는 메소드. UUID에 randomUUID기능을 사용하여 난수의 중복을 방지한다.
     *
     * @param couponCreateRequestDto 쿠폰을 생성하기 위한 Request DTO
     * @throws NotFoundException    입력된 쿠폰 타입이 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 카테고리가 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 책이 존재하지 않는 경우 예외 발생
     * @throws AlreadyUsedException 존재하지 않는 쿠폰타입이면 예외 발생
     */
    void createCoupon(CouponCreateRequestDto couponCreateRequestDto);

    /**
     * 사용자가 쿠폰을 등록하는 메소드. UUID에 randomUUID기능을 사용하여 난수의 중복을 방지한다.
     *
     * @param memberId 쿠폰을 등록하는 유저
     * @param couponId 등록할 쿠폰
     * @throws NotFoundException      존재하지 않는 쿠폰이 입력된 경우 예외 발생
     * @throws AlreadyExistException  이미 등록된 쿠폰인 경우 예외 발생
     * @throws InvalidPeriodException 쿠폰 발급이 가능한 날짜가 아닌 경우 예외 발생
     */
    void issueCoupon(Long memberId, String couponId);

    /**
     * 관리자용 쿠폰리스트를 보여주는 메소드.
     *
     * @param pageable       페이지
     * @param couponStatusId 쿠폰상태별로 보기 위한 파라미터
     * @return 쿠폰리스트
     * @throws NotFoundException 존재하는 쿠폰상태가 아닌 경우 예외 발생
     */
    Page<CouponReadResponseDto> adminViewCoupons(Pageable pageable, int couponStatusId);

    /**
     * 관리자가 책전용 쿠폰을 생성하는 메소드. UUID에 randomUUID기능을 사용하여 난수의 중복을 방지한다.
     *
     * @param bookCouponCreateRequestDto 책전용 쿠폰을 생성하기 위한 Request DTO
     * @throws NotFoundException    입력된 쿠폰 타입이 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 카테고리가 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 책이 존재하지 않는 경우 예외 발생
     * @throws AlreadyUsedException 존재하지 않는 쿠폰타입이면 예외 발생
     */
    void createBookCoupon(BookCouponCreateRequestDto bookCouponCreateRequestDto);

    /**
     * 관리자가 카테고리전용 쿠폰을 생성하는 메소드. UUID에 randomUUID기능을 사용하여 난수의 중복을 방지한다.
     *
     * @param categoryCouponCreateRequestDto 쿠폰전용 쿠폰을 생성하기 위한 Request DTO
     * @throws NotFoundException    입력된 쿠폰 타입이 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 카테고리가 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 책이 존재하지 않는 경우 예외 발생
     * @throws AlreadyUsedException 존재하지 않는 쿠폰타입이면 예외 발생
     */
    void createCategoryCoupon(
        CategoryCouponCreateRequestDto categoryCouponCreateRequestDto);

    List<OrderCouponReadResponseDto> getOrderCouponList(Long[] bookId, Long memberId);

    /**
     * 사용자가 가진 쿠폰을 조회하는 메소드. 이 때 used를 이용해서 사용햇는쿠폰을 조회할 것인지 미사용쿠폰을 조회할 것인지 정한다.
     *
     * @param memberId 조회할 사용자Id
     * @param used     사용여부
     * @return 사용자가 가진 쿠폰
     */
    Page<MemberCouponReadResponseDto> getMemberCouponList(Long memberId,
        MemberCouponStatusEnum used, Pageable pageable);

    /**
     * 발급가능한 쿠폰의 목록을 보여주는 메소드.
     *
     * @param pageable 페이지
     * @return 발급가능한 쿠폰 목록.
     */
    Page<CouponReadResponseDto> getIssuableCoupons(Pageable pageable);

    /**
     * 쿠폰의 상세한 정보를 찾아주는 메소드.
     *
     * @param couponId 쿠폰ID
     * @return 쿠폰의 상세정보, 카테고리, 책등 연관관계를 보여줌
     */
    CouponDetailReadResponseDto getDetailCoupon(String couponId);

    /**
     * 쿠폰 사용처리 하는 메소드.
     *
     * @param memberId       사용한 멤버
     * @param memberCouponId 사용할 쿠폰 목록들
     */
    void useCoupons(Long memberId, List<Long> memberCouponId);

    /**
     * 쿠폰 사용을 취소하는 메소드.
     *
     * @param memberId        취소할 멤버id
     * @param memberCouponIds 취소할 쿠폰 목록들
     */
    void cancelCouponUsage(Long memberId, List<Long> memberCouponIds);

    /**
     * 생일 쿠폰을 생성할때 사용하는 메소드.
     *
     * @param birthDayCouponCreateRequestDto 생일 쿠폰 정보
     */
    void createBirthdayCoupon(BirthDayCouponCreateRequestDto birthDayCouponCreateRequestDto);
}
