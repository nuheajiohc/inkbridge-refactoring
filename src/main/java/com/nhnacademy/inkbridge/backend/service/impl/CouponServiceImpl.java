package com.nhnacademy.inkbridge.backend.service.impl;

import static com.nhnacademy.inkbridge.backend.enums.BookMessageEnum.BOOK_NOT_FOUND;
import static com.nhnacademy.inkbridge.backend.enums.CategoryMessageEnum.CATEGORY_NOT_FOUND;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_ALREADY_NOT_USED;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_ALREADY_USED;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_DUPLICATED;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_ID;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_ISSUED_EXIST;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_ISSUE_PERIOD_EXPIRED;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_ISSUE_PERIOD_NOT_STARTED;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_NOT_FOUND;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_STATUS_NOT_FOUND;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_TYPE_NOT_FOUND;
import static com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum.MEMBER_ID;
import static com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum.MEMBER_NOT_FOUND;

import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoriesDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.BirthDayCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.BookCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CategoryCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.MemberCouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.OrderCouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookCategory;
import com.nhnacademy.inkbridge.backend.entity.BookCoupon;
import com.nhnacademy.inkbridge.backend.entity.BookCoupon.Pk;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.entity.CategoryCoupon;
import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.CouponStatus;
import com.nhnacademy.inkbridge.backend.entity.CouponType;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum;
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
import com.nhnacademy.inkbridge.backend.service.CouponService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: CouponServiceImpl.
 *
 * @author JBum
 * @version 2024/02/15
 */
@Service
public class CouponServiceImpl implements CouponService {

    private static final int COUPON_NORMAL = 1;
    private static final int COUPON_WAIT = 4;
    private final CouponRepository couponRepository;
    private final CouponTypeRepository couponTypeRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryCouponRepository categoryCouponRepository;
    private final BookRepository bookRepository;
    private final BookCouponRepository bookCouponRepository;
    private final CouponStatusRepository couponStatusRepository;
    private final BookCategoryRepository bookCategoryRepository;

