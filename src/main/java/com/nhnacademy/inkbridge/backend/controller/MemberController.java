package com.nhnacademy.inkbridge.backend.controller;

import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_TYPE_NOT_FOUND;

import com.nhnacademy.inkbridge.backend.annotation.Auth;
import com.nhnacademy.inkbridge.backend.dto.coupon.MemberCouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.OrderCouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberAuthLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberEmailRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberIdNoRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberInfoResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.BookOrderDetailResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.MemberCouponStatusEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.facade.MemberFacade;
import com.nhnacademy.inkbridge.backend.facade.OrderFacade;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import com.nhnacademy.inkbridge.backend.service.MemberService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: MemberController.
 *
 * @author minseo
 * @version 2/15/24
 * @modifiedBy JBum
 * @modifiedAt 3/7/24
 * @modificationReason - getOrderCoupons 추가, getMemberCoupons 추가
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final CouponService couponService;
    private final OrderFacade orderFacade;
    private final MemberFacade memberFacade;

    /**
     * 회원가입 하는 메서드입니다.
     *
     * @param memberCreateRequestDto 회원가입 폼 데이터
     * @return 회원가입 성공
     */
    @PostMapping("/members")
    public ResponseEntity<Void> create(
        @RequestBody @Valid MemberCreateRequestDto memberCreateRequestDto) {

        memberFacade.signupFacade(memberCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 로그인 인증 절차에 필요한 정보를 가져오는 메서드입니다.
     *
     * @param memberAuthLoginRequestDto 정보를 가져올 이메일
     * @return 로그인에 필요한 정보
     */
    @PostMapping("/members/login")
    public ResponseEntity<MemberAuthLoginResponseDto> authLogin(
        @RequestBody @Valid MemberAuthLoginRequestDto memberAuthLoginRequestDto) {

        MemberAuthLoginResponseDto memberAuthLoginResponseDto =
            memberService.loginInfoMember(memberAuthLoginRequestDto);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
            .body(memberAuthLoginResponseDto);
    }

    /**
     * 이메일 중복확인하는 메서드입니다.
     *
     * @param memberEmailRequestDto 이메일 정보
     * @return 중복 여부
     */
    @PostMapping("/members/checkEmail")
    public ResponseEntity<Boolean> isDuplicatedEmail(
        @Valid @RequestBody MemberEmailRequestDto memberEmailRequestDto) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
            .body(memberService.checkDuplicatedEmail(memberEmailRequestDto.getEmail()));
    }

    /**
     * 회원 정보를 가져오는 메서드입니다.
     *
     * @param request request
     * @return 회원 정보
     */
    @GetMapping("/auth/info")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(HttpServletRequest request) {

        Long memberId = Long.parseLong(request.getHeader("Authorization-Id"));

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
            .body(memberService.getMemberInfo(memberId));
    }

    /**
     * 소셜 회원인지 아닌지 체크하는 메서드
     *
     * @param memberIdNoRequestDto 멤버 아이디
     * @return 결과값
     */
    @PostMapping("/oauth/check")
    public ResponseEntity<Boolean> oauthMemberCheck(
        @Valid @RequestBody MemberIdNoRequestDto memberIdNoRequestDto) {
        boolean result = memberService.checkOAuthMember(memberIdNoRequestDto.getId());

        return ResponseEntity.ok(result);
    }

    /**
     * 소셜 회원의 이메일을 가져오는 메서드입니다.
     *
     * @param memberIdNoRequestDto 아이디
     * @return 이메일
     */
    @PostMapping("/oauth")
    public ResponseEntity<String> getOAuthEmail(
        @Valid @RequestBody MemberIdNoRequestDto memberIdNoRequestDto) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
            .body(memberService.getOAuthMemberEmail(memberIdNoRequestDto.getId()));
    }

    @Auth
    @GetMapping("/auth/members/{memberId}/order-coupons")
    public ResponseEntity<List<OrderCouponReadResponseDto>> getOrderCoupons(
        @PathVariable("memberId") Long memberId, @RequestParam("book-id") Long[] bookId) {
        return ResponseEntity.ok(couponService.getOrderCouponList(bookId, memberId));
    }

    @Auth
    @GetMapping("/auth/members/{memberId}/coupons")
    public ResponseEntity<Page<MemberCouponReadResponseDto>> getMemberCoupons(
        @PathVariable("memberId") Long memberId, Pageable pageable,
        @RequestParam(value = "status", defaultValue = "ACTIVE") String status) {
        MemberCouponStatusEnum statusEnum;
        try {
            statusEnum = MemberCouponStatusEnum.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new NotFoundException(COUPON_TYPE_NOT_FOUND.getMessage());
        }
        return ResponseEntity.ok(
            couponService.getMemberCouponList(memberId, statusEnum, pageable
            ));
    }

    @Auth
    @PostMapping("/auth/members/{memberId}/coupons/{couponId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void issueCoupon(@PathVariable("memberId") Long memberId,
        @PathVariable("couponId") String couponId) {
        couponService.issueCoupon(memberId, couponId);
    }

    /**
     * 회원의 주문 목록을 조회하는 메소드입니다.
     *
     * @return 페이지에 맞는 주문 목록
     */
    @Auth
    @GetMapping("/auth/members/{memberId}/orders")
    public ResponseEntity<Page<OrderReadResponseDto>> getOrder(@PathVariable Long memberId,
        @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(orderFacade.getOrderListByMemberId(memberId, pageable));
    }

    /**
     * 회원의 주문 상세 내역을 조회하는 메소드입니다.
     *
     * @return 주문 상세 내역
     */
    @Auth
    @GetMapping("/auth/members/{memberId}/orders/{orderCode}")
    public ResponseEntity<BookOrderDetailResponseDto> getOrder(
        @PathVariable("memberId") Long memberId, @PathVariable("orderCode") String orderCode) {
        return ResponseEntity.ok(orderFacade.getOrderDetailByOrderCode(orderCode));
    }
}
