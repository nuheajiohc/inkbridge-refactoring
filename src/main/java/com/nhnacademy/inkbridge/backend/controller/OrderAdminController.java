package com.nhnacademy.inkbridge.backend.controller;

import static com.nhnacademy.inkbridge.backend.enums.OrderStatusEnum.SHIPPING;

import com.nhnacademy.inkbridge.backend.dto.order.BookOrderDetailResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.facade.OrderFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: OrderAdminController.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class OrderAdminController {

    private final OrderFacade orderFacade;

    /**
     * 관리자가 주문 목록을 조회하는 메소드입니다.
     *
     * @param pageable 페이지 정보
     * @return 주문 목록
     */
    @GetMapping
    public ResponseEntity<Page<OrderReadResponseDto>> getOrderList(
        @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(orderFacade.getOrderList(pageable));
    }

    /**
     * 관리자가 주문 상세 내역을 조회하는 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return 주문 상세 내역
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<BookOrderDetailResponseDto> getOrderDetailByOrderId(
        @PathVariable("orderId") Long orderId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(orderFacade.getOrderDetailByOrderId(orderId));
    }

    /**
     * 관리자가 주문 상태를 배송중으로 변경하는 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return void
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable("orderId") Long orderId) {
        orderFacade.updateStatus(orderId, SHIPPING);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
