package com.nhnacademy.inkbridge.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: Address.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Entity
@Table(name = "general_address")
@NoArgsConstructor
@Getter
public class GeneralAddress {

    @Id
    @Column(name = "general_address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long generalAddressId;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "address")
    private String address;

    @Builder
    public GeneralAddress(Long generalAddressId, String zipCode, String address) {
        this.generalAddressId = generalAddressId;
        this.zipCode = zipCode;
        this.address = address;
    }
}
