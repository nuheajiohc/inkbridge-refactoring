package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.cart.CartCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.cart.CartReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.CartService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: CartController.
 *
 * @author minm063
 * @version 2024/03/09
 */
@Slf4j
@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * memberId에 따른 장바구니를 조회하는 api입니다.
     *
     * @param memberId Long
     * @return CartReadResponseDto
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<List<CartReadResponseDto>> getCart(@PathVariable Long memberId) {
        List<CartReadResponseDto> cartList = cartService.getCart(memberId);
        return new ResponseEntity<>(cartList, HttpStatus.OK);
    }

    /**
     * 장바구니를 생성하는 api입니다.
     *
     * @param cartList CartCreateRequestDto
     * @return HttpStatus
     */
    @PostMapping
    public ResponseEntity<HttpStatus> createCart(@RequestBody List<CartCreateRequestDto> cartList) {
        cartService.createCart(cartList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
