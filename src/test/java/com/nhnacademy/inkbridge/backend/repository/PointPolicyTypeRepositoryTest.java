package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * class: PointPolicyTypeRepositoryTest.
 *
 * @author jangjaehun
 * @version 2024/02/17
 */
@DataJpaTest
class PointPolicyTypeRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PointPolicyTypeRepository pointPolicyTypeRepository;

    PointPolicyType pointPolicyType;

    @BeforeEach
    void setup() {
        pointPolicyType = PointPolicyType.builder()
            .pointPolicyTypeId(1)
            .policyType("REGISTER")
            .build();

        entityManager.persist(pointPolicyType);
        entityManager.flush();
    }

    @Test
    void testFindAllPointPolicyTypeBy() {
        List<PointPolicyTypeReadResponseDto> responseDtoList = pointPolicyTypeRepository.findAllPointPolicyTypeBy();

        assertAll(
            () -> assertEquals(1, responseDtoList.size()),
            () -> assertEquals(pointPolicyType.getPointPolicyTypeId(), responseDtoList.get(0).getPointPolicyTypeId()),
            () -> assertEquals(pointPolicyType.getPolicyType(), responseDtoList.get(0).getPolicyType())
        );
    }

    @Test
    @DisplayName("existByPolicyType - 유형이 존재하지 않는 경우")
    void testExistsByPolicyType_result_false() {
        boolean result = pointPolicyTypeRepository.existsByPolicyType("REVIEW");

        assertFalse(result);
    }

    @Test
    @DisplayName("existByPolicyType - 유형이 존재하는 경우")
    void testExistsByPolicyType_result_true() {
        boolean result = pointPolicyTypeRepository.existsByPolicyType("REGISTER");

        assertTrue(result);
    }

    @Test
    void testCountAllBy() {
        Integer result = pointPolicyTypeRepository.countAllBy();

        assertEquals(1, result);
    }
}