package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoriesDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.BirthDayCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.BookCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CategoryCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.MemberCouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookCategory;
import com.nhnacademy.inkbridge.backend.entity.BookCoupon;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.entity.CategoryCoupon;
import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.CouponStatus;
import com.nhnacademy.inkbridge.backend.entity.CouponType;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import com.nhnacademy.inkbridge.backend.enums.MemberCouponStatusEnum;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.AlreadyUsedException;
import com.nhnacademy.inkbridge.backend.exception.InvalidPeriodException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookCategoryRepository;
import com.nhnacademy.inkbridge.backend.repository.BookCouponRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.CategoryCouponRepository;
import com.nhnacademy.inkbridge.backend.repository.CategoryRepository;
import com.nhnacademy.inkbridge.backend.repository.CouponRepository;
import com.nhnacademy.inkbridge.backend.repository.CouponStatusRepository;
import com.nhnacademy.inkbridge.backend.repository.CouponTypeRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberCouponRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * class: CouponServiceImplTest.
 *
 * @author JBum
 * @version 2024/02/19
 */

@ExtendWith(MockitoExtension.class)
class CouponServiceImplTest {

    @InjectMocks
    private CouponServiceImpl couponService;

    @Mock
    private CouponRepository couponRepository;
    @Mock
    private CouponTypeRepository couponTypeRepository;
    @Mock
    private CouponStatusRepository couponStatusRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberCouponRepository memberCouponRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookCategoryRepository bookCategoryRepository;
    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryCouponRepository categoryCouponRepository;

    @Mock
    private BookCouponRepository bookCouponRepository;

    @Test
    @DisplayName("정상적인쿠폰 생성")
    void testCreateCoupon_Coupon_Create_Success() {
        CouponCreateRequestDto couponCreateRequestDto = CouponCreateRequestDto.builder()
            .couponTypeId(1)
            .couponName("테스트 쿠폰")
            .basicIssuedDate(LocalDate.now())
            .basicExpiredDate(LocalDate.now().plusDays(30))
            .discountPrice(1000L)
            .maxDiscountPrice(5000L)
            .minPrice(10000L)
            .validity(7)
            .build();

        CouponType couponType = CouponType.builder()
            .couponTypeId(1)
            .build();
        when(couponTypeRepository.findById(1)).thenReturn(Optional.of(couponType));

        CouponStatus couponStatus = CouponStatus.builder()
            .couponStatusId(1)
            .build();
        when(couponStatusRepository.findById(any())).thenReturn(Optional.of(couponStatus));

        couponService.createCoupon(couponCreateRequestDto);

        verify(couponRepository, times(1)).save(any());
    }


    @Test
    @DisplayName("쿠폰 생성 실패: 유효하지 않은 유효기간")
    void testCreateCoupon_Fail_InvalidPeriod() {
        CouponCreateRequestDto couponCreateRequestDto = CouponCreateRequestDto.builder()
            .couponTypeId(1)
            .basicIssuedDate(LocalDate.now().minusDays(1))
            .build();
        when(couponTypeRepository.findById(1)).thenReturn(
            Optional.of(CouponType.builder().build()));

        assertThrows(NotFoundException.class,
            () -> couponService.createCoupon(couponCreateRequestDto));
        verify(couponRepository, never()).saveAndFlush(any());
    }

