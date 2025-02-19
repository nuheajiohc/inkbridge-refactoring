package com.nhnacademy.inkbridge.backend.dto.address;

import com.nhnacademy.inkbridge.backend.entity.GeneralAddress;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAddress;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * class: AddressCreateRequestDto.
 *
 * @author jeongbyeonghun
 * @version 3/9/24
 */

@NoArgsConstructor
@Getter
public class AddressCreateRequestDto {

    @NotBlank
    @Size(max = 5)
    private String zipCode;

    @NotBlank
    private String address;

    @NotBlank
    @Size(max = 20)
    private String alias;

    @NotBlank
    @Size(max = 200)
    private String addressDetail;

    @NotBlank
    @Size(max = 20)
    private String receiverName;

    @NotBlank
    @Size(max = 11)
    private String receiverNumber;


    public MemberAddress toEntity(Member user, GeneralAddress generalAddress) {
        return MemberAddress.builder().generalAddress(generalAddress).member(user)
            .addressDetail(addressDetail).alias(alias).receiverName(receiverName)
            .receiverNumber(receiverNumber).build();
    }

    public GeneralAddress toGeneralAddress() {
        return GeneralAddress.builder().address(address).zipCode(zipCode).build();
    }

    @Builder
    public AddressCreateRequestDto(@NonNull String zipCode, @NonNull String address,
        @NonNull String alias, @NonNull String addressDetail, @NonNull String receiverName,
        @NonNull String receiverNumber) {
        this.zipCode = zipCode;
        this.address = address;
        this.alias = alias;
        this.addressDetail = addressDetail;
        this.receiverName = receiverName;
        this.receiverNumber = receiverNumber;
    }
}
