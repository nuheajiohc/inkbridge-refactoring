package com.nhnacademy.inkbridge.backend.dto.order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class: OrderReadResponseDto.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
@AllArgsConstructor
@Getter
public class OrderReadResponseDto {

    private Long orderId;
    private String orderCode;
    private String orderName;
    private LocalDateTime orderAt;
    private LocalDate deliveryDate;
    private Long totalPrice;
}
