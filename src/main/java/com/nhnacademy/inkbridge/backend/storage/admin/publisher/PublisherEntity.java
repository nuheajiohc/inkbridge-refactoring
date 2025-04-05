package com.nhnacademy.inkbridge.backend.storage.admin.publisher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nhnacademy.inkbridge.backend.domain.DomainStatus;
import com.nhnacademy.inkbridge.backend.domain.admin.publisher.Publisher;
import com.nhnacademy.inkbridge.backend.storage.support.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "publisher")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PublisherEntity extends BaseEntity {

	@Id
	@Column(name = "publisher_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private DomainStatus status;

	public PublisherEntity(String name, DomainStatus status) {
		this.name = name;
		this.status = status;
	}

	public void update(Publisher publisher) {
		this.name = publisher.getName();
		this.status = publisher.getStatus();
	}

	public void delete() {
		status = DomainStatus.INACTIVE;
	}
}
