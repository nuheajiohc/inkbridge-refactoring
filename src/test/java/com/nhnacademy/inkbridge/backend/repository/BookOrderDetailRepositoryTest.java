package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nhnacademy.inkbridge.backend.dto.book.BookStockUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderBooksIdResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.BookOrderDetail;
import com.nhnacademy.inkbridge.backend.entity.BookOrderStatus;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.CouponStatus;
import com.nhnacademy.inkbridge.backend.entity.CouponType;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAuth;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import com.nhnacademy.inkbridge.backend.entity.MemberGrade;
import com.nhnacademy.inkbridge.backend.entity.MemberStatus;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.entity.Wrapping;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * class: BookOrderDetailRepositoryTest.
 *
 * @author jangjaehun
 * @version 2024/03/21
 */
@DataJpaTest
class BookOrderDetailRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookOrderDetailRepository bookOrderDetailRepository;

    MemberAuth memberAuth;
    MemberGrade memberGrade;
    MemberStatus memberStatus;
    Member member;
    BookOrder bookOrder;
    BookStatus bookStatus;
    Publisher publisher;
    File thumbnailFile;
    Book book;
    BookOrderStatus bookOrderStatus;
    BookOrderDetail bookOrderDetail;
    MemberCoupon memberCoupon;
    Coupon coupon;
    CouponType couponType;
    CouponStatus couponStatus;
    Wrapping wrapping;


    @BeforeEach
    void setup() {
        memberAuth = MemberAuth.create()
            .memberAuthId(1)
            .memberAuthName("MEMBER")
            .build();

        entityManager.persist(memberAuth);

        memberStatus = MemberStatus.create()
            .memberStatusId(1)
            .memberStatusName("ACTIVE")
            .build();

        entityManager.persist(memberStatus);

        memberGrade = MemberGrade.create()
            .gradeId(1)
            .grade("STANDARD")
            .pointRate(BigDecimal.valueOf(1L))
            .standardAmount(100000L)
            .build();

        entityManager.persist(memberGrade);

        member = Member.create()
            .memberName("장재훈")
            .phoneNumber("01099999999")
            .email("test@inkbridge.store")
            .birthday(LocalDate.of(1999, 8, 19))
            .password("password")
            .memberPoint(10000L)
            .memberAuth(memberAuth)
            .memberStatus(memberStatus)
            .memberGrade(memberGrade)
            .build();

        member = entityManager.persist(member);

        bookOrder = BookOrder.builder()
            .orderCode("orderCode")
            .orderName("orderName")
            .orderAt(LocalDateTime.of(2024, 1, 1, 0, 0))
            .receiver("receiverName")
            .receiverNumber("01011112222")
            .zipCode("00000")
            .address("address")
            .addressDetail("addressDetail")
            .orderer("senderName")
            .ordererNumber("01022223333")
            .ordererEmail("sender@inkbridge.store")
            .deliveryDate(LocalDate.of(2024, 1, 1))
            .usePoint(0L)
            .totalPrice(10000L)
            .deliveryPrice(3000L)
            .isPayment(false)
            .member(member)
            .build();

        entityManager.persist(bookOrder);

        bookStatus = BookStatus.builder()
            .statusId(1L)
            .statusName("ACTIVE")
            .build();

        entityManager.persist(bookStatus);

        publisher = Publisher.builder()
            .publisherName("출판사")
            .build();

        entityManager.persist(publisher);

        thumbnailFile = File.builder()
            .fileUrl("파일 경로")
            .fileName("파일 이름")
            .fileExtension("파일 확장자")
            .build();

        entityManager.persist(thumbnailFile);

        book = Book.builder()
            .bookTitle("도서 제목")
            .bookIndex("목차")
            .description("설명")
            .publicatedAt(LocalDate.of(2020, 1, 1))
            .isbn("ISBN")
            .regularPrice(15000L)
            .price(10000L)
            .discountRatio(BigDecimal.valueOf(33))
            .isPackagable(true)
            .updatedAt(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
            .bookStatus(bookStatus)
            .publisher(publisher)
            .thumbnailFile(thumbnailFile)
            .build();

        entityManager.persist(book);

        bookOrderStatus = BookOrderStatus.builder()
            .orderStatusId(1L)
            .orderStatus("WAITING")
            .build();

        entityManager.persist(bookOrderStatus);

        couponType = CouponType.builder()
            .couponTypeId(1)
            .typeName("PERCENT")
            .build();

        entityManager.persist(couponType);

        couponStatus = CouponStatus.builder()
            .couponStatusId(1)
            .couponStatusName("ACTIVE")
            .build();

        entityManager.persist(couponStatus);

        coupon = Coupon.builder()
            .couponId("couponId")
            .couponName("couponName")
            .minPrice(2500L)
            .maxDiscountPrice(5000L)
            .basicIssuedDate(LocalDate.of(2023, 12, 31))
            .basicExpiredDate(LocalDate.of(2024, 1, 1))
            .isBirth(false)
            .couponType(couponType)
            .couponStatus(couponStatus)
            .build();

        entityManager.persist(coupon);

        memberCoupon = MemberCoupon.builder()
            .expiredAt(LocalDate.of(2024, 1, 1))
            .issuedAt(LocalDate.of(2023, 12, 31))
            .member(member)
            .coupon(coupon)
            .build();

        memberCoupon = entityManager.persist(memberCoupon);

        wrapping = Wrapping.builder()
            .wrappingName("포장지")
            .price(300L)
            .isActive(true)
            .build();

        entityManager.persist(wrapping);

        bookOrderDetail = BookOrderDetail.builder()
            .bookPrice(10000L)
            .wrappingPrice(300L)
            .amount(1)
            .bookOrderStatus(bookOrderStatus)
            .bookOrder(bookOrder)
            .book(book)
            .memberCoupon(memberCoupon)
            .wrapping(wrapping)
            .build();

        bookOrderDetail = entityManager.persist(bookOrderDetail);
    }


    @Test
    @DisplayName("주문에 사용한 쿠폰 목록 조회")
    void testFindAllByOrderCode() {
        List<Long> result = bookOrderDetailRepository.findAllByOrderCode("orderCode");

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(1, result.size()),
            () -> assertEquals(memberCoupon.getMemberCouponId(), result.get(0))
        );
    }

    @Test
    @DisplayName("주문 상세 목록 DTO 조회 - 주문 번호")
    void testFindAllOrderDetailByOrderId() {
        List<OrderDetailReadResponseDto> result = bookOrderDetailRepository.findAllOrderDetailByOrderId(
            bookOrder.getOrderId());

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(bookOrderDetail.getOrderDetailId(),
                result.get(0).getOrderDetailId()),
            () -> assertEquals(bookOrderDetail.getBookPrice(), result.get(0).getBookPrice()),
            () -> assertEquals(bookOrderDetail.getWrappingPrice(),
                result.get(0).getWrappingPrice()),
            () -> assertEquals(bookOrderDetail.getAmount(), result.get(0).getAmount()),
            () -> assertEquals(wrapping.getWrappingName(), result.get(0).getWrappingName()),
            () -> assertEquals(bookOrderStatus.getOrderStatus(), result.get(0).getOrderStatus()),
            () -> assertEquals(book.getBookId(), result.get(0).getBookId()),
            () -> assertEquals(thumbnailFile.getFileUrl(), result.get(0).getThumbnailUrl()),
            () -> assertEquals(book.getBookTitle(), result.get(0).getBookTitle()),
            () -> assertEquals(coupon.getCouponName(), result.get(0).getCouponName()),
            () -> assertEquals(couponType.getTypeName(), result.get(0).getCouponTypeName()),
            () -> assertEquals(coupon.getMaxDiscountPrice(), result.get(0).getMaxDiscountPrice()),
            () -> assertEquals(coupon.getDiscountPrice(), result.get(0).getDiscountPrice())
        );
    }

    @Test
    @DisplayName("주문 상세 목록 DTO 조회 - 주문 코드")
    void testFindAllOrderDetailByOrderCode() {
        List<OrderDetailReadResponseDto> result = bookOrderDetailRepository.findAllOrderDetailByOrderCode(
            bookOrder.getOrderCode());

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(bookOrderDetail.getOrderDetailId(),
                result.get(0).getOrderDetailId()),
            () -> assertEquals(bookOrderDetail.getBookPrice(), result.get(0).getBookPrice()),
            () -> assertEquals(bookOrderDetail.getWrappingPrice(),
                result.get(0).getWrappingPrice()),
            () -> assertEquals(bookOrderDetail.getAmount(), result.get(0).getAmount()),
            () -> assertEquals(wrapping.getWrappingName(), result.get(0).getWrappingName()),
            () -> assertEquals(bookOrderStatus.getOrderStatus(), result.get(0).getOrderStatus()),
            () -> assertEquals(book.getBookId(), result.get(0).getBookId()),
            () -> assertEquals(thumbnailFile.getFileUrl(), result.get(0).getThumbnailUrl()),
            () -> assertEquals(book.getBookTitle(), result.get(0).getBookTitle()),
            () -> assertEquals(coupon.getCouponName(), result.get(0).getCouponName()),
            () -> assertEquals(couponType.getTypeName(), result.get(0).getCouponTypeName()),
            () -> assertEquals(coupon.getMaxDiscountPrice(), result.get(0).getMaxDiscountPrice()),
            () -> assertEquals(coupon.getDiscountPrice(), result.get(0).getDiscountPrice())
        );
    }

    @Test
    @DisplayName("주문 상세 목록 Entity 조회 - 주문 번호")
    void testOrderDetailByOrderId() {
        List<BookOrderDetail> result = bookOrderDetailRepository.findOrderDetailByOrderId(
            bookOrder.getOrderId());

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(bookOrderDetail.getOrderDetailId(),
                result.get(0).getOrderDetailId()),
            () -> assertEquals(bookOrderDetail.getBookPrice(), result.get(0).getBookPrice()),
            () -> assertEquals(bookOrderDetail.getWrappingPrice(),
                result.get(0).getWrappingPrice()),
            () -> assertEquals(bookOrderDetail.getAmount(), result.get(0).getAmount()),
            () -> assertEquals(bookOrderDetail.getBookOrderStatus(),
                result.get(0).getBookOrderStatus()),
            () -> assertEquals(bookOrderDetail.getWrapping(), result.get(0).getWrapping()),
            () -> assertEquals(bookOrderDetail.getBookOrder(), result.get(0).getBookOrder()),
            () -> assertEquals(bookOrderDetail.getBook(), result.get(0).getBook()),
            () -> assertEquals(bookOrderDetail.getMemberCoupon(), result.get(0).getMemberCoupon())
        );
    }

    @Test
    @DisplayName("도서 주문 수량 조회")
    void testFindBookStockByOrderCode() {
        List<BookStockUpdateRequestDto> result = bookOrderDetailRepository.findBookStockByOrderCode(
            bookOrder.getOrderCode());

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(bookOrderDetail.getBook().getBookId(), result.get(0).getBookId()),
            () -> assertEquals(bookOrderDetail.getAmount(), result.get(0).getAmount())
        );
    }

    @Test
    @DisplayName("주문 상세 조회")
    void testFindOrderDetailByOrderCode() {
        List<BookOrderDetail> result = bookOrderDetailRepository.findOrderDetailByOrderCode(
            bookOrder.getOrderCode());

        assertEquals(bookOrderDetail, result.get(0));
    }

    @Test
    @DisplayName("주문 도서 번호 조회")
    void testFindBookIdByOrderCode() {
        List<OrderBooksIdResponseDto> result = bookOrderDetailRepository.findBookIdByOrderCode(
            bookOrder.getOrderCode());

        assertEquals(book.getBookId(), result.get(0).getBookId());
    }
}