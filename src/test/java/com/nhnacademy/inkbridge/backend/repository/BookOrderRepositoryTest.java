package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberPointReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderedMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAuth;
import com.nhnacademy.inkbridge.backend.entity.MemberGrade;
import com.nhnacademy.inkbridge.backend.entity.MemberStatus;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * class: BookOrderRepositoryImplTest.
 *
 * @author jangjaehun
 * @version 2024/03/13
 */
@DataJpaTest
class BookOrderRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookOrderRepository bookOrderRepository;

    BookOrder bookOrder;

    Member member;

    MemberAuth memberAuth;

    MemberStatus memberStatus;

    MemberGrade memberGrade;


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
    }

    @Test
    @DisplayName("주문 결제 정보 조회 - 주문 번호로 조회")
    void testFindOrderPayByOrderId() {
        OrderPayInfoReadResponseDto result = bookOrderRepository.findOrderPayByOrderId(
                bookOrder.getOrderId())
            .orElse(null);

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(bookOrder.getOrderCode(), result.getOrderCode()),
            () -> assertEquals(bookOrder.getOrderName(), result.getOrderName()),
            () -> assertEquals(bookOrder.getTotalPrice(), result.getAmount())
        );
    }

    @Test
    @DisplayName("주문 결제 정보 조회 - 주문 코드로 조회")
    void testFindOrderPayByOrderCode() {
        OrderPayInfoReadResponseDto result = bookOrderRepository.findOrderPayByOrderCode(
                "orderCode")
            .orElse(null);

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(bookOrder.getOrderCode(), result.getOrderCode()),
            () -> assertEquals(bookOrder.getOrderName(), result.getOrderName()),
            () -> assertEquals(bookOrder.getTotalPrice(), result.getAmount())
        );
    }

    @Test
    @DisplayName("주문 사용 포인트 조회")
    void testFindUsedPointByOrderCode() {
        OrderedMemberPointReadResponseDto result = bookOrderRepository.findUsedPointByOrderCode(
            "orderCode");

        assertAll(
            () -> assertEquals(bookOrder.getMember().getMemberId(), result.getMemberId()),
            () -> assertEquals(bookOrder.getUsePoint(), result.getUsePoint()),
            () -> assertEquals(bookOrder.getTotalPrice(), result.getTotalPrice())
        );
    }

    @Test
    @DisplayName("회원 주문 목록 조회")
    void testFindOrderByMemberId() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<OrderReadResponseDto> result = bookOrderRepository.findOrderByMemberId(
            member.getMemberId(), pageable);

        List<OrderReadResponseDto> content = result.getContent();

        assertAll(
            () -> assertEquals(1, content.size()),
            () -> assertEquals(bookOrder.getOrderId(), content.get(0).getOrderId()),
            () -> assertEquals(bookOrder.getOrderCode(), content.get(0).getOrderCode()),
            () -> assertEquals(bookOrder.getOrderName(), content.get(0).getOrderName()),
            () -> assertEquals(bookOrder.getOrderAt(), content.get(0).getOrderAt()),
            () -> assertEquals(bookOrder.getDeliveryDate(), content.get(0).getDeliveryDate()),
            () -> assertEquals(bookOrder.getTotalPrice(), content.get(0).getTotalPrice())
        );
    }

    @Test
    @DisplayName("주문 조회 - 주문 번호")
    void testFindOrderByOrderId() {
        OrderResponseDto result = bookOrderRepository.findOrderByOrderId(bookOrder.getOrderId())
            .orElse(null);

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(bookOrder.getOrderCode(), result.getOrderCode()),
            () -> assertEquals(bookOrder.getOrderName(), result.getOrderName()),
            () -> assertEquals(bookOrder.getReceiver(), result.getReceiverName()),
            () -> assertEquals(bookOrder.getReceiverNumber(), result.getReceiverPhoneNumber()),
            () -> assertEquals(bookOrder.getZipCode(), result.getZipCode()),
            () -> assertEquals(bookOrder.getAddress(), result.getAddress()),
            () -> assertEquals(bookOrder.getAddressDetail(), result.getDetailAddress()),
            () -> assertEquals(bookOrder.getOrderer(), result.getSenderName()),
            () -> assertEquals(bookOrder.getOrdererNumber(), result.getSenderPhoneNumber()),
            () -> assertEquals(bookOrder.getOrdererEmail(), result.getSenderEmail()),
            () -> assertEquals(bookOrder.getDeliveryDate(), result.getDeliveryDate()),
            () -> assertEquals(bookOrder.getUsePoint(), result.getUsePoint()),
            () -> assertEquals(bookOrder.getTotalPrice(), result.getPayAmount()),
            () -> assertEquals(bookOrder.getDeliveryPrice(), result.getDeliveryPrice()),
            () -> assertEquals(bookOrder.getOrderAt(), result.getOrderAt()),
            () -> assertEquals(bookOrder.getShipDate(), result.getShipDate())
        );
    }

    @Test
    @DisplayName("주문 조회 - 주문 코드")
    void testFindOrderByOrderCode() {
        OrderResponseDto result = bookOrderRepository.findOrderByOrderCode("orderCode")
            .orElse(null);

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(bookOrder.getOrderCode(), result.getOrderCode()),
            () -> assertEquals(bookOrder.getOrderName(), result.getOrderName()),
            () -> assertEquals(bookOrder.getReceiver(), result.getReceiverName()),
            () -> assertEquals(bookOrder.getReceiverNumber(), result.getReceiverPhoneNumber()),
            () -> assertEquals(bookOrder.getZipCode(), result.getZipCode()),
            () -> assertEquals(bookOrder.getAddress(), result.getAddress()),
            () -> assertEquals(bookOrder.getAddressDetail(), result.getDetailAddress()),
            () -> assertEquals(bookOrder.getOrderer(), result.getSenderName()),
            () -> assertEquals(bookOrder.getOrdererNumber(), result.getSenderPhoneNumber()),
            () -> assertEquals(bookOrder.getOrdererEmail(), result.getSenderEmail()),
            () -> assertEquals(bookOrder.getDeliveryDate(), result.getDeliveryDate()),
            () -> assertEquals(bookOrder.getUsePoint(), result.getUsePoint()),
            () -> assertEquals(bookOrder.getTotalPrice(), result.getPayAmount()),
            () -> assertEquals(bookOrder.getDeliveryPrice(), result.getDeliveryPrice()),
            () -> assertEquals(bookOrder.getOrderAt(), result.getOrderAt()),
            () -> assertEquals(bookOrder.getShipDate(), result.getShipDate())
        );
    }

    @Test
    @DisplayName("전체 목록 조회")
    void testFindOrderBy() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<OrderReadResponseDto> page = bookOrderRepository.findOrderBy(pageable);

        List<OrderReadResponseDto> result = page.getContent();

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(bookOrder.getOrderId(), result.get(0).getOrderId()),
            () -> assertEquals(bookOrder.getOrderCode(), result.get(0).getOrderCode()),
            () -> assertEquals(bookOrder.getOrderName(), result.get(0).getOrderName()),
            () -> assertEquals(bookOrder.getOrderAt(), result.get(0).getOrderAt()),
            () -> assertEquals(bookOrder.getDeliveryDate(), result.get(0).getDeliveryDate()),
            () -> assertEquals(bookOrder.getTotalPrice(), result.get(0).getTotalPrice())
        );
    }

    @Test
    @DisplayName("회원 주문 가격 조회")
    void testFindUsedPointByOrderId() {
        OrderedMemberReadResponseDto result = bookOrderRepository.findUsedPointByOrderId(
            bookOrder.getOrderId());

        assertAll(
            () -> assertEquals(member.getMemberId(), result.getMemberId()),
            () -> assertEquals(bookOrder.getTotalPrice(), result.getTotalPrice())
        );
    }
}