    @DisplayName("쿠폰 생성 실패: 유효하지 않은 쿠폰 타입")
    @Test
    void testCreateCoupon_Fail_InvalidCouponType() {
        CouponCreateRequestDto couponCreateRequestDto = CouponCreateRequestDto.builder()
            .couponTypeId(1)
            .build();
        when(couponTypeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> couponService.createCoupon(couponCreateRequestDto));
        verify(couponRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("쿠폰 생성 실패: 유효하지 않은 쿠폰 상태")
    void testFindCouponStatus_Fail() {
        CouponType couponType = mock(CouponType.class);

        CouponCreateRequestDto couponCreateRequestDto = CouponCreateRequestDto.builder()
            .couponTypeId(6)
            .couponName("테스트 쿠폰")
            .basicIssuedDate(LocalDate.now())
            .basicExpiredDate(LocalDate.now())
            .discountPrice(1000L)
            .maxDiscountPrice(5000L)
            .minPrice(10000L)
            .validity(7)
            .build();

        when(couponTypeRepository.findById(any())).thenReturn(Optional.of(couponType));

        assertThrows(NotFoundException.class, () -> {
            couponService.createCoupon(couponCreateRequestDto);
        });
        verify(couponRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("쿠폰 생성 실패: 쿠폰 상태(어제)")
    void testFindCouponStatusByIssuedDate_BeforeToday() {
        CouponType couponType = mock(CouponType.class);

        CouponCreateRequestDto couponCreateRequestDto = CouponCreateRequestDto.builder()
            .couponTypeId(1)
            .couponName("테스트 쿠폰")
            .basicIssuedDate(LocalDate.now().minusDays(1))
            .basicExpiredDate(LocalDate.now().minusDays(30))
            .discountPrice(1000L)
            .maxDiscountPrice(5000L)
            .minPrice(10000L)
            .validity(7)
            .build();

        when(couponTypeRepository.findById(any())).thenReturn(Optional.of(couponType));

        assertThrows(NotFoundException.class, () -> {
            couponService.createCoupon(couponCreateRequestDto);
        });
        verify(couponRepository, never()).saveAndFlush(any());

    }

    @Test
    @DisplayName("카테고리 쿠폰 생성 성공")
    void createCategoryCoupon() {
        CategoryCouponCreateRequestDto categoryCouponCreateRequestDto = CategoryCouponCreateRequestDto.builder()
            .couponName("Test Coupon")
            .couponTypeId(1)
            .basicIssuedDate(LocalDate.now())
            .basicExpiredDate(LocalDate.now().plusDays(7))
            .discountPrice(10L)
            .maxDiscountPrice(100L)
            .minPrice(50L)
            .validity(30)
            .categoryId(1L)
            .build();
        CouponType couponType = mock(CouponType.class);
        CouponStatus couponStatus = mock(CouponStatus.class);
        Category category = mock(Category.class);
        CategoryCoupon categoryCoupon = mock(CategoryCoupon.class);
        when(couponTypeRepository.findById(any())).thenReturn(Optional.of(couponType));
        when(couponStatusRepository.findById(any())).thenReturn(Optional.of(couponStatus));
        when(categoryRepository.findById(any())).thenReturn(Optional.of(category));
        when(categoryCouponRepository.save(any())).thenReturn(categoryCoupon);
        couponService.createCategoryCoupon(categoryCouponCreateRequestDto);

        verify(couponRepository, times(1)).save(any(Coupon.class));
        verify(couponTypeRepository, times(1)).findById(anyInt());
        verify(couponStatusRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("카테고리 쿠폰생성 실패")
    void createCategoryCoupon_Fail() {
        CategoryCouponCreateRequestDto categoryCouponCreateRequestDto = CategoryCouponCreateRequestDto.builder()
            .couponName("Test Coupon")
            .couponTypeId(1)
            .basicIssuedDate(LocalDate.now())
            .basicExpiredDate(LocalDate.now().plusDays(7))
            .discountPrice(10L)
            .maxDiscountPrice(100L)
            .minPrice(50L)
            .validity(30)
            .categoryId(1L)
            .build();
        CouponType couponType = mock(CouponType.class);
        CouponStatus couponStatus = mock(CouponStatus.class);

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        when(couponTypeRepository.findById(any())).thenReturn(Optional.of(couponType));
        when(couponStatusRepository.findById(any())).thenReturn(Optional.of(couponStatus));

        assertThrows(NotFoundException.class, () -> {
            couponService.createCategoryCoupon(categoryCouponCreateRequestDto);
        });

        verify(categoryRepository, times(1)).findById(1L);

        verify(couponRepository, times(1)).save(any());
        verify(couponRepository, never()).saveAndFlush(any());

    }

    @Test
    @DisplayName("책 쿠폰생성 성공")
    void createBookCoupon() {
        BookCouponCreateRequestDto bookCouponCreateRequestDto = BookCouponCreateRequestDto.builder()
            .couponName("Test Coupon")
            .couponTypeId(1)
            .basicIssuedDate(LocalDate.now())
            .basicExpiredDate(LocalDate.now().plusDays(7))
            .discountPrice(10L)
            .maxDiscountPrice(100L)
            .minPrice(50L)
            .validity(30)
            .bookId(1L)
            .build();
        CouponType couponType = mock(CouponType.class);
        CouponStatus couponStatus = mock(CouponStatus.class);
        Book book = mock(Book.class);
        BookCoupon bookCoupon = mock(BookCoupon.class);
        when(couponTypeRepository.findById(any())).thenReturn(Optional.of(couponType));
        when(couponStatusRepository.findById(any())).thenReturn(Optional.of(couponStatus));
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(bookCouponRepository.save(any())).thenReturn(bookCoupon);
        couponService.createBookCoupon(bookCouponCreateRequestDto);

        verify(couponRepository, times(1)).saveAndFlush(any(Coupon.class));
        verify(couponTypeRepository, times(1)).findById(anyInt());
        verify(couponStatusRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("책 쿠폰생성 실패")
    void createBookCoupon_Fail() {
        BookCouponCreateRequestDto bookCouponCreateRequestDto = BookCouponCreateRequestDto.builder()
            .couponName("Test Coupon")
            .couponTypeId(1)
            .basicIssuedDate(LocalDate.now())
            .basicExpiredDate(LocalDate.now().plusDays(7))
            .discountPrice(10L)
            .maxDiscountPrice(100L)
            .minPrice(50L)
            .validity(30)
            .bookId(1L)
            .build();
        CouponType couponType = mock(CouponType.class);
        CouponStatus couponStatus = mock(CouponStatus.class);

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        when(couponTypeRepository.findById(any())).thenReturn(Optional.of(couponType));
        when(couponStatusRepository.findById(any())).thenReturn(Optional.of(couponStatus));

        assertThrows(NotFoundException.class, () -> {
            couponService.createBookCoupon(bookCouponCreateRequestDto);
        });

        verify(bookRepository, times(1)).findById(1L);

        verify(couponRepository, times(1)).saveAndFlush(any());
        verify(couponRepository, never()).save(any());

    }

    @DisplayName("쿠폰발급 성공")
    @Test
    void testIssueCoupon() {
        Coupon coupon = mock(Coupon.class);
        Member member = mock(Member.class);
        MemberCoupon issueCouponDto = mock(MemberCoupon.class);

        LocalDate startDate = LocalDate.now().plusDays(0);
        LocalDate endDate = LocalDate.now().plusDays(0);

        when(coupon.getValidity()).thenReturn(30);
        when(coupon.getBasicIssuedDate()).thenReturn(startDate);
        when(coupon.getBasicExpiredDate()).thenReturn(endDate);
        when(coupon.getCouponId()).thenReturn("asd");
        when(member.getMemberId()).thenReturn(1L);

        when(issueCouponDto.getCoupon()).thenReturn(coupon);
        when(issueCouponDto.getMember()).thenReturn(member);

        when(couponRepository.findById("asd")).thenReturn(Optional.of(coupon));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        couponService.issueCoupon(issueCouponDto.getMember().getMemberId(),
            issueCouponDto.getCoupon().getCouponId());

        verify(memberCouponRepository, times(1)).saveAndFlush(any(MemberCoupon.class));
    }

    @DisplayName("쿠폰발급 실패: 찾을수 없는 쿠폰")
    @Test
    void testIssueCoupon_NotFoundCoupon() {
        Coupon coupon = mock(Coupon.class);
        Member member = mock(Member.class);
        MemberCoupon issueCouponDto = mock(MemberCoupon.class);

        when(coupon.getCouponId()).thenReturn("asd");
        when(member.getMemberId()).thenReturn(1L);

        when(issueCouponDto.getCoupon()).thenReturn(coupon);
        when(issueCouponDto.getMember()).thenReturn(member);
        Long memberId = issueCouponDto.getMember().getMemberId();
        String couponId = issueCouponDto.getCoupon().getCouponId();
        when(couponRepository.findById("asd")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            couponService.issueCoupon(memberId, couponId
            );
        });
        verify(couponRepository, times(1)).findById("asd");
        verify(memberRepository, times(0)).findById(1L);
        verify(memberCouponRepository, times(0)).existsByCouponAndMember(coupon, member);
    }


    @DisplayName("쿠폰발급 실패 찾을수 없는 멤버")
    @Test
    void testIssueCoupon_NorFoundMember() {
        Coupon coupon = mock(Coupon.class);
        Member member = mock(Member.class);
        MemberCoupon issueCouponDto = mock(MemberCoupon.class);

        when(coupon.getCouponId()).thenReturn("asd");
        when(member.getMemberId()).thenReturn(1L);

        when(issueCouponDto.getCoupon()).thenReturn(coupon);
        when(issueCouponDto.getMember()).thenReturn(member);
        Long memberId = issueCouponDto.getMember().getMemberId();
        String couponId = issueCouponDto.getCoupon().getCouponId();
        when(couponRepository.findById("asd")).thenReturn(Optional.of(coupon));
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            couponService.issueCoupon(memberId, couponId);
        });
        verify(couponRepository, times(1)).findById("asd");
        verify(memberRepository, times(1)).findById(1L);
        verify(memberCouponRepository, times(0)).existsByCouponAndMember(coupon, member);
    }

    @DisplayName("쿠폰발급 실패: 이미 사용한 쿠폰")
    @Test
    void testIssueCoupon_AlreadyExistExceptionMemberCoupon() {
        Coupon coupon = mock(Coupon.class);
        Member member = mock(Member.class);
        MemberCoupon issueCouponDto = mock(MemberCoupon.class);

        LocalDate startDate = LocalDate.now().minusDays(1);

        LocalDate endDate = LocalDate.now().plusDays(30);

        when(coupon.getCouponId()).thenReturn("asd");
        when(member.getMemberId()).thenReturn(1L);
        when(coupon.getBasicIssuedDate()).thenReturn(startDate);
        when(coupon.getBasicExpiredDate()).thenReturn(endDate);
        when(issueCouponDto.getCoupon()).thenReturn(coupon);
        when(issueCouponDto.getMember()).thenReturn(member);

        when(couponRepository.findById("asd")).thenReturn(Optional.of(coupon));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberCouponRepository.existsByCouponAndMember(coupon, member)).thenReturn(true);
        Long memberId = issueCouponDto.getMember().getMemberId();
        String couponId = issueCouponDto.getCoupon().getCouponId();
        assertThrows(AlreadyExistException.class, () -> {
            couponService.issueCoupon(memberId, couponId);
        });
        verify(couponRepository, times(1)).findById("asd");
        verify(memberRepository, times(1)).findById(1L);
        verify(memberCouponRepository, times(1)).existsByCouponAndMember(coupon, member);
    }

    @DisplayName("쿠폰발급 실패: 아직 발급을 시작하지 않은 쿠폰")
    @Test
    void testIssueCoupon_InvalidPeriodExceptionStartDate() {
        Coupon coupon = mock(Coupon.class);
        Member member = mock(Member.class);
        MemberCoupon issueCouponDto = mock(MemberCoupon.class);

        LocalDate startDate = LocalDate.now().plusDays(30);

        LocalDate endDate = LocalDate.now().plusDays(30);

        when(coupon.getCouponId()).thenReturn("asd");
        when(member.getMemberId()).thenReturn(1L);
        when(coupon.getBasicIssuedDate()).thenReturn(startDate);
        when(coupon.getBasicExpiredDate()).thenReturn(endDate);
        when(issueCouponDto.getCoupon()).thenReturn(coupon);
        when(issueCouponDto.getMember()).thenReturn(member);
        Long memberId = issueCouponDto.getMember().getMemberId();
        String couponId = issueCouponDto.getCoupon().getCouponId();
        when(couponRepository.findById("asd")).thenReturn(Optional.of(coupon));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        assertThrows(InvalidPeriodException.class, () -> {
            couponService.issueCoupon(memberId, couponId);
        });
        verify(couponRepository, times(1)).findById("asd");
        verify(memberRepository, times(1)).findById(1L);
        verify(memberCouponRepository, times(0)).existsByCouponAndMember(coupon, member);
    }

    @DisplayName("쿠폰발급 실패: 이미 발급기간이 지난 쿠폰")
    @Test
    void testIssueCoupon_InvalidPeriodExceptionEndDate() {
        Coupon coupon = mock(Coupon.class);
        Member member = mock(Member.class);
        MemberCoupon issueCouponDto = mock(MemberCoupon.class);

        LocalDate startDate = LocalDate.now().plusDays(30);

        LocalDate endDate = LocalDate.now().minusDays(30);

        when(coupon.getCouponId()).thenReturn("asd");
        when(member.getMemberId()).thenReturn(1L);
        when(coupon.getBasicIssuedDate()).thenReturn(startDate);
        when(coupon.getBasicExpiredDate()).thenReturn(endDate);
        when(issueCouponDto.getCoupon()).thenReturn(coupon);
        when(issueCouponDto.getMember()).thenReturn(member);
        Long memberId = issueCouponDto.getMember().getMemberId();
        String couponId = issueCouponDto.getCoupon().getCouponId();
        when(couponRepository.findById("asd")).thenReturn(Optional.of(coupon));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        assertThrows(InvalidPeriodException.class, () -> {
            couponService.issueCoupon(memberId, couponId);
        });
        verify(couponRepository, times(1)).findById("asd");
        verify(memberRepository, times(1)).findById(1L);
        verify(memberCouponRepository, times(0)).existsByCouponAndMember(coupon, member);
    }

    @DisplayName("어드민이 볼 수 있는 쿠폰 관리테스트")
    @Test
    void testAdminViewCoupons() {
        int couponStatusId = 1;
        Pageable pageable = mock(Pageable.class);
        Page<CouponReadResponseDto> page = mock(Page.class);
        CouponStatus couponStatus = mock(CouponStatus.class);
        when(couponStatusRepository.findById(couponStatusId)).thenReturn(Optional.of(couponStatus));
        when(couponRepository.findByCouponStatus(any(CouponStatus.class),
            any(Pageable.class))).thenReturn(page);

        couponService.adminViewCoupons(pageable, couponStatusId);

        verify(couponRepository, times(1)).findByCouponStatus(any(CouponStatus.class),
            any(Pageable.class));
    }

    @DisplayName("주문쿠폰리스트 성공")
    @Test
    void testGetOrderCouponList() {
        Long[] bookIds = {1L, 2L, 3L};
        Long memberId = 1L;
        BookCategoriesDto bookCategoriesDto = mock(BookCategoriesDto.class);
        List<BookCategory> bookCategories = new ArrayList<>();
        Set<MemberCouponReadResponseDto> memberCouponReadResponseDtos = new HashSet<>();
        when(bookRepository.existsById(any())).thenReturn(true);
        lenient().when(bookCategoryRepository.findByPk_BookId(any())).thenReturn(bookCategories);
        lenient().when(memberCouponRepository.findOrderCoupons(memberId, bookCategoriesDto))
            .thenReturn(memberCouponReadResponseDtos);
        couponService.getOrderCouponList(bookIds, memberId);

        verify(bookCategoryRepository, times(3)).findByPk_BookId(any());
        verify(memberCouponRepository, times(3)).findOrderCoupons(any(), any());
        verify(bookRepository, times(3)).existsById(any());
    }

    @Test
    void testGetOrderCouponList_NotFoundExceptionBook() {
        Long[] bookIds = {1L, 2L, 3L};
        Long memberId = 1L;
        BookCategoriesDto bookCategoriesDto = mock(BookCategoriesDto.class);
        List<BookCategory> bookCategories = new ArrayList<>();
        Set<MemberCouponReadResponseDto> memberCouponReadResponseDtos = new HashSet<>();
        when(bookRepository.existsById(any())).thenReturn(false);
        lenient().when(bookCategoryRepository.findByPk_BookId(any())).thenReturn(bookCategories);
        lenient().when(memberCouponRepository.findOrderCoupons(memberId, bookCategoriesDto))
            .thenReturn(memberCouponReadResponseDtos);
        assertThrows(NotFoundException.class, () -> {
            couponService.getOrderCouponList(bookIds, memberId);
        });

        verify(bookRepository, times(1)).existsById(any());
    }

    @Test
    void testGetMemberCouponList_StatusActive() {
        Long memberId = 1L;
        LocalDate now = LocalDate.now();
        Page<MemberCoupon> memberCoupons = new PageImpl<>(new ArrayList<>());
        lenient().when(
                memberCouponRepository.findByMember_MemberIdAndUsedAtIsNullAndExpiredAtAfterOrExpiredAt(
                    memberId, now, now, PageRequest.of(0, 10)))
            .thenReturn(memberCoupons);
        couponService.getMemberCouponList(memberId, MemberCouponStatusEnum.ACTIVE,
            PageRequest.of(0, 10));

        verify(memberCouponRepository, times(0)).findByMember_MemberIdAndUsedAtIsNotNull(any(),
            any());
        verify(memberCouponRepository,
            times(1)).findByMember_MemberIdAndUsedAtIsNullAndExpiredAtAfterOrExpiredAt(any(), any(),
            any(), any());
        verify(memberCouponRepository,
            times(0)).findByMember_MemberIdAndExpiredAtBeforeAndUsedAtIsNull(any(), any(), any());

    }

    @Test
    void testGetMemberCouponList_StatusExpired() {
        Long memberId = 1L;
        LocalDate now = LocalDate.now();
        Page<MemberCoupon> memberCoupons = new PageImpl<>(new ArrayList<>());
        lenient().when(
                memberCouponRepository.findByMember_MemberIdAndExpiredAtBeforeAndUsedAtIsNull(
                    memberId, now, PageRequest.of(0, 10)))
            .thenReturn(memberCoupons);
        couponService.getMemberCouponList(memberId, MemberCouponStatusEnum.EXPIRED,
            PageRequest.of(0, 10));

        verify(memberCouponRepository, times(0)).findByMember_MemberIdAndUsedAtIsNotNull(any(),
            any());
        verify(memberCouponRepository,
            times(0)).findByMember_MemberIdAndUsedAtIsNullAndExpiredAtAfterOrExpiredAt(any(), any(),
            any(), any());
        verify(memberCouponRepository,
            times(1)).findByMember_MemberIdAndExpiredAtBeforeAndUsedAtIsNull(any(), any(), any());

    }

    @Test
    void testGetMemberCouponList_StatusUsed() {
        Long memberId = 1L;
        Page<MemberCoupon> memberCoupons = new PageImpl<>(new ArrayList<>());
        lenient().when(memberCouponRepository.findByMember_MemberIdAndUsedAtIsNotNull(memberId,
                PageRequest.of(0, 10)))
            .thenReturn(memberCoupons);
        couponService.getMemberCouponList(memberId, MemberCouponStatusEnum.USED,
            PageRequest.of(0, 10));

        verify(memberCouponRepository, times(1)).findByMember_MemberIdAndUsedAtIsNotNull(any(),
            any());
        verify(memberCouponRepository,
            times(0)).findByMember_MemberIdAndUsedAtIsNullAndExpiredAtAfterOrExpiredAt(any(), any(),
            any(), any());
        verify(memberCouponRepository,
            times(0)).findByMember_MemberIdAndExpiredAtBeforeAndUsedAtIsNull(any(), any(), any());

    }

    @Test
    void testGetIssuableCoupons() {
        Pageable pageable = mock(Pageable.class);

        CouponReadResponseDto couponReadResponseDto = mock(CouponReadResponseDto.class);
        Page<CouponReadResponseDto> mockPage = new PageImpl<>(List.of(couponReadResponseDto));

        when(couponRepository.findByCouponStatus_CouponStatusIdAndIsBirthFalse(
            1, pageable))
            .thenReturn(mockPage);

        couponService.getIssuableCoupons(pageable);

        verify(couponRepository).findByCouponStatus_CouponStatusIdAndIsBirthFalse(
            1, pageable);
    }

    @Test
    void testGetDetailCoupon() {
        String couponId = "asd";
        CouponDetailReadResponseDto couponDetailReadResponseDto = mock(
            CouponDetailReadResponseDto.class);

        when(couponRepository.findDetailCoupon(couponId)).thenReturn(
            Optional.of(couponDetailReadResponseDto));
        couponService.getDetailCoupon(couponId);
        verify(couponRepository, times(1)).findDetailCoupon(couponId);
    }

    @Test
    void testGetDetailCouponNotFound() {
        String couponId = "asd";

        when(couponRepository.findDetailCoupon(couponId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            couponService.getDetailCoupon(couponId);
        });
        verify(couponRepository, times(1)).findDetailCoupon(couponId);
    }

    @Test
    void testUseCoupons() {
        MemberCoupon memberCoupon = mock(MemberCoupon.class);
        List<MemberCoupon> memberCoupons = Arrays.asList(memberCoupon, memberCoupon,
            memberCoupon);
        Long memberId = 1L;
        List<Long> memberCouponIds = Arrays.asList(1L, 2L, 3L);
        when(memberCoupon.getUsedAt()).thenReturn(null);

        when(memberCouponRepository.findAllByMemberCouponIdInAndMember_MemberId(memberCouponIds,
            memberId))
            .thenReturn(memberCoupons);
        couponService.useCoupons(memberId, memberCouponIds);
        verify(memberCouponRepository, times(1)).findAllByMemberCouponIdInAndMember_MemberId(
            memberCouponIds, memberId);
    }

    @Test
    void testUseCoupons_NotFoundSize() {
        MemberCoupon memberCoupon = mock(MemberCoupon.class);
        List<MemberCoupon> memberCoupons = Arrays.asList(memberCoupon, memberCoupon,
            memberCoupon);
        Long memberId = 1L;
        List<Long> memberCouponIds = Arrays.asList(2L, 3L);

        // memberCouponRepository.findAllByMemberCouponIdInAndMember_MemberId() 메서드의 반환 값을 설정합니다.
        when(memberCouponRepository.findAllByMemberCouponIdInAndMember_MemberId(memberCouponIds,
            memberId))
            .thenReturn(memberCoupons);
        // useCoupons() 메서드를 호출합니다.
        assertThrows(NotFoundException.class, () -> {
            couponService.useCoupons(memberId, memberCouponIds);

        });
        verify(memberCouponRepository, times(1)).findAllByMemberCouponIdInAndMember_MemberId(
            memberCouponIds, memberId);
    }

    @Test
    void testUseCoupons_AlreadyUsedExceptionMemberCoupon() {
        MemberCoupon memberCoupon = mock(MemberCoupon.class);
        List<MemberCoupon> memberCoupons = Arrays.asList(memberCoupon, memberCoupon,
            memberCoupon);
        Long memberId = 1L;
        List<Long> memberCouponIds = Arrays.asList(1L, 2L, 3L);
        when(memberCoupon.getUsedAt()).thenReturn(LocalDate.now());

        when(memberCouponRepository.findAllByMemberCouponIdInAndMember_MemberId(memberCouponIds,
            memberId))
            .thenReturn(memberCoupons);
        assertThrows(AlreadyUsedException.class, () -> {
            couponService.useCoupons(memberId, memberCouponIds);

        });
        verify(memberCouponRepository, times(1)).findAllByMemberCouponIdInAndMember_MemberId(
            memberCouponIds, memberId);
    }

    @Test
    void testCancelCoupons() {
        MemberCoupon memberCoupon = mock(MemberCoupon.class);
        List<MemberCoupon> memberCoupons = Arrays.asList(memberCoupon, memberCoupon,
            memberCoupon);
        Long memberId = 1L;
        List<Long> memberCouponIds = Arrays.asList(1L, 2L, 3L);
        when(memberCoupon.getUsedAt()).thenReturn(null);
        lenient().when(memberCoupon.getUsedAt()).thenReturn(LocalDate.now());

        when(memberCouponRepository.findAllByMemberCouponIdInAndMember_MemberId(memberCouponIds,
            memberId))
            .thenReturn(memberCoupons);
        couponService.cancelCouponUsage(memberId, memberCouponIds);
        verify(memberCouponRepository, times(1)).findAllByMemberCouponIdInAndMember_MemberId(
            memberCouponIds, memberId);
    }

    @Test
    void testCancelCoupons_NotFoundSize() {
        MemberCoupon memberCoupon = mock(MemberCoupon.class);
        List<MemberCoupon> memberCoupons = Arrays.asList(memberCoupon, memberCoupon,
            memberCoupon);
        Long memberId = 1L;
        List<Long> memberCouponIds = Arrays.asList(2L, 3L);

        // memberCouponRepository.findAllByMemberCouponIdInAndMember_MemberId() 메서드의 반환 값을 설정합니다.
        when(memberCouponRepository.findAllByMemberCouponIdInAndMember_MemberId(memberCouponIds,
            memberId))
            .thenReturn(memberCoupons);
        lenient().when(memberCoupon.getUsedAt()).thenReturn(LocalDate.now());
        // useCoupons() 메서드를 호출합니다.
        assertThrows(NotFoundException.class, () -> {
            couponService.cancelCouponUsage(memberId, memberCouponIds);

        });
        verify(memberCouponRepository, times(1)).findAllByMemberCouponIdInAndMember_MemberId(
            memberCouponIds, memberId);
    }

    @Test
    void testCancelCoupons_AlreadyUsedExceptionMemberCoupon() {
        MemberCoupon memberCoupon = mock(MemberCoupon.class);
        List<MemberCoupon> memberCoupons = Arrays.asList(memberCoupon, memberCoupon,
            memberCoupon);
        Long memberId = 1L;
        List<Long> memberCouponIds = Arrays.asList(1L, 2L, 3L);
        lenient().when(memberCoupon.getUsedAt()).thenReturn(null);

        when(memberCouponRepository.findAllByMemberCouponIdInAndMember_MemberId(memberCouponIds,
            memberId))
            .thenReturn(memberCoupons);
        assertThrows(AlreadyUsedException.class, () -> {
            couponService.cancelCouponUsage(memberId, memberCouponIds);

        });
        verify(memberCouponRepository, times(1)).findAllByMemberCouponIdInAndMember_MemberId(
            memberCouponIds, memberId);
    }

    @Test
    void testCreateBirthdayCoupon() {
        CouponType couponType = mock(CouponType.class);
        CouponStatus couponStatus = mock(CouponStatus.class);
        BirthDayCouponCreateRequestDto birthDayCouponCreateRequestDto = mock(
            BirthDayCouponCreateRequestDto.class);
        when(birthDayCouponCreateRequestDto.getMonth()).thenReturn(4);
        when(couponTypeRepository.findById(any())).thenReturn(Optional.of(couponType));
        when(couponStatusRepository.findById(any())).thenReturn(Optional.of(couponStatus));

        couponService.createBirthdayCoupon(birthDayCouponCreateRequestDto);
        verify(couponRepository, times(1)).save(any(Coupon.class));
    }

    @Test
    void testCreateBirthdayCoupon_AlreadyExistException() {
        CouponType couponType = mock(CouponType.class);
        CouponStatus couponStatus = mock(CouponStatus.class);
        BirthDayCouponCreateRequestDto birthDayCouponCreateRequestDto = mock(
            BirthDayCouponCreateRequestDto.class);
        when(birthDayCouponCreateRequestDto.getMonth()).thenReturn(4);
        // 변경된 스텁 설정
        when(couponRepository.existsByBasicIssuedDateAndIsBirthTrue(
            LocalDate.of(2024, 4, 1))).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> {
            couponService.createBirthdayCoupon(birthDayCouponCreateRequestDto);
        });
        verify(couponRepository, times(1)).existsByBasicIssuedDateAndIsBirthTrue(
            LocalDate.of(2024, 4, 1));
    }
}