package com.nhnacademy.inkbridge.backend.dto.order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class: OrderResponseDto.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
@Getter
@AllArgsConstructor
public class OrderResponseDto {

    private String orderCode;

    private String orderName;

    private String receiverName;

    private String receiverPhoneNumber;

    private String zipCode;

    private String address;

    private String detailAddress;

    private String senderName;

    private String senderPhoneNumber;

    private String senderEmail;

    private LocalDate deliveryDate;

    private Long usePoint;

    private Long payAmount;

    private Long deliveryPrice;

    private LocalDateTime orderAt;

    private LocalDate shipDate;
}
