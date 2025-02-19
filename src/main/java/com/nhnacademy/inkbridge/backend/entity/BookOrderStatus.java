package com.nhnacademy.inkbridge.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookOrderStatus.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Entity
@Table(name = "book_order_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookOrderStatus {

    @Id
    @Column(name = "order_status_id")
    private Long orderStatusId;

    @Column(name = "order_status")
    private String orderStatus;

    @Builder
    public BookOrderStatus(Long orderStatusId, String orderStatus) {
        this.orderStatusId = orderStatusId;
        this.orderStatus = orderStatus;
    }
}
