package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;

import com.nhnacademy.inkbridge.backend.dto.cart.CartCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.cart.CartReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.ShoppingCart;
import com.nhnacademy.inkbridge.backend.entity.ShoppingCart.Pk;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.repository.ShoppingCartRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *  class: CartServiceImplTest.
 *
 *  @author minm063
 *  @version 2024/03/22
 */
@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @InjectMocks
    CartServiceImpl cartService;

    @Mock
    BookRepository bookRepository;

    @Mock
    ShoppingCartRepository shoppingCartRepository;

    @Mock
    MemberRepository memberRepository;

    List<CartCreateRequestDto> cartCreateRequestDto;

    @BeforeEach
    void setup() {
        cartCreateRequestDto = List.of(
            CartCreateRequestDto.builder().bookId(1L).memberId(1L).amount(1).build());
    }

    @Test
    @DisplayName("회원 번호로 장바구니 조회")
    void getCart() {
        when(shoppingCartRepository.findByMemberId(anyLong())).thenReturn(List.of(mock(
            CartReadResponseDto.class)));

        List<CartReadResponseDto> cart = cartService.getCart(1L);

        assertEquals(1, cart.size());
        verify(shoppingCartRepository, times(1)).findByMemberId(anyLong());
    }

    @Test
    @DisplayName("장바구니 등록")
    void createCart() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(mock(Member.class)));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(shoppingCartRepository.findById(any(Pk.class))).thenReturn(
            Optional.of(mock(ShoppingCart.class)));
        when(shoppingCartRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        cartService.createCart(cartCreateRequestDto);

        verify(memberRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(shoppingCartRepository, times(1)).findById(any(Pk.class));
        verify(shoppingCartRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("장바구니 등록 - 회원 검증 실패")
    void givenInvalidMember_whenCreateCart_thenThrowNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> cartService.createCart(cartCreateRequestDto));
        assertEquals(notFoundException.getMessage(),
            MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());

        verify(memberRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("장바구니 등록 - 도서 검증 실패")
    void givenInvalidBook_whenCreateCart_thenThrowNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(mock(Member.class)));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> cartService.createCart(cartCreateRequestDto));
        assertEquals(notFoundException.getMessage(),
            BookMessageEnum.BOOK_NOT_FOUND.getMessage());

        verify(memberRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("장바구니 등록 - 데이터베이스에 장바구니 부재")
    void givenShoppingCartAbsent_whenCreateCart_thenThrowNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(mock(Member.class)));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(shoppingCartRepository.findById(any(Pk.class))).thenReturn(
            Optional.empty());

        cartService.createCart(cartCreateRequestDto);

        verify(memberRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(shoppingCartRepository, times(1)).findById(any(Pk.class));
    }
}