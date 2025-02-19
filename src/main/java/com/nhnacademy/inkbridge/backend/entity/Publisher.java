package com.nhnacademy.inkbridge.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: Publisher.
 *
 * @author minm063
 * @version 2024/02/08
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "publisher")
public class Publisher {

    @Id
    @Column(name = "publisher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publisherId;

    @Column(name = "publisher_name")
    private String publisherName;

    @Builder
    public Publisher(String publisherName) {
        this.publisherName = publisherName;
    }

    /**
     * 출판사명을 수정할 때 사용하는 메서드
     * @param publisherName 출판사명
     */
    public void updatePublisher(String publisherName) {
        this.publisherName = publisherName;
    }

}