    /**
     * couponService에 필요한 Repository들 주입.
     *
     * @param couponRepository         쿠폰
     * @param couponTypeRepository     쿠폰타입
     * @param memberRepository         멤버
     * @param memberCouponRepository   멤버가 가진 쿠폰
     * @param categoryRepository       카테고리
     * @param categoryCouponRepository 카테고리전용 쿠폰
     * @param bookRepository           책
     * @param bookCouponRepository     책전용 쿠폰
     * @param couponStatusRepository   쿠폰상태
     * @param bookCategoryRepository   책 카테고리 연관관계
     */
    public CouponServiceImpl(CouponRepository couponRepository,
        CouponTypeRepository couponTypeRepository, MemberRepository memberRepository,
        MemberCouponRepository memberCouponRepository, CategoryRepository categoryRepository,
        CategoryCouponRepository categoryCouponRepository, BookRepository bookRepository,
        BookCouponRepository bookCouponRepository, CouponStatusRepository couponStatusRepository,
        BookCategoryRepository bookCategoryRepository) {
        this.couponRepository = couponRepository;
        this.couponTypeRepository = couponTypeRepository;
        this.memberRepository = memberRepository;
        this.memberCouponRepository = memberCouponRepository;
        this.categoryRepository = categoryRepository;
        this.categoryCouponRepository = categoryCouponRepository;
        this.bookRepository = bookRepository;
        this.bookCouponRepository = bookCouponRepository;
        this.couponStatusRepository = couponStatusRepository;
        this.bookCategoryRepository = bookCategoryRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void createCoupon(CouponCreateRequestDto couponCreateRequestDto) {
        CouponType couponType = findCouponType(couponCreateRequestDto.getCouponTypeId());
        CouponStatus couponStatus = findCouponStatusByIssuedDate(
            couponCreateRequestDto.getBasicIssuedDate());

        Coupon newCoupon = Coupon.builder().couponId(UUID.randomUUID().toString())
            .couponType(couponType).couponName(couponCreateRequestDto.getCouponName())
            .basicExpiredDate(couponCreateRequestDto.getBasicExpiredDate())
            .basicIssuedDate(couponCreateRequestDto.getBasicIssuedDate())
            .discountPrice(couponCreateRequestDto.getDiscountPrice())
            .isBirth(false)
            .maxDiscountPrice(couponCreateRequestDto.getMaxDiscountPrice())
            .minPrice(couponCreateRequestDto.getMinPrice())
            .validity(couponCreateRequestDto.getValidity()).couponStatus(couponStatus).build();
        couponRepository.save(newCoupon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void createCategoryCoupon(
        CategoryCouponCreateRequestDto categoryCouponCreateRequestDto) {
        CouponType couponType = findCouponType(categoryCouponCreateRequestDto.getCouponTypeId());

        CouponStatus couponStatus = findCouponStatusByIssuedDate(
            categoryCouponCreateRequestDto.getBasicIssuedDate());

        Coupon newCoupon = Coupon.builder().couponId(UUID.randomUUID().toString())
            .couponType(couponType).couponName(categoryCouponCreateRequestDto.getCouponName())
            .basicExpiredDate(categoryCouponCreateRequestDto.getBasicExpiredDate())
            .basicIssuedDate(categoryCouponCreateRequestDto.getBasicIssuedDate())
            .discountPrice(categoryCouponCreateRequestDto.getDiscountPrice())
            .maxDiscountPrice(categoryCouponCreateRequestDto.getMaxDiscountPrice())
            .isBirth(false)
            .minPrice(categoryCouponCreateRequestDto.getMinPrice())
            .validity(categoryCouponCreateRequestDto.getValidity()).couponStatus(couponStatus)
            .build();
        couponRepository.save(newCoupon);
        Category category = categoryRepository.findById(
                categoryCouponCreateRequestDto.getCategoryId())
            .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND.getMessage()));
        saveCategoryCoupon(category, newCoupon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void createBookCoupon(BookCouponCreateRequestDto bookCouponCreateRequestDto) {
        CouponType couponType = findCouponType(bookCouponCreateRequestDto.getCouponTypeId());

        CouponStatus couponStatus = findCouponStatusByIssuedDate(
            bookCouponCreateRequestDto.getBasicIssuedDate());
        Coupon newCoupon = Coupon.builder().couponId(UUID.randomUUID().toString())
            .couponType(couponType).couponName(bookCouponCreateRequestDto.getCouponName())
            .basicIssuedDate(bookCouponCreateRequestDto.getBasicIssuedDate())
            .basicExpiredDate(bookCouponCreateRequestDto.getBasicExpiredDate())
            .discountPrice(bookCouponCreateRequestDto.getDiscountPrice())
            .maxDiscountPrice(bookCouponCreateRequestDto.getMaxDiscountPrice())
            .isBirth(false)
            .minPrice(bookCouponCreateRequestDto.getMinPrice())
            .validity(bookCouponCreateRequestDto.getValidity()).couponStatus(couponStatus).build();
        couponRepository.saveAndFlush(newCoupon);
        Book book = bookRepository.findById(bookCouponCreateRequestDto.getBookId())
            .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND.getMessage()));
        saveBookCoupon(book, newCoupon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void issueCoupon(Long memberId, String couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(
            () -> new NotFoundException(
                String.format("%s%s%s", COUPON_NOT_FOUND.getMessage(), COUPON_ID.getMessage(),
                    couponId)));
        Member member = memberRepository.findById(memberId).orElseThrow(
            () -> new NotFoundException(
                String.format("%s%s%d", MEMBER_NOT_FOUND.getMessage(), MEMBER_ID.getMessage(),
                    memberId)));
        validateCouponPeriod(coupon.getBasicIssuedDate(), coupon.getBasicExpiredDate());
        if (memberCouponRepository.existsByCouponAndMember(coupon, member)) {
            throw new AlreadyExistException(COUPON_ISSUED_EXIST.getMessage());
        }
        MemberCoupon memberCoupon = MemberCoupon.builder()
            .member(member).coupon(coupon)
            .issuedAt(LocalDate.now()).expiredAt(LocalDate.now().plusDays(coupon.getValidity()))
            .build();
        memberCouponRepository.saveAndFlush(memberCoupon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CouponReadResponseDto> adminViewCoupons(Pageable pageable, int couponStatusId) {
        CouponStatus couponStatus = findCouponStatus(couponStatusId);
        return couponRepository.findByCouponStatus(couponStatus, pageable);
    }


    /**
     * Member가 사용했는 쿠폰인지 확인하는 메소드.
     *
     * @param memberCoupon 멤버의 쿠폰
     * @throws AlreadyUsedException 사용한 쿠폰이라면 예외 발생
     */
    private void validateCouponUsed(MemberCoupon memberCoupon) {
        if (memberCoupon.getUsedAt() != null) {
            throw new AlreadyUsedException(COUPON_ALREADY_USED.getMessage());
        }
    }

    /**
     * Member가 사용안했는 쿠폰인지 확인하는 메소드.
     *
     * @param memberCoupon 멤버의 쿠폰
     * @throws AlreadyUsedException 사용안한 쿠폰이라면 예외 발생
     */
    private void validateCouponNotUsed(MemberCoupon memberCoupon) {
        if (memberCoupon.getUsedAt() == null) {
            throw new AlreadyUsedException(COUPON_ALREADY_NOT_USED.getMessage());
        }
    }

    /**
     * 발급 가능한 날짜 인지 체크 하는 메소드.
     *
     * @param startDate 발급 시작 날짜
     * @param endDate   발급 종료 날짜
     * @throws InvalidPeriodException 발급 기한이 아닌 경우 예외 발생
     */
    private void validateCouponPeriod(LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (now.isBefore(startDate)) {
            throw new InvalidPeriodException(COUPON_ISSUE_PERIOD_NOT_STARTED.getMessage());
        } else if (endDate.isBefore(now)) {
            throw new InvalidPeriodException(COUPON_ISSUE_PERIOD_EXPIRED.getMessage());
        }
    }

    /**
     * 카테고리 쿠폰을 저장하는 메소드.
     *
     * @param category 적용할 카테고리
     * @param coupon   적용할 쿠폰
     */
    private void saveCategoryCoupon(Category category, Coupon coupon) {
        CategoryCoupon categoryCoupon = CategoryCoupon.builder().category(category).coupon(coupon)
            .pk(CategoryCoupon.Pk.builder().couponId(coupon.getCouponId())
                .categoryId(category.getCategoryId()).build()).build();
        categoryCouponRepository.save(categoryCoupon);
    }

    /**
     * 책 쿠폰을 저장하는 메소드.
     *
     * @param book   적용할 책
     * @param coupon 적용할 쿠폰
     */
    private void saveBookCoupon(Book book, Coupon coupon) {
        BookCoupon bookCoupon = BookCoupon.builder().coupon(coupon).book(book)
            .pk(Pk.builder().bookId(book.getBookId()).couponId(coupon.getCouponId()).build())
            .build();
        bookCouponRepository.save(bookCoupon);
    }

    /**
     * 입력받은 쿠폰상태가 존재하는지 찾는 메소드.
     *
     * @param couponStatusId 쿠폰상태 id
     * @return 쿠폰상태
     * @throws NotFoundException 존재하는 쿠폰상태가 아닌 경우 예외 발생
     */
    private CouponStatus findCouponStatus(int couponStatusId) {
        return couponStatusRepository.findById(couponStatusId)
            .orElseThrow(() -> new NotFoundException(COUPON_STATUS_NOT_FOUND.getMessage()));
    }

    /**
     * 쿠폰의상태를 등록날짜를 기준으로 조회한다.
     *
     * @param basicIssuedDate 등록날짜
     * @return 쿠폰상태
     * @throws NotFoundException 날짜가 현재
     */
    private CouponStatus findCouponStatusByIssuedDate(LocalDate basicIssuedDate) {
        LocalDate today = LocalDate.now();
        if (basicIssuedDate.isBefore(today)) {
            throw new NotFoundException(
                CouponMessageEnum.COUPON_CANNOT_CREATE_PAST_DATE.getMessage());
        } else {
            return couponStatusRepository.findById(
                    today.isEqual(basicIssuedDate) ? COUPON_NORMAL : COUPON_WAIT)
                .orElseThrow(() -> new NotFoundException(COUPON_STATUS_NOT_FOUND.getMessage()));
        }
    }

    /**
     * 입력받은 쿠폰타입이 존재하는지 찾는 메소드.
     *
     * @param couponTypeId 쿠폰타입 id
     * @return 쿠폰타입
     * @throws NotFoundException 존재하는 쿠폰타입이 아닌 경우 예외 발생
     */
    private CouponType findCouponType(int couponTypeId) {
        return couponTypeRepository.findById(couponTypeId)
            .orElseThrow(() -> new NotFoundException(COUPON_TYPE_NOT_FOUND.getMessage()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderCouponReadResponseDto> getOrderCouponList(Long[] bookIds, Long memberId) {
        bookCheck(bookIds);
        List<BookCategoriesDto> bookCategoriesDtoList = new ArrayList<>();
        List<OrderCouponReadResponseDto> orderCouponReadResponseDtoList = new ArrayList<>();
        for (Long bookId : bookIds) {
            List<BookCategory> bookCategory = bookCategoryRepository.findByPk_BookId(bookId);
            bookCategoriesDtoList.add(
                BookCategoriesDto.builder().bookId(bookId).categoryIds(bookCategory.stream()
                    .map(bookCategoryItem -> bookCategoryItem.getCategory().getCategoryId())
                    .collect(Collectors.toList())).build());
        }
        for (BookCategoriesDto bookCategoriesDto : bookCategoriesDtoList) {

            orderCouponReadResponseDtoList.add(
                OrderCouponReadResponseDto.builder().bookId(bookCategoriesDto.getBookId())
                    .memberCouponReadResponseDtos(
                        memberCouponRepository.findOrderCoupons(memberId, bookCategoriesDto))
                    .build());
        }
        return orderCouponReadResponseDtoList;
    }

    /**
     * {@inheritDoc}
     */
    public Page<MemberCouponReadResponseDto> getMemberCouponList(Long memberId,
        MemberCouponStatusEnum statusEnum, Pageable pageable) {
        LocalDate now = LocalDate.now();
        Page<MemberCoupon> coupons = null;

        if (statusEnum == MemberCouponStatusEnum.USED) {
            coupons = memberCouponRepository.findByMember_MemberIdAndUsedAtIsNotNull(memberId,
                pageable);
        } else if (statusEnum == MemberCouponStatusEnum.ACTIVE) {
            coupons = memberCouponRepository.findByMember_MemberIdAndUsedAtIsNullAndExpiredAtAfterOrExpiredAt(
                memberId, now, now, pageable);
        } else if (statusEnum == MemberCouponStatusEnum.EXPIRED) {
            coupons = memberCouponRepository.findByMember_MemberIdAndExpiredAtBeforeAndUsedAtIsNull(
                memberId, now, pageable);
        }

        if (coupons != null) {
            return coupons.map(MemberCoupon::toResponseDto);
        } else {
            return Page.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CouponReadResponseDto> getIssuableCoupons(Pageable pageable) {
        return couponRepository.findByCouponStatus_CouponStatusIdAndIsBirthFalse(COUPON_NORMAL,
            pageable);
    }

    /**
     * 책이 존재하는지 확인하는 메소드.
     *
     * @param bookIds 확인할 책 번호
     * @throw NotFoundException 책이 존재하지 않으면 발생
     */
    private void bookCheck(Long[] bookIds) {
        Arrays.stream(bookIds)
            .filter(bookId -> !bookRepository.existsById(bookId))
            .findFirst()
            .ifPresent(id -> {
                throw new NotFoundException(BOOK_NOT_FOUND.getMessage());
            });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponDetailReadResponseDto getDetailCoupon(String couponId) {

        return couponRepository.findDetailCoupon(couponId).orElseThrow(() -> new NotFoundException(
            COUPON_NOT_FOUND.getMessage()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void useCoupons(Long memberId, List<Long> memberCouponIds) {
        List<MemberCoupon> useCoupons = memberCouponRepository
            .findAllByMemberCouponIdInAndMember_MemberId(
                memberCouponIds, memberId);
        if (useCoupons.size() != memberCouponIds.size()) {
            throw new NotFoundException(COUPON_STATUS_NOT_FOUND.getMessage());
        }
        useCoupons.forEach(coupon -> {
            validateCouponUsed(coupon);
            coupon.use();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void cancelCouponUsage(Long memberId, List<Long> memberCouponIds) {
        List<MemberCoupon> useCoupons = memberCouponRepository
            .findAllByMemberCouponIdInAndMember_MemberId(
                memberCouponIds, memberId);
        if (useCoupons.size() != memberCouponIds.size()) {
            throw new NotFoundException(COUPON_STATUS_NOT_FOUND.getMessage());
        }
        useCoupons.forEach(coupon -> {
            validateCouponNotUsed(coupon);
            coupon.cancel();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void createBirthdayCoupon(
        BirthDayCouponCreateRequestDto birthDayCouponCreateRequestDto) {
        LocalDate basicIssuedDate = LocalDate.of(LocalDate.now().getYear(),
            birthDayCouponCreateRequestDto.getMonth(), 1);
        if (couponRepository.existsByBasicIssuedDateAndIsBirthTrue(basicIssuedDate)) {
            throw new AlreadyExistException(COUPON_DUPLICATED.getMessage());
        }
        Integer dayCount = LocalDate.now().withMonth(birthDayCouponCreateRequestDto.getMonth())
            .lengthOfMonth();
        LocalDate basicExpiredDate = LocalDate.of(LocalDate.now().getYear(),
            birthDayCouponCreateRequestDto.getMonth(),
            dayCount);
        CouponStatus couponStatus = findCouponStatusByIssuedDate(
            basicIssuedDate);
        CouponType couponType = findCouponType(2);

        Coupon newCoupon = Coupon.builder().couponId(UUID.randomUUID().toString())
            .couponType(couponType).couponName(birthDayCouponCreateRequestDto.getCouponName())
            .basicExpiredDate(basicExpiredDate)
            .basicIssuedDate(basicIssuedDate)
            .discountPrice(birthDayCouponCreateRequestDto.getDiscountPrice())
            .isBirth(true)
            .maxDiscountPrice(birthDayCouponCreateRequestDto.getMaxDiscountPrice())
            .minPrice(birthDayCouponCreateRequestDto.getMinPrice())
            .validity(dayCount).couponStatus(couponStatus).build();
        couponRepository.save(newCoupon);
    }
}