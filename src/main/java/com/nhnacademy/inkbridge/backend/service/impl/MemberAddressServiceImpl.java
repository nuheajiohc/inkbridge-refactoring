package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.address.AddressCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.address.AddressUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.address.MemberAddressReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.GeneralAddress;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAddress;
import com.nhnacademy.inkbridge.backend.enums.AddressMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.GeneralAddressRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberAddressRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.service.MemberAddressService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: MemberAddressServiceImpl.
 *
 * @author jeongbyeonghun
 * @version 3/9/24
 */

@Service
@RequiredArgsConstructor
public class MemberAddressServiceImpl implements MemberAddressService {

    private final MemberAddressRepository memberAddressRepository;
    private final MemberRepository memberRepository;
    private final GeneralAddressRepository generalAddressRepository;

    /**
     * 사용자 ID에 해당하는 모든 주소 정보를 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 조회된 주소 정보 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<MemberAddressReadResponseDto> getAddressByUserId(Long userId) {
        return memberAddressRepository.findAllByMemberMemberId(userId).stream()
            .map(MemberAddressReadResponseDto::toDto).collect(
                Collectors.toList());
    }

    /**
     * 새로운 주소 정보를 생성합니다.
     *
     * @param userId 주소를 생성할 사용자의 ID
     * @param addressCreateRequestDto 생성할 주소의 정보
     */
    @Override
    @Transactional
    public void createAddress(Long userId, AddressCreateRequestDto addressCreateRequestDto) {
        Member user = memberRepository.findById(userId).orElseThrow(() -> new NotFoundException(
            MemberMessageEnum.MEMBER_NOT_FOUND.name()));
        GeneralAddress generalAddress = generalAddressRepository.findByZipCodeAndAddress(
                addressCreateRequestDto.getZipCode(), addressCreateRequestDto.getAddress())
            .orElseGet(
                () -> generalAddressRepository.save(addressCreateRequestDto.toGeneralAddress()));
        MemberAddress memberAddress = addressCreateRequestDto.toEntity(user, generalAddress);
        memberAddressRepository.save(memberAddress);
    }

    /**
     * 주소 정보를 업데이트합니다.
     *
     * @param userId 업데이트할 주소의 사용자 ID
     * @param addressUpdateRequestDto 업데이트할 주소 정보
     */
    @Override
    @Transactional
    public void updateAddress(Long userId, AddressUpdateRequestDto addressUpdateRequestDto) {
        Member user = memberRepository.findById(userId).orElseThrow(() -> new NotFoundException(
            MemberMessageEnum.MEMBER_NOT_FOUND.name()));
        GeneralAddress generalAddress = generalAddressRepository.findByZipCodeAndAddress(
                addressUpdateRequestDto.getZipCode(), addressUpdateRequestDto.getAddress())
            .orElseGet(
                () -> generalAddressRepository.save(addressUpdateRequestDto.toGeneralAddress()));
        MemberAddress memberAddress = memberAddressRepository.findByMemberAndAddressId(user,
            addressUpdateRequestDto.getAddressId()).orElseThrow(() -> new NotFoundException(
            AddressMessageEnum.ADDRESS_NOT_FOUND_ERROR.getMessage()));

        memberAddress.update(generalAddress, addressUpdateRequestDto);
    }
     /**
     * 주소 정보를 삭제합니다.
     *
     * @param userId 삭제할 주소의 사용자 ID
     * @param addressId 삭제할 주소의 ID
     */
    @Override
    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        MemberAddress memberAddress = memberAddressRepository.findByMemberMemberIdAndAddressId(
            userId, addressId).orElseThrow(
            () -> new NotFoundException(AddressMessageEnum.ADDRESS_NOT_FOUND_ERROR.getMessage()));
        memberAddressRepository.delete(memberAddress);
    }


    /**
     * 사용자 ID와 주소 ID에 해당하는 주소 정보를 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @param addressId 조회할 주소의 ID
     * @return 조회된 주소 정보
     */
    @Override
    @Transactional(readOnly = true)
    public MemberAddressReadResponseDto getAddressByUserIdAndAddressId(Long userId,
        Long addressId) {
        return MemberAddressReadResponseDto.toDto(
            memberAddressRepository.findByMemberMemberIdAndAddressId(userId, addressId).orElseThrow(
                () -> new NotFoundException(
                    AddressMessageEnum.ADDRESS_NOT_FOUND_ERROR.getMessage())));
    }
}
