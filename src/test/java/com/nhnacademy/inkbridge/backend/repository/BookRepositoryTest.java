package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminSelectedReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookOrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookAuthor;
import com.nhnacademy.inkbridge.backend.entity.BookAuthor.Pk;
import com.nhnacademy.inkbridge.backend.entity.BookCategory;
import com.nhnacademy.inkbridge.backend.entity.BookFile;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.entity.BookTag;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.entity.Tag;
import com.nhnacademy.inkbridge.backend.entity.Wish;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * class: BookRepositoryTest.
 *
 * @author minm063
 * @version 2024/03/06
 */
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
class BookRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    BookRepository bookRepository;

    Pageable pageable;
    Book book;
    Publisher publisher;
    Author author;
    File file;
    Wish wish;
    Member member;
    BookStatus bookStatus;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 5);
        bookStatus = BookStatus.builder().statusId(1L).statusName("status").build();
        publisher = Publisher.builder().publisherName("publisher").build();
        file = File.builder().fileName("file").fileExtension("extension").fileExtension("png")
            .build();
        author = Author.builder().authorName("author").authorIntroduce("introduce").file(file)
            .build();
        book = Book.builder().bookTitle("title").bookIndex("index").description("description")
            .publicatedAt(LocalDate.parse("2024-03-06")).isbn("1234567890123").regularPrice(1L)
            .price(1L)
            .discountRatio(BigDecimal.valueOf(3.33)).stock(1).isPackagable(true)
            .bookStatus(bookStatus)
            .publisher(publisher).thumbnailFile(file).build();
        member = Member.create().memberName("member").build();
        Category category = Category.create().categoryName("category").build();
        Tag tag = Tag.builder().tagName("tag").build();

        bookStatus = testEntityManager.persist(bookStatus);
        publisher = testEntityManager.persist(publisher);
        file = testEntityManager.persist(file);
        author = testEntityManager.persist(author);
        category = testEntityManager.persist(category);
        tag = testEntityManager.persist(tag);
        bookRepository.save(book);
        member = testEntityManager.persist(member);

        BookAuthor bookAuthor = BookAuthor.builder()
            .pk(Pk.builder().bookId(book.getBookId()).authorId(
                author.getAuthorId()).build()).book(book).author(author).build();
        bookAuthor = testEntityManager.persist(bookAuthor);
        BookCategory bookCategory = BookCategory.create()
            .pk(BookCategory.Pk.builder().bookId(book.getBookId()).categoryId(
                category.getCategoryId()).build()).book(book)
            .category(category).build();
        bookCategory = testEntityManager.persist(bookCategory);
        BookTag bookTag = BookTag.builder()
            .pk(BookTag.Pk.builder().bookId(book.getBookId()).tagId(tag.getTagId()).build())
            .tag(tag).book(book)
            .build();
        bookTag = testEntityManager.persist(bookTag);
        wish = Wish.builder().pk(Wish.Pk.builder().memberId(1L).bookId(1L).build()).member(member)
            .book(book).build();
        wish = testEntityManager.persist(wish);
        BookFile bookFile = BookFile.builder().fileId(file.getFileId()).book(book).build();
        bookFile = testEntityManager.persist(bookFile);
    }

    @Test
    void findAllBooks() {
        Page<BooksPaginationReadResponseDto> books = bookRepository.findAllBooks(pageable);

        assertAll(
            () -> assertEquals(5, books.getSize()),
            () -> assertEquals(1, books.getContent().size()),
            () -> assertEquals(book.getBookId(), books.getContent().get(0).getBookId()),
            () -> assertEquals(book.getBookTitle(), books.getContent().get(0).getBookTitle()),
            () -> assertEquals(book.getPrice(), books.getContent().get(0).getPrice()),
            () -> assertEquals(publisher.getPublisherName(),
                books.getContent().get(0).getPublisherName()),
            () -> assertEquals(file.getFileUrl(), books.getContent().get(0).getFileUrl())
        );
    }

    @Test
    void findByBookId() {
        Optional<BookDetailReadResponseDto> bookReadResponseDtoOptional = bookRepository.findByBookId(
            book.getBookId(),
            member.getMemberId());

        if (bookReadResponseDtoOptional.isPresent()) {
            BookDetailReadResponseDto response = bookReadResponseDtoOptional.get();
            assertAll(
                () -> assertEquals(response.getBookTitle(), book.getBookTitle()),
                () -> assertEquals(response.getBookIndex(), book.getBookIndex()),
                () -> assertEquals(response.getDescription(), book.getDescription()),
                () -> assertEquals(response.getPublicatedAt(), book.getPublicatedAt()),
                () -> assertEquals(response.getIsbn(), book.getIsbn()),
                () -> assertEquals(response.getRegularPrice(), book.getRegularPrice()),
                () -> assertEquals(response.getPrice(), book.getPrice()),
                () -> assertEquals(response.getDiscountRatio(), book.getDiscountRatio()),
                () -> assertEquals(response.getIsPackagable(), book.getIsPackagable()),
                () -> assertEquals(response.getPublisherId(),
                    publisher.getPublisherId()),
                () -> assertEquals(response.getPublisherName(),
                    publisher.getPublisherName()),
                () -> assertEquals(1, response.getAuthors().size()),
                () -> assertEquals(response.getWish(), wish.getPk().getMemberId()),
                () -> assertEquals(1, response.getFileUrl().size()),
                () -> assertEquals(1, response.getTagName().size()),
                () -> assertEquals(1, response.getCategoryName().size())
            );
        }
    }

    @Test
    void givenInvalidBookId_findByBookId() {
        Optional<BookDetailReadResponseDto> byBookId = bookRepository.findByBookId(0L, 0L);

        assertFalse(byBookId.isPresent());
    }

    @Test
    void findAllBooksByAdmin() {
        Page<BooksAdminPaginationReadResponseDto> books = bookRepository.findAllBooksByAdmin(
            pageable);

        assertAll(
            () -> assertEquals(5, books.getSize()),
            () -> assertEquals(1, books.getContent().size()),
            () -> assertEquals(books.getContent().get(0).getBookId(), book.getBookId()),
            () -> assertEquals(books.getContent().get(0).getBookTitle(),
                book.getBookTitle()),
            () -> assertEquals(books.getContent().get(0).getPublisherName(),
                publisher.getPublisherName()),
            () -> assertEquals(books.getContent().get(0).getStatusName(),
                bookStatus.getStatusName())
        );
    }

    @Test
    void findBookByAdminBookId() {
        Optional<BookAdminSelectedReadResponseDto> bookByAdminByBookIdOptional = bookRepository.findBookByAdminByBookId(
            book.getBookId());

        if (bookByAdminByBookIdOptional.isPresent()) {
            BookAdminSelectedReadResponseDto bookByAdminByBookId = bookByAdminByBookIdOptional.get();
            assertAll(
                () -> assertEquals(bookByAdminByBookId.getBookTitle(), book.getBookTitle()),
                () -> assertEquals(bookByAdminByBookId.getBookIndex(), book.getBookIndex()),
                () -> assertEquals(bookByAdminByBookId.getDescription(), book.getDescription()),
                () -> assertEquals(bookByAdminByBookId.getPublicatedAt(), book.getPublicatedAt()),
                () -> assertEquals(bookByAdminByBookId.getIsbn(), book.getIsbn()),
                () -> assertEquals(bookByAdminByBookId.getRegularPrice(), book.getRegularPrice()),
                () -> assertEquals(bookByAdminByBookId.getPrice(), book.getPrice()),
                () -> assertEquals(bookByAdminByBookId.getDiscountRatio(), book.getDiscountRatio()),
                () -> assertEquals(bookByAdminByBookId.getStock(), book.getStock()),
                () -> assertEquals(bookByAdminByBookId.getIsPackagable(), book.getIsPackagable()),
                () -> assertEquals(bookByAdminByBookId.getAuthorIdList().get(0),
                    author.getAuthorId()),
                () -> assertEquals(bookByAdminByBookId.getPublisherId(),
                    publisher.getPublisherId()),
                () -> assertEquals(bookByAdminByBookId.getStatusId(), bookStatus.getStatusId()),
                () -> assertEquals(bookByAdminByBookId.getUrl(), file.getFileUrl()),
                () -> assertEquals(1, bookByAdminByBookId.getCategoryIdList().size()),
                () -> assertEquals(1, bookByAdminByBookId.getTagIdList().size())
            );
        }
    }

    @Test
    void givenInvalidBookId_findBookByAdminBookId() {
        Optional<BookAdminSelectedReadResponseDto> bookByAdminByBookId = bookRepository.findBookByAdminByBookId(
            0L);

        assertFalse(bookByAdminByBookId.isPresent());
    }

    @Test
    @DisplayName("장바구니용 도서 조회")
    void findByBookIdIn() {
        List<BookOrderReadResponseDto> byBookIdIn = bookRepository.findByBookIdIn(
            Set.of(book.getBookId()));

        assertAll(
            () -> assertEquals(1, byBookIdIn.size()),
            () -> assertEquals(book.getBookId(), byBookIdIn.get(0).getBookId()),
            () -> assertEquals(book.getBookTitle(), byBookIdIn.get(0).getBookTitle()),
            () -> assertEquals(book.getRegularPrice(), byBookIdIn.get(0).getRegularPrice()),
            () -> assertEquals(book.getPrice(), byBookIdIn.get(0).getPrice()),
            () -> assertEquals(book.getDiscountRatio(), byBookIdIn.get(0).getDiscountRatio()),
            () -> assertEquals(book.getStock(), byBookIdIn.get(0).getStock()),
            () -> assertEquals(book.getIsPackagable(), byBookIdIn.get(0).getIsPackagable()),
            () -> assertEquals(file.getFileUrl(), byBookIdIn.get(0).getThumbnail())
        );
    }
}