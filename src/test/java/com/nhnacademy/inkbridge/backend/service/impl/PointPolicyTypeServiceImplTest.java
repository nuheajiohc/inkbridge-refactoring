package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyTypeRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * class: PointPolicyTypeServiceImplTest.
 *
 * @author jangjaehun
 * @version 2024/02/17
 */
@ExtendWith(MockitoExtension.class)
class PointPolicyTypeServiceImplTest {

    @InjectMocks
    PointPolicyTypeServiceImpl pointPolicyTypeService;

    @Mock
    PointPolicyTypeRepository pointPolicyTypeRepository;

    @Mock
    PointPolicyRepository pointPolicyRepository;

    @Test
    @DisplayName("포인트 정책 유형 조회 테스트")
    void testGetPointPolicyTypes() {
        PointPolicyTypeReadResponseDto responseDto = new PointPolicyTypeReadResponseDto(1, "REGISTER");

        List<PointPolicyTypeReadResponseDto> list = List.of(responseDto);

        given(pointPolicyTypeRepository.findAllPointPolicyTypeBy()).willReturn(list);

        List<PointPolicyTypeReadResponseDto> actual = pointPolicyTypeService.getPointPolicyTypes();

        assertEquals(list, actual);

        verify(pointPolicyTypeRepository, times(1)).findAllPointPolicyTypeBy();
    }

    @Test
    @DisplayName("포인트 정책 유형 생성 - 존재하는 포인트 유형")
    void testCreatePointPolicyType_already_exist() {
        PointPolicyTypeCreateRequestDto requestDto = new PointPolicyTypeCreateRequestDto();
        requestDto.setPolicyType("REGISTER");

        given(pointPolicyTypeRepository.existsByPolicyType("REGISTER")).willReturn(true);

        assertThrows(AlreadyExistException.class,
            () -> pointPolicyTypeService.createPointPolicyType(requestDto));

        verify(pointPolicyTypeRepository, times(1)).existsByPolicyType("REGISTER");
    }

    @Test
    @DisplayName("포인트 정책 유형 생성 - 성공")
    void testCreatePointPolicyType_success() {
        PointPolicyTypeCreateRequestDto requestDto = new PointPolicyTypeCreateRequestDto();
        requestDto.setPolicyType("REGISTER");

        given(pointPolicyTypeRepository.existsByPolicyType("REGISTER")).willReturn(false);

        pointPolicyTypeService.createPointPolicyType(requestDto);

        verify(pointPolicyTypeRepository, times(1)).existsByPolicyType("REGISTER");
        verify(pointPolicyTypeRepository, times(1)).save(any());
        verify(pointPolicyRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("포인트 정책 유형 수정 - 수정할 정책 유형이 존재하지 않는 경우")
    void testUpdatePointPolicyType_not_found() {
        PointPolicyTypeUpdateRequestDto requestDto = new PointPolicyTypeUpdateRequestDto();
        requestDto.setPointPolicyTypeId(1);
        requestDto.setPolicyType("REGISTER");

        given(pointPolicyTypeRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pointPolicyTypeService.updatePointPolicyType(requestDto));

        verify(pointPolicyTypeRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("포인트 정책 유형 수정 - 성공")
    void testUpdatePointPolicyType_success() {
        PointPolicyTypeUpdateRequestDto requestDto = new PointPolicyTypeUpdateRequestDto();
        requestDto.setPointPolicyTypeId(1);
        requestDto.setPolicyType("REGISTER");

        PointPolicyType pointPolicyType = PointPolicyType.builder()
            .pointPolicyTypeId(1)
            .policyType("REVIEW").build();

        given(pointPolicyTypeRepository.findById(1)).willReturn(Optional.of(pointPolicyType));

        pointPolicyTypeService.updatePointPolicyType(requestDto);

        verify(pointPolicyTypeRepository, times(1)).findById(1);
        verify(pointPolicyTypeRepository, times(1)).save(pointPolicyType);
    }

    @Test
    @DisplayName("포인트 정책 유형 삭제 - 삭제할 포인트 정책 유형이 존재하지 않는 경우")
    void testDeletePointPolicyTypeById_not_found() {
        given(pointPolicyTypeRepository.existsById(1)).willReturn(false);

        assertThrows(NotFoundException.class, () -> pointPolicyTypeService.deletePointPolicyTypeById(1));

        verify(pointPolicyTypeRepository, times(1)).existsById(1);
    }

    @Test
    @DisplayName("포인트 정책 유형 삭제 - 성공")
    void testDeletePointPolicyTypeById_success() {
        given(pointPolicyTypeRepository.existsById(1)).willReturn(true);

        pointPolicyTypeService.deletePointPolicyTypeById(1);

        verify(pointPolicyTypeRepository, times(1)).existsById(1);
        verify(pointPolicyTypeRepository, times(1)).deleteById(1);
    }


}