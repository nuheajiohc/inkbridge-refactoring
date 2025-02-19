package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.DeliveryPolicy;
import com.nhnacademy.inkbridge.backend.repository.DeliveryPolicyRepository;
import com.nhnacademy.inkbridge.backend.service.DeliveryPolicyService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: DeliveryServiceImpl.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
@Service
@RequiredArgsConstructor
public class DeliveryPolicyServiceImpl implements DeliveryPolicyService {

    private final DeliveryPolicyRepository deliveryPolicyRepository;

    /**
     * {@inheritDoc}
     *
     * @return List - DeliveryPolicyReadResponseDto
     */
    @Transactional(readOnly = true)
    @Override
    public List<DeliveryPolicyAdminReadResponseDto> getDeliveryPolicies() {
        return deliveryPolicyRepository.findAllDeliveryPolicyBy();
    }

    /**
     * {@inheritDoc}
     *
     * @return DeliveryPolicyReadResponseDto
     */
    @Transactional(readOnly = true)
    @Override
    public DeliveryPolicyReadResponseDto getCurrentDeliveryPolicy() {
        return deliveryPolicyRepository.findCurrentPolicy();
    }

    /**
     * {@inheritDoc}
     *
     * @param deliveryPolicyCreateRequestDto DeliveryPolicyCreateRequestDto
     */
    @Transactional
    @Override
    public void createDeliveryPolicy(
        DeliveryPolicyCreateRequestDto deliveryPolicyCreateRequestDto) {

        DeliveryPolicy deliveryPolicy = DeliveryPolicy.builder()
            .deliveryPrice(deliveryPolicyCreateRequestDto.getDeliveryPrice())
            .createdAt(LocalDate.now())
            .freeDeliveryPrice(deliveryPolicyCreateRequestDto.getFreeDeliveryPrice())
            .build();

        deliveryPolicyRepository.save(deliveryPolicy);
    }
}
