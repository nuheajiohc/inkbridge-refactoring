package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nhnacademy.inkbridge.backend.dto.cart.CartReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.entity.ShoppingCart;
import com.nhnacademy.inkbridge.backend.entity.ShoppingCart.Pk;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 *  class: ShoppingCartRepositoryTest.
 *
 *  @author minm063
 *  @version 2024/03/22
 */
@DataJpaTest
class ShoppingCartRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    ShoppingCart shoppingCart;
    Book book;
    Member member;

    @BeforeEach
    void setup() {
        BookStatus status = BookStatus.builder().statusId(1L).statusName("status").build();
        Publisher publisher = Publisher.builder().publisherName("publisher").build();
        File file = File.builder().fileName("file").fileExtension("extension").fileExtension("png")
            .build();
        Author author = Author.builder().authorName("author").authorIntroduce("introduce")
            .file(file)
            .build();

        testEntityManager.persist(status);
        testEntityManager.persist(publisher);
        testEntityManager.persist(file);
        testEntityManager.persist(author);

        book = Book.builder().bookTitle("title").bookIndex("index").description("description")
            .publicatedAt(LocalDate.parse("2024-03-06")).isbn("1234567890123").regularPrice(1L)
            .price(1L)
            .discountRatio(BigDecimal.valueOf(3.33)).stock(1).isPackagable(true)
            .bookStatus(status)
            .publisher(publisher).thumbnailFile(file).build();
        testEntityManager.persist(book);
        member = Member.create().memberName("member").build();
        member = testEntityManager.persist(member);
        shoppingCart = ShoppingCart.builder().book(book).member(member).amount(100).pk(
            Pk.builder().memberId(member.getMemberId()).bookId(book.getBookId()).build()).build();

        testEntityManager.persist(book);
        testEntityManager.persist(member);
        testEntityManager.persist(shoppingCart);
    }

    @Test
    @DisplayName("회원 번호로 조회")
    void findByMemberId() {
        List<CartReadResponseDto> cart = shoppingCartRepository.findByMemberId(
            member.getMemberId());

        assertAll(
            () -> assertEquals(1, cart.size()),
            () -> assertEquals(cart.get(0).getAmount(), shoppingCart.getAmount()),
            () -> assertEquals(cart.get(0).getBookId(), book.getBookId())
        );
    }
}