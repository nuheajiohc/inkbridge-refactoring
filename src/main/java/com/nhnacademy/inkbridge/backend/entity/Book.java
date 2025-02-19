package com.nhnacademy.inkbridge.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: Book.
 *
 * @author nhn
 * @version 2024/02/08
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "book_index")
    private String bookIndex;

    @Column(name = "description")
    private String description;

    @Column(name = "publicated_at")
    private LocalDate publicatedAt;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "regular_price")
    private Long regularPrice;

    @Column(name = "price")
    private Long price;

    @Column(name = "discount_ratio")
    @JsonSerialize(as = BigDecimal.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal discountRatio;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "is_packagable")
    private Boolean isPackagable;

    @Column(name = "view")
    private Long view;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private BookStatus bookStatus;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToOne
    @JoinColumn(name = "thumbnail_id")
    private File thumbnailFile;

    @Builder
    public Book(String bookTitle, String bookIndex, String description, LocalDate publicatedAt,
        String isbn, Long regularPrice, Long price, BigDecimal discountRatio, Integer stock,
        Boolean isPackagable, LocalDateTime updatedAt, BookStatus bookStatus, Publisher publisher,
        File thumbnailFile) {
        this.bookTitle = bookTitle;
        this.bookIndex = bookIndex;
        this.description = description;
        this.publicatedAt = publicatedAt;
        this.isbn = isbn;
        this.regularPrice = regularPrice;
        this.price = price;
        this.discountRatio = discountRatio;
        this.stock = stock;
        this.isPackagable = isPackagable;
        this.updatedAt = updatedAt;
        this.view = 0L;
        this.bookStatus = bookStatus;
        this.publisher = publisher;
        this.thumbnailFile = thumbnailFile;
    }

    public void updateBook(String bookTitle, String bookIndex, String description,
        LocalDate publicatedAt, String isbn, Long regularPrice, Long price,
        BigDecimal discountRatio, Integer stock, Boolean isPackagable, BookStatus bookStatus,
        Publisher publisher, File thumbnailFile) {
        this.bookTitle = bookTitle;
        this.bookIndex = bookIndex;
        this.description = description;
        this.publicatedAt = publicatedAt;
        this.isbn = isbn;
        this.regularPrice = regularPrice;
        this.price = price;
        this.discountRatio = discountRatio;
        this.stock = stock;
        this.isPackagable = isPackagable;
        this.updatedAt = LocalDateTime.now();
        this.bookStatus = bookStatus;
        this.publisher = publisher;
        this.thumbnailFile = thumbnailFile;
    }

    /**
     * 도서 재고를 수정하는 메서드입니다.
     *
     * @param stock Integer
     */
    public void updateBookStock(Integer stock) {
        this.stock = stock;
    }

    public void updateStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }
}