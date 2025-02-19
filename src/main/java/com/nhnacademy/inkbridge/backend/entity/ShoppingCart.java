package com.nhnacademy.inkbridge.backend.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: ShoppingCart.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "shopping_cart")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShoppingCart {

    @EmbeddedId
    private Pk pk;

    @Column(name = "amount")
    private Integer amount;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @MapsId("bookId")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Builder
    public ShoppingCart(Pk pk, Member member, Book book, Integer amount) {
        this.pk = pk;
        this.member = member;
        this.book = book;
        this.amount = amount;
    }

    /**
     * class: ShoppingCart.Pk.
     *
     * @author nhn
     * @version 2024/02/08
     */
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @EqualsAndHashCode
    @Getter
    @Embeddable
    public static class Pk implements Serializable {

        @Column(name = "member_id")
        private Long memberId;
        @Column(name = "book_id")
        private Long bookId;

        @Builder
        public Pk(Long memberId, Long bookId) {
            this.memberId = memberId;
            this.bookId = bookId;
        }
    }

    public void updateShoppingCart(Integer amount) {
        this.amount = amount;
    }
}
