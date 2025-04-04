package com.nhnacademy.inkbridge.backend.storage.admin.publisher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nhnacademy.inkbridge.backend.domain.admin.publisher.Publisher;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "publisher")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PublisherEntity {

	@Id
	@Column(name = "publisher_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	public PublisherEntity(String name) {
		this.name = name;
	}

	public void update(Publisher publisher) {
		this.name = publisher.getName();
	}
}
