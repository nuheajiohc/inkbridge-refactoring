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
 * class: BookTag.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_tag")
public class BookTag {

    @EmbeddedId
    private Pk pk;

    @MapsId("bookId")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @MapsId("tagId")
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public BookTag(Pk pk, Book book, Tag tag) {
        this.pk = pk;
        this.book = book;
        this.tag = tag;
    }

    /**
     * class: BookTag.Pk.
     *
     * @author nhn
     * @version 2024/02/08
     */
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {

        @Column(name = "book_id")
        private Long bookId;
        @Column(name = "tag_id")
        private Long tagId;

        @Builder
        public Pk(Long bookId, Long tagId) {
            this.bookId = bookId;
            this.tagId = tagId;
        }
    }
}
