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
 * class: BookAuthor.
 *
 * @author minm063
 * @version 2024/02/08
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_author")
public class BookAuthor {

    @EmbeddedId
    private Pk pk;

    @MapsId("authorId")
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @MapsId("bookId")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Builder
    public BookAuthor(Pk pk, Author author, Book book) {
        this.pk = pk;
        this.author = author;
        this.book = book;
    }

    /**
     * class: BookAuthor.Pk.
     *
     * @author nhn
     * @version 2024/02/08
     */
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @EqualsAndHashCode
    @Getter
    @Embeddable
    public static class Pk implements Serializable {

        @Column(name = "author_id")
        private Long authorId;
        @Column(name = "book_id")
        private Long bookId;

        @Builder
        public Pk(Long authorId, Long bookId) {
            this.authorId = authorId;
            this.bookId = bookId;
        }
    }

    public void updateBookAuthor(Author author) {
        this.author = author;
    }
}
