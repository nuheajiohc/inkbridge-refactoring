package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.PointPolicy;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * class: PointPolicyRepositoryTest.
 *
 * @author jangjaehun
 * @version 2024/02/17
 */
@DataJpaTest
class PointPolicyRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PointPolicyRepository pointPolicyRepository;
    PointPolicy pointPolicy;
    PointPolicyType pointPolicyType;

    @BeforeEach
    void setup() {
        pointPolicyType = PointPolicyType.builder()
            .pointPolicyTypeId(1)
            .policyType("REGISTER")
            .build();

        entityManager.persist(pointPolicyType);

        pointPolicy = PointPolicy.builder()
            .accumulatePoint(1000L)
            .createdAt(LocalDate.of(2024, 1, 1))
            .pointPolicyType(pointPolicyType)
            .build();

        pointPolicy = entityManager.persist(pointPolicy);
    }

    @Test
    @DisplayName("포인트 정책 전체 조회 테스트")
    void testFindAllPointPolicyBy() {
        List<PointPolicyAdminReadResponseDto> responseDtoList = pointPolicyRepository.findAllPointPolicyBy();

        assertAll(
            () -> assertEquals(1, responseDtoList.size()),
            () -> assertEquals(pointPolicy.getPointPolicyId(),
                responseDtoList.get(0).getPointPolicyId()),
            () -> assertEquals(pointPolicy.getPointPolicyType().getPolicyType(),
                responseDtoList.get(0).getPolicyType()),
            () -> assertEquals(pointPolicy.getAccumulatePoint(),
                responseDtoList.get(0).getAccumulatePoint()),
            () -> assertEquals(pointPolicy.getCreatedAt(), responseDtoList.get(0).getCreatedAt())
        );
    }

    @Test
    @DisplayName("포인트 정책 유형 조회 테스트")
    void testFindAllPointPolicyByTypeId() {
        PointPolicyType reviewPointPolicyType = PointPolicyType.builder()
            .pointPolicyTypeId(2)
            .policyType("REVIEW")
            .build();

        entityManager.persist(reviewPointPolicyType);

        PointPolicy updatePointPolicy = PointPolicy.builder()
            .accumulatePoint(1500L)
            .createdAt(LocalDate.of(2024, 1, 2))
            .pointPolicyType(pointPolicyType)
            .build();

        entityManager.persist(updatePointPolicy);

        PointPolicy reviewPointPolicy = PointPolicy.builder()
            .accumulatePoint(1000L)
            .pointPolicyType(reviewPointPolicyType)
            .createdAt(LocalDate.of(2024, 1, 2))
            .build();

        entityManager.persist(reviewPointPolicy);

        List<PointPolicyAdminReadResponseDto> result = pointPolicyRepository.findAllPointPolicyByTypeId(1);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("적용중인 포인트 정책 목록 조회 테스트")
    void testFindAllCurrentPointPolicies() {
        PointPolicyType reviewPointPolicyType = PointPolicyType.builder()
            .pointPolicyTypeId(2)
            .policyType("REVIEW")
            .build();

        entityManager.persist(reviewPointPolicyType);

        PointPolicy updatePointPolicy = PointPolicy.builder()
            .accumulatePoint(1500L)
            .createdAt(LocalDate.of(2024, 1, 2))
            .pointPolicyType(pointPolicyType)
            .build();

        entityManager.persist(updatePointPolicy);

        PointPolicy reviewPointPolicy = PointPolicy.builder()
            .accumulatePoint(1000L)
            .pointPolicyType(reviewPointPolicyType)
            .createdAt(LocalDate.of(2024, 1, 2))
            .build();

        entityManager.persist(reviewPointPolicy);

        List<PointPolicyAdminReadResponseDto> result = pointPolicyRepository.findAllCurrentPointPolicies();

        assertAll(
            () -> assertEquals(2, result.size()),
            () -> assertEquals(1500L, result.get(0).getAccumulatePoint()),
            () -> assertEquals("REGISTER", result.get(0).getPolicyType())
        );
    }

    @Test
    @DisplayName("포인트 정책 유형 적용 정책 조회")
    void testFindAllCurrentPointPolicyByTypeId() {
        PointPolicy updatePointPolicy = PointPolicy.builder()
            .accumulatePoint(1500L)
            .createdAt(LocalDate.of(2024, 1, 2))
            .pointPolicyType(pointPolicyType)
            .build();

        entityManager.persist(updatePointPolicy);

        PointPolicyReadResponseDto result = pointPolicyRepository.findCurrentPointPolicy(1);

        assertAll(
            () -> assertEquals(1500L, result.getAccumulatePoint()),
            () -> assertEquals("REGISTER", result.getPolicyType())
        );
    }
}