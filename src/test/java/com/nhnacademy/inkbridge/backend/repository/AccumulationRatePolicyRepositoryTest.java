package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.AccumulationRatePolicy;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * class: AccumulationRatePolicyRepositoryTest.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
class AccumulationRatePolicyRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AccumulationRatePolicyRepository accumulationRatePolicyRepository;

    AccumulationRatePolicy accumulationRatePolicy;

    @BeforeEach
    void setup() {
        accumulationRatePolicy = AccumulationRatePolicy.builder()
            .accumulationRate(5)
            .createdAt(LocalDate.of(2024, 1, 1))
            .build();

        accumulationRatePolicy = entityManager.persist(accumulationRatePolicy);
    }

    @Test
    @DisplayName("전체 적립율 정책 조회")
    @Order(2)
    void testFindAllAccumulationRatePolicy() {
        List<AccumulationRatePolicyAdminReadResponseDto> result =
            accumulationRatePolicyRepository.findAllAccumulationRatePolicies();

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(accumulationRatePolicy.getAccumulationRatePolicyId(), result.get(0).getAccumulationRatePolicyId()),
            () -> assertEquals(accumulationRatePolicy.getAccumulationRate(), result.get(0).getAccumulationRate()),
            () -> assertEquals(accumulationRatePolicy.getCreatedAt(), result.get(0).getCreatedAt())
        );
    }

    @Test
    @DisplayName("적립율 정책 id로 정책 조회")
    @Order(1)
    void testFindAccumulationRatePolicy() {
        AccumulationRatePolicyReadResponseDto result =
            accumulationRatePolicyRepository.findAccumulationRatePolicy(1L);

        assertAll(
            () -> assertEquals(accumulationRatePolicy.getAccumulationRatePolicyId(), result.getAccumulationRatePolicyId()),
            () -> assertEquals(accumulationRatePolicy.getAccumulationRate(), result.getAccumulationRate())
        );
    }

    @Test
    @DisplayName("현재 적용중인 적립율 정책 조회")
    @Order(3)
    void testFindCurrentAccumulationRatePolicy() {
        AccumulationRatePolicyReadResponseDto result =
            accumulationRatePolicyRepository.findCurrentAccumulationRatePolicy();

        assertAll(
            () -> assertEquals(accumulationRatePolicy.getAccumulationRatePolicyId(), result.getAccumulationRatePolicyId()),
            () -> assertEquals(accumulationRatePolicy.getAccumulationRate(), result.getAccumulationRate())
        );
    }
}