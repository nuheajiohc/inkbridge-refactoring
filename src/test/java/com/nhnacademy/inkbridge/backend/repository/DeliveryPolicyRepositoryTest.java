package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.DeliveryPolicy;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * class: DeliveryPolicyRepositoryTest.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
class DeliveryPolicyRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    DeliveryPolicyRepository deliveryPolicyRepository;

    DeliveryPolicy deliveryPolicy;

    @BeforeEach
    void setup() {
        deliveryPolicy = DeliveryPolicy.builder()
            .deliveryPrice(1000L)
            .createdAt(LocalDate.of(2024, 1, 1))
            .freeDeliveryPrice(50000L)
            .build();

        deliveryPolicy = entityManager.persist(deliveryPolicy);
    }

    @Test
    @DisplayName("배송비 정책 전체 조회")
    void testFindAllDeliveryPolicyBy() {
        List<DeliveryPolicyAdminReadResponseDto> result = deliveryPolicyRepository.findAllDeliveryPolicyBy();

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(deliveryPolicy.getDeliveryPolicyId(),
                result.get(0).getDeliveryPolicyId()),
            () -> assertEquals(deliveryPolicy.getDeliveryPrice(), result.get(0).getDeliveryPrice()),
            () -> assertEquals(deliveryPolicy.getCreatedAt(), result.get(0).getCreatedAt()),
            () -> assertEquals(deliveryPolicy.getFreeDeliveryPrice(),
                result.get(0).getFreeDeliveryPrice())
        );
    }

    @Test
    @DisplayName("현재 적용되는 배송비 정책 조회")
    void testFindCurrentPolicy() {
        DeliveryPolicy currentPolicy = DeliveryPolicy.builder()
            .deliveryPrice(1500L)
            .createdAt(LocalDate.of(2024, 1, 2))
            .freeDeliveryPrice(60000L)
            .build();

        entityManager.persist(currentPolicy);

        DeliveryPolicyReadResponseDto result = deliveryPolicyRepository.findCurrentPolicy();

        assertAll(
            () -> assertEquals(currentPolicy.getDeliveryPolicyId(), result.getDeliveryPolicyId()),
            () -> assertEquals(currentPolicy.getDeliveryPrice(), result.getDeliveryPrice()),
            () -> assertEquals(currentPolicy.getFreeDeliveryPrice(), result.getFreeDeliveryPrice())
        );
    }
}