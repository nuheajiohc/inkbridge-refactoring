package com.nhnacademy.inkbridge.backend.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.nhnacademy.inkbridge.backend.domain.Address;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.storage.support.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressEntity extends BaseEntity {

	@Id
	@Column(name = "address_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "road_name")
	private String roadName;

	@Column(name = "address_detail")
	private String addressDetail;

	@Column(name = "zip_code")
	private String zipCode;

	@Column(name = "receiver_name")
	private String receiverName;

	@Column(name = "receiver_phone")
	private String receiverPhone;

	@Column(name = "is_default")
	private Boolean isDefault;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member memberEntity;

	@Builder
	public AddressEntity(String roadName, String addressDetail, String zipCode, String receiverName,
		String receiverPhone,
		Boolean isDefault, Member memberEntity) {
		this.roadName = roadName;
		this.addressDetail = addressDetail;
		this.zipCode = zipCode;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.isDefault = isDefault;
		this.memberEntity = memberEntity;
	}

	public Address toAddress(){
		return Address.builder()
			.id(this.id)
			.roadName(this.roadName)
			.addressDetail(this.addressDetail)
			.zipCode(this.zipCode)
			.receiverName(this.receiverName)
			.receiverPhone(this.receiverPhone)
			.isDefault(this.isDefault)
			.build();
	}

	public void update(Address address) {
		this.roadName = address.getRoadName();
		this.addressDetail = address.getAddressDetail();
		this.zipCode = address.getZipCode();
		this.receiverName = address.getReceiverName();
		this.receiverPhone = address.getReceiverPhone();
		this.isDefault = address.isDefault();
	}
}
