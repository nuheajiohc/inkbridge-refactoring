package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.cart.CartReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.QBook;
import com.nhnacademy.inkbridge.backend.entity.QMember;
import com.nhnacademy.inkbridge.backend.entity.QShoppingCart;
import com.nhnacademy.inkbridge.backend.entity.ShoppingCart;
import com.nhnacademy.inkbridge.backend.repository.custom.ShoppingCartRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: CartRepositoryImpl.
 *
 * @author minm063
 * @version 2024/03/12
 */
public class ShoppingCartRepositoryImpl extends QuerydslRepositorySupport implements
    ShoppingCartRepositoryCustom {

    public ShoppingCartRepositoryImpl() {
        super(ShoppingCart.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CartReadResponseDto> findByMemberId(Long memberId) {
        QShoppingCart shoppingCart = QShoppingCart.shoppingCart;
        QBook book = QBook.book;
        QMember member = QMember.member;

        return from(shoppingCart)
            .innerJoin(book).on(book.bookId.eq(shoppingCart.book.bookId))
            .innerJoin(member).on(member.memberId.eq(shoppingCart.member.memberId))
            .where(member.memberId.eq(memberId))
            .select(Projections.constructor(CartReadResponseDto.class, shoppingCart.amount,
                book.bookId)).fetch();
    }
}
