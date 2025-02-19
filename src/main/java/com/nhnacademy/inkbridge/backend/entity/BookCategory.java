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
import lombok.ToString;

/**
 * class: BookCategory.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_category")
public class BookCategory {

    @EmbeddedId
    private Pk pk;

    @MapsId("categoryId")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @MapsId("bookId")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Builder(builderMethodName = "create")
    public BookCategory(Pk pk, Category category, Book book) {
        this.pk = pk;
        this.category = category;
        this.book = book;
    }

    /**
     * class: BookCategory.Pk.
     *
     * @author nhn
     * @version 2024/02/08
     */
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    @Embeddable
    @ToString
    public static class Pk implements Serializable {

        @Column(name = "category_id")
        private Long categoryId;

        @Column(name = "book_id")
        private Long bookId;

        @Builder
        public Pk(Long categoryId, Long bookId) {
            this.categoryId = categoryId;
            this.bookId = bookId;
        }
    }
}
