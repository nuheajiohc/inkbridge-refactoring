package com.nhnacademy.inkbridge.backend.entity;

import com.nhnacademy.inkbridge.backend.dto.address.AddressUpdateRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: memberAddress.
 *
 * @author minseo
 * @version 2024/02/08
 */
@Entity
@Table(name = "member_address")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAddress {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(name = "alias")
    private String alias;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_number")
    private String receiverNumber;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "general_address_id")
    private GeneralAddress generalAddress;

    @Builder
    public MemberAddress(Long addressId, String alias, String addressDetail, String receiverName,
        String receiverNumber, Member member, GeneralAddress generalAddress) {
        this.addressId = addressId;
        this.alias = alias;
        this.addressDetail = addressDetail;
        this.receiverName = receiverName;
        this.receiverNumber = receiverNumber;
        this.member = member;
        this.generalAddress = generalAddress;
    }


    public void update(GeneralAddress generalAddress,
        AddressUpdateRequestDto addressUpdateRequestDto) {
        this.generalAddress = generalAddress;
        this.alias = addressUpdateRequestDto.getAlias();
        this.addressDetail = addressUpdateRequestDto.getAddressDetail();
        this.receiverName = addressUpdateRequestDto.getReceiverName();
        this.receiverNumber = addressUpdateRequestDto.getReceiverNumber();
    }
}
