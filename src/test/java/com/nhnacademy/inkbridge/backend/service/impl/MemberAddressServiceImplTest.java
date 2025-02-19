package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.address.AddressCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.address.AddressUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.address.MemberAddressReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.GeneralAddress;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAddress;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.GeneralAddressRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberAddressRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * class: MemberAddressServiceImplTest.
 *
 * @author jeongbyeonghun
 * @version 3/13/24
 */

@ExtendWith(MockitoExtension.class)
class MemberAddressServiceImplTest {

    @Mock
    private MemberAddressRepository memberAddressRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private GeneralAddressRepository generalAddressRepository;

    @InjectMocks
    private MemberAddressServiceImpl memberAddressService;

    private static Long memberId;
    private static Member member;

    private static Long addressId;
    private static String zipCode;
    private static GeneralAddress generalAddress;
    private static String address;
    private static Long generalAddressId;
    private static String alias;
    private static String addressDetail;
    private static String receiverName;
    private static String receiverNumber;
    private static MemberAddress memberAddress;


    @BeforeAll
    static void setUp() {
        // 정적 변수들에 대한 가상의 데이터 할당
        memberId = 1L;
        address = "123 Main St, Anytown, AN";
        generalAddressId = 1L;
        alias = "My Home";
        addressDetail = "Apartment 101";
        receiverName = "Jane Doe";
        receiverNumber = "01012345678";
        zipCode = "12345";
        addressId = 1L;

        member = Member.create()
            .memberPoint(memberId)
            .build();

        generalAddress = GeneralAddress.builder()
            .generalAddressId(generalAddressId)
            .zipCode(zipCode)
            .address(address)
            .build();

        memberAddress = MemberAddress.builder()
            .addressId(addressId)
            .alias(alias)
            .addressDetail(addressDetail)
            .receiverName(receiverName)
            .receiverNumber(receiverNumber)
            .member(member)
            .generalAddress(generalAddress)
            .build();

    }

    @Test
    void getAddressByUserIdShouldReturnAddressList() {
        List<MemberAddress> memberAddressList = new ArrayList<>();
        memberAddressList.add(memberAddress);

        when(memberAddressRepository.findAllByMemberMemberId(memberId))
            .thenReturn(memberAddressList);

        List<MemberAddressReadResponseDto> result = memberAddressService.getAddressByUserId(
            memberId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(memberAddress.getAddressId(), result.get(0).getAddressId());
        assertEquals(memberAddress.getGeneralAddress().getAddress(), result.get(0).getAddress());
        assertEquals(memberAddress.getAddressDetail(), result.get(0).getAddressDetail());
        assertEquals(memberAddress.getGeneralAddress().getZipCode(), result.get(0).getZipCode());
        assertEquals(memberAddress.getAlias(), result.get(0).getAlias());
        assertEquals(memberAddress.getReceiverName(), result.get(0).getReceiverName());
        assertEquals(memberAddress.getReceiverNumber(), result.get(0).getReceiverNumber());
    }

    @Test
    void createAddress() {
        AddressCreateRequestDto requestDto = AddressCreateRequestDto.builder().zipCode(zipCode)
            .address(address).addressDetail(addressDetail).receiverName(receiverName)
            .receiverNumber(receiverNumber).alias(alias).build();

        when(memberRepository.findById(memberId)).thenReturn(java.util.Optional.of(member));
        when(generalAddressRepository.findByZipCodeAndAddress(zipCode, address)).thenReturn(
            Optional.empty());

        memberAddressService.createAddress(memberId, requestDto);

        verify(memberAddressRepository).save(any(MemberAddress.class));

    }

    @Test
    void createAddressWhenMemberNotFound() {
        AddressCreateRequestDto requestDto = new AddressCreateRequestDto();

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> memberAddressService.createAddress(memberId, requestDto));
    }


    @Test
    void getAddressByUserIdAndAddressIdShouldThrowExceptionWhenNotFound() {
        when(memberAddressRepository.findByMemberMemberIdAndAddressId(memberId, addressId))
            .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> memberAddressService.getAddressByUserIdAndAddressId(memberId, addressId));

        verify(memberAddressRepository).findByMemberMemberIdAndAddressId(memberId, addressId);
    }

    @Test
    void updateAddress() {

        AddressUpdateRequestDto requestDto = AddressUpdateRequestDto.builder()
            .address(address).zipCode(zipCode).addressDetail(addressDetail).addressId(addressId)
            .alias(alias).receiverName(receiverName).receiverNumber(receiverNumber).build();

        when(memberRepository.findById(memberId)).thenReturn(java.util.Optional.of(member));
        when(memberAddressRepository.findByMemberAndAddressId(member, addressId)).thenReturn(
            java.util.Optional.of(memberAddress));
        when(generalAddressRepository.findByZipCodeAndAddress(zipCode, address)).thenReturn(
            java.util.Optional.empty());

        assertDoesNotThrow(() -> memberAddressService.updateAddress(memberId, requestDto));

        verify(memberRepository).findById(memberId);
        verify(memberAddressRepository).findByMemberAndAddressId(member, addressId);
        verify(generalAddressRepository).findByZipCodeAndAddress(zipCode, address);
        verify(generalAddressRepository).save(any(GeneralAddress.class));

    }

    @Test
    void updateAddressWhenMemberNotFound() {
        AddressUpdateRequestDto requestDto = new AddressUpdateRequestDto();

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> memberAddressService.updateAddress(memberId, requestDto));
    }

    @Test
    void updateAddressWhenAddressNotFound() {
        AddressUpdateRequestDto requestDto = AddressUpdateRequestDto.builder().addressId(addressId)
            .build();

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(memberAddressRepository.findByMemberAndAddressId(member, addressId)).thenReturn(
            Optional.empty());

        assertThrows(NotFoundException.class,
            () -> memberAddressService.updateAddress(memberId, requestDto));
    }




}