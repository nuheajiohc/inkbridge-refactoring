package com.nhnacademy.inkbridge.backend.dto.address;

import com.nhnacademy.inkbridge.backend.entity.GeneralAddress;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: AddressUpdateRequestDto.
 *
 * @author jeongbyeonghun
 * @version 3/9/24
 */
@NoArgsConstructor
@Getter
public class AddressUpdateRequestDto {
    @NotNull
    private Long addressId;

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

    @Builder
    public AddressUpdateRequestDto(Long addressId, String zipCode, String address, String alias,
        String addressDetail, String receiverName, String receiverNumber) {
        this.addressId = addressId;
        this.zipCode = zipCode;
        this.address = address;
        this.alias = alias;
        this.addressDetail = addressDetail;
        this.receiverName = receiverName;
        this.receiverNumber = receiverNumber;
    }

    public GeneralAddress toGeneralAddress() {
        return GeneralAddress.builder().address(address).zipCode(zipCode).build();
    }
}
