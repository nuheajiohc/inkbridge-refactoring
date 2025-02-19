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
 * class: Wish.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "wish")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wish {

    @EmbeddedId
    private Pk pk;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @MapsId("bookId")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Builder
    public Wish(Pk pk, Member member, Book book) {
        this.pk = pk;
        this.member = member;
        this.book = book;
    }

    /**
     * class: Wish.Pk.
     *
     * @author minseo
     * @version 2/8/24
     */
    @Embeddable
    @EqualsAndHashCode
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
