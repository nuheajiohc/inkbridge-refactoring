package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.PointPolicy;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyTypeRepository;
import com.nhnacademy.inkbridge.backend.service.PointPolicyTypeService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: PointPolicyTypeServiceImpl.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@Service
@RequiredArgsConstructor
public class PointPolicyTypeServiceImpl implements PointPolicyTypeService {

    private final PointPolicyTypeRepository pointPolicyTypeRepository;
    private final PointPolicyRepository pointPolicyRepository;

    /**
     * {@inheritDoc}
     *
     * @return List - PointPolicyTypeReadResponseDto
     */
    @Transactional(readOnly = true)
    @Override
    public List<PointPolicyTypeReadResponseDto> getPointPolicyTypes() {
        return pointPolicyTypeRepository.findAllPointPolicyTypeBy();
    }

    /**
     * {@inheritDoc}
     *
     * @param pointPolicyTypeCreateRequestDto PointPolicyTypeCreateRequestDto
     * @throws AlreadyExistException 정책 유형이 이미 존재하는 경우
     */
    @Transactional
    @Override
    public void createPointPolicyType(
        PointPolicyTypeCreateRequestDto pointPolicyTypeCreateRequestDto) {

        if (pointPolicyTypeRepository.existsByPolicyType(
            pointPolicyTypeCreateRequestDto.getPolicyType())) {
            throw new AlreadyExistException(
                PointPolicyMessageEnum.POINT_POLICY_TYPE_ALREADY_EXIST.getMessage());
        }

        PointPolicyType pointPolicyType = PointPolicyType.builder()
            .pointPolicyTypeId(Math.addExact(pointPolicyTypeRepository.countAllBy(), 1))
            .policyType(pointPolicyTypeCreateRequestDto.getPolicyType())
            .build();

        PointPolicy pointPolicy = PointPolicy.builder()
            .accumulatePoint(pointPolicyTypeCreateRequestDto.getAccumulatePoint())
            .createdAt(LocalDate.now())
            .pointPolicyType(pointPolicyType)
            .build();

        pointPolicyTypeRepository.save(pointPolicyType);
        pointPolicyRepository.save(pointPolicy);
    }

    /**
     * {@inheritDoc}
     *
     * @param pointPolicyTypeUpdateRequestDto PointPolicyTypeUpdateRequestDto
     * @throws NotFoundException 수정할 정책 유형이 존재하지 않을 경우
     */
    @Transactional
    @Override
    public void updatePointPolicyType(
        PointPolicyTypeUpdateRequestDto pointPolicyTypeUpdateRequestDto) {

        PointPolicyType pointPolicyType = pointPolicyTypeRepository.findById(
                pointPolicyTypeUpdateRequestDto.getPointPolicyTypeId())
            .orElseThrow(() -> new NotFoundException(
                PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage()));

        pointPolicyType.setPointPolicyType(pointPolicyTypeUpdateRequestDto);

        pointPolicyTypeRepository.save(pointPolicyType);
    }

    /**
     * {@inheritDoc}
     *
     * @param pointPolicyTypeId Integer
     * @throws NotFoundException 삭제할 포인트 정책 유형이 존재하지 않을 경우
     */
    @Transactional
    @Override
    public void deletePointPolicyTypeById(Integer pointPolicyTypeId) {
        if (!pointPolicyTypeRepository.existsById(pointPolicyTypeId)) {
            throw new NotFoundException(PointPolicyMessageEnum.POINT_POLICY_NOT_FOUND.getMessage());
        }

        pointPolicyTypeRepository.deleteById(pointPolicyTypeId);
    }
}
