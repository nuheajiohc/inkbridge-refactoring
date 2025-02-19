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
 * class: Wrapping.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "wrapping")
public class Wrapping {

    @Id
    @Column(name = "wrapping_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wrappingId;
    @Column(name = "wrapping_name")
    private String wrappingName;
    @Column(name = "price")
    private Long price;
    @Column(name = "is_active")
    private Boolean isActive;

    @Builder
    public Wrapping(Long wrappingId, String wrappingName, Long price, Boolean isActive) {
        this.wrappingId = wrappingId;
        this.wrappingName = wrappingName;
        this.price = price;
        this.isActive = isActive;
    }

    public void update(String wrappingName, Long price, boolean isActive) {
        this.wrappingName = wrappingName;
        this.price = price;
        this.isActive = isActive;
    }
}
