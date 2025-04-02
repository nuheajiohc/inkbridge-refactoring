package com.nhnacademy.inkbridge.backend.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nhnacademy.inkbridge.backend.domain.Grade;
import com.nhnacademy.inkbridge.backend.storage.support.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "grade")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GradeEntity extends BaseEntity {

	@Id
	@Column(name = "grade_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "earning_rate")
	private Integer earningRate;
	@Column(name = "min_purchase_amount")
	private Integer minPurchaseAmount;

	public Grade toGrade(){
		return new Grade(id, name, earningRate, minPurchaseAmount);
	}
}
