package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nhnacademy.inkbridge.backend.dto.pay.PayReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAuth;
import com.nhnacademy.inkbridge.backend.entity.MemberGrade;
import com.nhnacademy.inkbridge.backend.entity.MemberStatus;
import com.nhnacademy.inkbridge.backend.entity.Pay;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * class: PayRepositoryTest.
 *
 * @author jangjaehun
 * @version 2024/03/21
 */
@DataJpaTest
class PayRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PayRepository payRepository;

    Pay pay;
    BookOrder bookOrder;
    Member member;
    MemberGrade memberGrade;
    MemberStatus memberStatus;
    MemberAuth memberAuth;

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

        pay = Pay.builder()
            .paymentKey("결제 키")
            .method("간편 결제")
            .status("DONE")
            .requestedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
            .approvedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
            .totalAmount(30000L)
            .balanceAmount(30000L)
            .vat(3000L)
            .isPartialCancelable(true)
            .provider("toss")
            .order(bookOrder)
            .build();

        pay = entityManager.persist(pay);
    }

    @Test
    @DisplayName("결제 정보 조회 - 결제 번호")
    void testFindPayByPayID() {
        PayReadResponseDto result = payRepository.findPayByPayId(pay.getPayId()).orElse(null);

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(pay.getPaymentKey(), result.getPaymentKey()),
            () -> assertEquals(pay.getMethod(), result.getMethod()),
            () -> assertEquals(pay.getStatus(), result.getStatus()),
            () -> assertEquals(pay.getRequestedAt(), result.getRequestedAt()),
            () -> assertEquals(pay.getTotalAmount(), result.getTotalAmount()),
            () -> assertEquals(pay.getBalanceAmount(), result.getBalanceAmount()),
            () -> assertEquals(pay.getVat(), result.getVat()),
            () -> assertEquals(pay.getIsPartialCancelable(), result.getIsPartialCancelable()),
            () -> assertEquals(pay.getProvider(), result.getProvider())
        );
    }

    @Test
    @DisplayName("결제 정보 조회 - 주문 번호")
    void testFindPayByOrderId() {
        PayReadResponseDto result = payRepository.findPayByOrderId(bookOrder.getOrderId());

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(pay.getPaymentKey(), result.getPaymentKey()),
            () -> assertEquals(pay.getMethod(), result.getMethod()),
            () -> assertEquals(pay.getStatus(), result.getStatus()),
            () -> assertEquals(pay.getRequestedAt(), result.getRequestedAt()),
            () -> assertEquals(pay.getTotalAmount(), result.getTotalAmount()),
            () -> assertEquals(pay.getBalanceAmount(), result.getBalanceAmount()),
            () -> assertEquals(pay.getVat(), result.getVat()),
            () -> assertEquals(pay.getIsPartialCancelable(), result.getIsPartialCancelable()),
            () -> assertEquals(pay.getProvider(), result.getProvider())
        );
    }

    @Test
    @DisplayName("결제 정보 조회 - 주문 코드")
    void testFindPayByOrderCode() {
        PayReadResponseDto result = payRepository.findPayByOrderCode(bookOrder.getOrderCode());

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(pay.getPaymentKey(), result.getPaymentKey()),
            () -> assertEquals(pay.getMethod(), result.getMethod()),
            () -> assertEquals(pay.getStatus(), result.getStatus()),
            () -> assertEquals(pay.getRequestedAt(), result.getRequestedAt()),
            () -> assertEquals(pay.getTotalAmount(), result.getTotalAmount()),
            () -> assertEquals(pay.getBalanceAmount(), result.getBalanceAmount()),
            () -> assertEquals(pay.getVat(), result.getVat()),
            () -> assertEquals(pay.getIsPartialCancelable(), result.getIsPartialCancelable()),
            () -> assertEquals(pay.getProvider(), result.getProvider())
        );
    }

    @Test
    @DisplayName("결제 정보 조회")
    void testFindByOrderCode() {
        Optional<Pay> result = payRepository.findByOrderCode(bookOrder.getOrderCode());
        Pay resultPay = result.orElse(null);

        assertAll(
            () -> assertTrue(result.isPresent()),
            () -> assertEquals(pay, resultPay)
        );
    }
}