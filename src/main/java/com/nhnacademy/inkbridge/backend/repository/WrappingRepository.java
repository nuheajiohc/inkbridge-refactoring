package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.dto.order.WrappingResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Wrapping;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: WrappingRepository.
 *
 * @author JBum
 * @version 2024/02/28
 */
public interface WrappingRepository extends JpaRepository<Wrapping, Long> {

    /**
     * 모든 wrapping을 가져오는 메소드.
     *
     * @param isActive 활성여부
     * @return 모든 wrapping dto
     */
    List<WrappingResponseDto> findAllByIsActive(boolean isActive);

    /**
     * 선택한 wrapper를 가져오는 메소드.
     *
     * @param wrappingId 선택할 wrappingId
     * @return 선택된 wrapperId의 wrapping dto
     */
    Optional<WrappingResponseDto> findByWrappingId(Long wrappingId);
}
