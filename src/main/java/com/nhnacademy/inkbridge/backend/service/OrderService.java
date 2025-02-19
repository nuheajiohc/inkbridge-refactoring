package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto;

/**
 * class: OrderService.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
public interface OrderService {

    String createOrder(OrderCreateRequestDto requestDto);
}
