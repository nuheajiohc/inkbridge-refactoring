package com.nhnacademy.inkbridge.backend.service.impl;

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
import com.nhnacademy.inkbridge.backend.service.CartService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: CartServiceImpl.
 *
 * @author minm063
 * @version 2024/03/09
 */
@Service
public class CartServiceImpl implements CartService {

    private final BookRepository bookRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final MemberRepository memberRepository;

    public CartServiceImpl(BookRepository bookRepository,
        ShoppingCartRepository shoppingCartRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CartReadResponseDto> getCart(Long memberId) {
        return shoppingCartRepository.findByMemberId(memberId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void createCart(List<CartCreateRequestDto> cartList) {
        List<ShoppingCart> array = new ArrayList<>();
        cartList.forEach(dto -> {
            Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new NotFoundException(
                    MemberMessageEnum.MEMBER_NOT_FOUND.getMessage()));
            Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new NotFoundException(
                    BookMessageEnum.BOOK_NOT_FOUND.getMessage()));

            Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(
                Pk.builder().build());
            if (shoppingCart.isPresent()) {
                shoppingCart.get().updateShoppingCart(dto.getAmount());
                return;
            }
            ShoppingCart cart = ShoppingCart.builder()
                .pk(Pk.builder().bookId(dto.getBookId()).memberId(dto.getMemberId()).build())
                .member(member).book(book).amount(dto.getAmount()).build();
            array.add(cart);
        });
        shoppingCartRepository.saveAll(array);
    }
}
