package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.address.AddressCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.address.MemberAddressReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.address.AddressUpdateRequestDto;
import java.util.List;

/**
 * class: MemberAddressService.
 *
 * @author jeongbyeonghun
 * @version 3/9/24
 */
public interface MemberAddressService {

    /**
     * 사용자 ID에 해당하는 모든 주소 정보를 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 조회된 주소 정보 리스트
     */
    List<MemberAddressReadResponseDto> getAddressByUserId(Long userId);

    /**
     * 새로운 주소 정보를 생성합니다.
     *
     * @param userId 주소를 생성할 사용자의 ID
     * @param addressCreateRequestDto 생성할 주소의 정보
     */
    void createAddress(Long userId, AddressCreateRequestDto addressCreateRequestDto);

    /**
     * 주소 정보를 업데이트합니다.
     *
     * @param userId 업데이트할 주소의 사용자 ID
     * @param addressUpdateRequestDto 업데이트할 주소 정보
     */
    void updateAddress(Long userId, AddressUpdateRequestDto addressUpdateRequestDto);

    /**
     * 주소 정보를 삭제합니다.
     *
     * @param userId 삭제할 주소의 사용자 ID
     * @param addressId 삭제할 주소의 ID
     */
    void deleteAddress(Long userId, Long addressId);

    /**
     * 사용자 ID와 주소 ID에 해당하는 주소 정보를 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @param addressId 조회할 주소의 ID
     * @return 조회된 주소 정보
     */
    MemberAddressReadResponseDto getAddressByUserIdAndAddressId(Long userId, Long addressId);
}
