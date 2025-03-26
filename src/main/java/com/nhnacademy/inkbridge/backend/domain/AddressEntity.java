package com.nhnacademy.inkbridge.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.nhnacademy.inkbridge.backend.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressEntity {

	@Id
	@Column(name = "address_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "address_name")
	private String addressName;

	@Column(name = "road_name")
	private String roadName;

	@Column(name = "zip_code")
	private String zipCode;

	@Column(name = "receiver_name")
	private String receiverName;

	@Column(name = "receiver_phone")
	private String receiverPhone;

	@Column(name = "is_default")
	private Boolean isDefault;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member memberEntity;

}
