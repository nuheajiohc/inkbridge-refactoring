package com.nhnacademy.inkbridge.backend.dto.order;

import com.nhnacademy.inkbridge.backend.dto.pay.PayReadResponseDto;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * class: BookOrderDetailResponseDto.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
@Builder
@AllArgsConstructor
@Getter
public class BookOrderDetailResponseDto {

    private OrderResponseDto orderInfo;
    private PayReadResponseDto payInfo;
    private List<OrderDetailReadResponseDto> orderDetailInfoList;
    private Map<Long, Boolean> isReviewed;
}
