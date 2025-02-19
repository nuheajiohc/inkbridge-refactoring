package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminSelectedReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookOrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookStockUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewAverageReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.entity.Tag;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.ConflictException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.AuthorRepository;
import com.nhnacademy.inkbridge.backend.repository.BookAuthorRepository;
import com.nhnacademy.inkbridge.backend.repository.BookCategoryRepository;
import com.nhnacademy.inkbridge.backend.repository.BookFileRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.BookStatusRepository;
import com.nhnacademy.inkbridge.backend.repository.BookTagRepository;
import com.nhnacademy.inkbridge.backend.repository.CategoryRepository;
import com.nhnacademy.inkbridge.backend.repository.FileRepository;
import com.nhnacademy.inkbridge.backend.repository.PublisherRepository;
import com.nhnacademy.inkbridge.backend.repository.ReviewRepository;
import com.nhnacademy.inkbridge.backend.repository.TagRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * class: BookServiceImplTest.
 *
 * @author minm063
 * @version 2024/02/18
 */
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;
    @Mock
    BookStatusRepository bookStatusRepository;
    @Mock
    PublisherRepository publisherRepository;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    TagRepository tagRepository;
    @Mock
    BookFileRepository bookFileRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    FileRepository fileRepository;
    @Mock
    BookCategoryRepository bookCategoryRepository;
    @Mock
    BookAuthorRepository bookAuthorRepository;
    @Mock
    BookTagRepository bookTagRepository;
    @Mock
    ReviewRepository reviewRepository;
    @Mock
    Pageable pageable;

    AuthorPaginationReadResponseDto authorPaginationReadResponseDto;
    File file;

    @BeforeEach
    void setup() {
        authorPaginationReadResponseDto = AuthorPaginationReadResponseDto.builder()
            .build();
        file = File.builder().build();
    }

    @Test
    @DisplayName("메인 페이지 도서 목록")
    void whenReadBooks_thenReturnPageDtoList() {
        Page<BooksPaginationReadResponseDto> resultPage = new PageImpl<>(List.of(
            BooksPaginationReadResponseDto.builder().bookId(1L).build()),
            pageable, 0);
        List<AuthorPaginationReadResponseDto> authorPaginationReadResponseDtos = List.of(
            authorPaginationReadResponseDto);

        when(bookRepository.findAllBooks(any(Pageable.class))).thenReturn(resultPage);
        when(authorRepository.findAuthorNameByBookId(anyList())).thenReturn(
            authorPaginationReadResponseDtos);

        BooksReadResponseDto booksReadResponseDto = bookService.readBooks(pageable);

        assertEquals(1, booksReadResponseDto.getBooksPaginationReadResponseDtos().getSize());
        assertEquals(1, booksReadResponseDto.getAuthorPaginationReadResponseDto().size());
        verify(bookRepository, times(1)).findAllBooks(pageable);
        verify(authorRepository, times(1)).findAuthorNameByBookId(anyList());
    }

    @Test
    @DisplayName("도서 번호들로 장바구니용 도서 목록 조회")
    void whenGetCartBooks() {
        when(bookRepository.findByBookIdIn(anySet())).thenReturn(
            List.of(BookOrderReadResponseDto.builder().bookId(1L)
                .build()));

        List<BookOrderReadResponseDto> cartBooks = bookService.getCartBooks(Set.of(1L));

        assertEquals(1, cartBooks.size());
        verify(bookRepository, times(1)).findByBookIdIn(anySet());
    }

    @Test
    @DisplayName("도서 상세 조회")
    void whenReadBook_thenReturnDto() {
        when(bookRepository.findByBookId(anyLong(), anyLong())).thenReturn(
            Optional.of(BookDetailReadResponseDto.builder().build()));
        when(reviewRepository.avgReview(anyLong())).thenReturn(
            ReviewAverageReadResponseDto.builder().avg(3.33).count(1L).build());

        BookReadResponseDto bookReadResponseDto = bookService.readBook(pageable, 1L, 1L);

        assertNotNull(bookReadResponseDto);
        verify(bookRepository, times(1)).findByBookId(anyLong(), anyLong());
        verify(reviewRepository, times(1)).avgReview(anyLong());
    }

    @Test
    @DisplayName("도서 상세 조회 - 도서 번호 검증 실패")
    void givenInvalidBookId_whenReadBook_thenThrowNowFoundException() {
        when(bookRepository.findByBookId(anyLong(), anyLong())).thenReturn(
            Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> bookService.readBook(pageable, 1L, 1L));
        assertEquals(BookMessageEnum.BOOK_NOT_FOUND.getMessage(), notFoundException.getMessage());
    }

    @Test
    @DisplayName("관리자 페이지 도서 목록")
    void whenReadBooksByAdmin_thenReturnPageDtoList() {
        Page<BooksAdminPaginationReadResponseDto> resultPage = new PageImpl<>(
            List.of(BooksAdminPaginationReadResponseDto.builder().bookId(1L).build()));

        when(bookRepository.findAllBooksByAdmin(any(Pageable.class))).thenReturn(resultPage);
        when(authorRepository.findAuthorNameByBookId(anyList())).thenReturn(
            List.of(authorPaginationReadResponseDto));

        BooksAdminReadResponseDto booksAdminReadResponseDto = bookService.readBooksByAdmin(
            pageable);

        assertEquals(1,
            booksAdminReadResponseDto.getBooksAdminPaginationReadResponseDtos().getSize());
        assertEquals(1, booksAdminReadResponseDto.getAuthorPaginationReadResponseDtos().size());
        verify(bookRepository, times(1)).findAllBooksByAdmin(pageable);
        verify(authorRepository, times(1)).findAuthorNameByBookId(anyList());
    }

    @Test
    @DisplayName("관리자 페이지 도서 상세")
    void whenReadBookByAdmin_thenReturnDto() {

        when(bookRepository.findBookByAdminByBookId(anyLong())).thenReturn(
            Optional.of(mock(BookAdminSelectedReadResponseDto.class)));
        when(categoryRepository.findAllByCategoryParentIsNull()).thenReturn(
            List.of(mock(Category.class)));
        when(publisherRepository.findAll()).thenReturn(List.of(mock(Publisher.class)));
        when(authorRepository.findAll()).thenReturn(List.of(mock(Author.class)));
        when(bookStatusRepository.findAll()).thenReturn(List.of(mock(BookStatus.class)));
        when(tagRepository.findAll()).thenReturn(List.of(mock(Tag.class)));

        BookAdminDetailReadResponseDto bookAdminDetailReadResponseDto = bookService.readBookByAdmin(
            1L);

        assertNotNull(bookAdminDetailReadResponseDto);
        verify(bookRepository, times(1)).findBookByAdminByBookId(anyLong());
        verify(categoryRepository, times(1)).findAllByCategoryParentIsNull();
        verify(publisherRepository, times(1)).findAll();
        verify(authorRepository, times(1)).findAll();
        verify(bookStatusRepository, times(1)).findAll();
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("관리자 페이지 도서 상세 - 도서 검증 실패")
    void givenNotFound_whenReadBookByAdmin_thenThrowNotFoundException() {
        when(bookRepository.findBookByAdminByBookId(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.readBookByAdmin(1L));
        verify(bookRepository, times(1)).findBookByAdminByBookId(anyLong());
    }

    @Test
    @DisplayName("관리자 페이지 도서 등록 폼")
    void whenReadBookByAdmin_thenReturnFormDto() {
        when(categoryRepository.findAllByCategoryParentIsNull()).thenReturn(
            List.of(mock(Category.class)));
        when(publisherRepository.findAll()).thenReturn(List.of(mock(Publisher.class)));
        when(authorRepository.findAll()).thenReturn(List.of(mock(Author.class)));
        when(bookStatusRepository.findAll()).thenReturn(List.of(mock(BookStatus.class)));
        when(tagRepository.findAll()).thenReturn(List.of(mock(Tag.class)));

        BookAdminReadResponseDto bookAdminReadResponseDto = bookService.readBookByAdmin();

        assertNotNull(bookAdminReadResponseDto);
        verify(categoryRepository, times(1)).findAllByCategoryParentIsNull();
        verify(publisherRepository, times(1)).findAll();
        verify(authorRepository, times(1)).findAll();
        verify(bookStatusRepository, times(1)).findAll();
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("도서 등록")
    void whenCreateBook() {
        Book book = Book.builder().build();

        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);

        when(bookAdminCreateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminCreateRequestDto.getTags()).thenReturn(List.of(1L));
        when(bookAdminCreateRequestDto.getFileIdList()).thenReturn(List.of(1L));
        when(bookAdminCreateRequestDto.getAuthorIdList()).thenReturn(List.of(1L));
        when(bookAdminCreateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Category.class)));
        when(bookCategoryRepository.saveAll(any())).thenReturn(Collections.emptyList());
        when(tagRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Tag.class)));
        when(bookTagRepository.saveAll(any())).thenReturn(Collections.emptyList());
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(mock(Author.class)));
        when(bookAuthorRepository.saveAll(any())).thenReturn(Collections.emptyList());
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(fileRepository.findById(anyLong())).thenReturn(Optional.of(mock(File.class)));
        when(bookFileRepository.saveAll(any())).thenReturn(Collections.emptyList());

        bookService.createBook(file, bookAdminCreateRequestDto);

        verify(bookRepository, times(1)).save(any(Book.class));
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(tagRepository, times(1)).findById(anyLong());
        verify(fileRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(authorRepository, times(1)).findById(anyLong());

        verify(bookFileRepository, times(1)).saveAll(any());
        verify(bookTagRepository, times(1)).saveAll(any());
        verify(bookAuthorRepository, times(1)).saveAll(any());
        verify(bookCategoryRepository, times(1)).saveAll(any());
    }

    @Test
    @DisplayName("도서 등록 - 출판사 검증 실패")
    void givenInvalidPublisher_whenCreateBook_thenThrowNotFoundException() {
        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);

        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.createBook(file, bookAdminCreateRequestDto));

        verify(publisherRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("도서 등록 - 작가 검증 실패")
    void givenInvalidAuthor_whenCreateBook_thenThrowNotFoundException() {
        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);
        when(bookAdminCreateRequestDto.getAuthorIdList()).thenReturn(List.of(1L));
        when(bookRepository.save(any())).thenReturn(mock(Book.class));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.createBook(file, bookAdminCreateRequestDto));

        verify(bookRepository, times(1)).save(any());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(authorRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("도서 등록 - 카테고리 검증 실패")
    void givenInvalidCategory_whenCreateBook_thenThrowNotFoundException() {
        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);
        Book book = Book.builder().build();

        when(bookAdminCreateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminCreateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.empty());
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));

        assertThrows(NotFoundException.class,
            () -> bookService.createBook(file, bookAdminCreateRequestDto));

        verify(bookRepository, times(1)).save(any(Book.class));
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("도서 등록 - 태그 검증 실패")
    void givenInvalidTag_whenCreateBook_thenThrowNotFoundException() {
        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);
        Book book = Book.builder().build();

        when(bookAdminCreateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminCreateRequestDto.getTags()).thenReturn(List.of(1L));
        when(bookAdminCreateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Category.class)));
        when(bookCategoryRepository.saveAll(any())).thenReturn(Collections.emptyList());
        when(tagRepository.findById(anyLong())).thenReturn(
            Optional.empty());
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> bookService.createBook(file, bookAdminCreateRequestDto));
        assertEquals(BookMessageEnum.BOOK_TAG_NOT_FOUND.getMessage(),
            notFoundException.getMessage());

        verify(bookRepository, times(1)).save(any(Book.class));
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(tagRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());

        verify(bookCategoryRepository, times(1)).saveAll(any());
    }

    @Test
    @DisplayName("도서 수정")
    void whenUpdateBookByAdmin() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookAdminUpdateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminUpdateRequestDto.getTags()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getFileIdList()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getAuthorIdList()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));

        when(authorRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Author.class)));
        doNothing().when(bookAuthorRepository).deleteAllByBook_BookId(anyLong());
        when(bookAuthorRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(fileRepository.findById(anyLong())).thenReturn(Optional.of(mock(File.class)));
        doNothing().when(bookFileRepository).deleteAllByBook_BookId(anyLong());
        when(bookFileRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Category.class)));
        doNothing().when(bookCategoryRepository).deleteByPk_BookId(anyLong());
        when(bookCategoryRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(mock(Tag.class)));
        doNothing().when(bookTagRepository).deleteAllByPk_BookId(anyLong());
        when(bookTagRepository.saveAll(any())).thenReturn(Collections.emptyList());

        bookService.updateBookByAdmin(1L, file, bookAdminUpdateRequestDto);

        verify(authorRepository, times(1)).findById(anyLong());

        verify(publisherRepository, times(1)).findById(anyLong());

        verify(bookFileRepository).saveAll(any());
        verify(bookCategoryRepository).saveAll(any());
    }

    @Test
    @DisplayName("도서 업데이트 - 도서 검증 실패")
    void givenInvalidBook_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, file, bookAdminUpdateRequestDto));
        assertEquals(BookMessageEnum.BOOK_NOT_FOUND.getMessage(),
            notFoundException.getMessage());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("도서 업데이트 - 출판사 검증 실패")
    void givenInvalidPublisher_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, file, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("도서 업데이트 - 도서 상태 검증 실패")
    void givenInvalidBookStatus_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(bookStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, file, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(bookStatusRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("도서 업데이트 - 파일 검증 실패")
    void givenInvalidBookFile_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookAdminUpdateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminUpdateRequestDto.getTags()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getFileIdList()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getAuthorIdList()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));

        when(authorRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Author.class)));
        doNothing().when(bookAuthorRepository).deleteAllByBook_BookId(anyLong());
        when(bookAuthorRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(fileRepository.findById(anyLong())).thenReturn(Optional.empty());

        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Category.class)));
        doNothing().when(bookCategoryRepository).deleteByPk_BookId(anyLong());
        when(bookCategoryRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(mock(Tag.class)));
        doNothing().when(bookTagRepository).deleteAllByPk_BookId(anyLong());
        when(bookTagRepository.saveAll(any())).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, file, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(bookStatusRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("도서 업데이트 - 태그 검증 실패")
    void givenInvalidBookTag_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookAdminUpdateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminUpdateRequestDto.getTags()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getAuthorIdList()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));

        when(authorRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Author.class)));
        doNothing().when(bookAuthorRepository).deleteAllByBook_BookId(anyLong());
        when(bookAuthorRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Category.class)));
        doNothing().when(bookCategoryRepository).deleteByPk_BookId(anyLong());
        when(bookCategoryRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, file, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(bookStatusRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("도서 업데이트 - 카테고리 검증 실패")
    void givenInvalidBookCategory_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookAdminUpdateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminUpdateRequestDto.getAuthorIdList()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));

        when(authorRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Author.class)));
        doNothing().when(bookAuthorRepository).deleteAllByBook_BookId(anyLong());
        when(bookAuthorRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, file, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(bookStatusRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("도서 업데이트 - 작가 검증 실패")
    void givenInvalidBookAuthor_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookAdminUpdateRequestDto.getAuthorIdList()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));

        when(authorRepository.findById(anyLong())).thenReturn(
            Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, file, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(bookStatusRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("입력값과 저장된 재고를 비교")
    void validateStock() {
        BookStockUpdateRequestDto bookStockUpdateRequestDto = BookStockUpdateRequestDto.builder()
            .bookId(1L).amount(1).build();
        when(bookRepository.findBookByBookIdIn(anyList())).thenReturn(
            List.of(Book.builder().stock(2).build()));

        Boolean validateResult = bookService.validateStock(List.of(bookStockUpdateRequestDto));
        assertTrue(validateResult);
    }

    @Test
    @DisplayName("입력값과 저장된 재고를 비교 - 재고 부족")
    void givenInvalidInput_whenValidateStock_thenThrowConflictException() {
        List<BookStockUpdateRequestDto> bookStockUpdateRequestDto = List.of(
            BookStockUpdateRequestDto.builder()
                .bookId(1L).amount(1).build());
        when(bookRepository.findBookByBookIdIn(anyList())).thenReturn(
            List.of(Book.builder().stock(0).build()));

        assertThrows(ConflictException.class,
            () -> bookService.validateStock(bookStockUpdateRequestDto));
        verify(bookRepository, times(1)).findBookByBookIdIn(anyList());
    }

    @Test
    @DisplayName("재고 업데이트")
    void whenUpdateStock() {
        BookStockUpdateRequestDto bookStockUpdateRequestDto = BookStockUpdateRequestDto.builder()
            .bookId(1L).amount(1).build();
        when(bookRepository.findBookByBookIdIn(anyList())).thenReturn(
            List.of(Book.builder().bookTitle("bookTitle").stock(1).build()));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));

        bookService.updateStock(List.of(bookStockUpdateRequestDto));

        verify(bookRepository, times(1)).findBookByBookIdIn(anyList());
    }

    @Test
    @DisplayName("재고 업데이트 - 재고 부족")
    void givenInvalidInput_whenUpdateStock_thenThrowConflictException() {
        List<BookStockUpdateRequestDto> bookStockUpdateRequestDto = List.of(
            BookStockUpdateRequestDto.builder()
                .bookId(1L).amount(1).build());
        when(bookRepository.findBookByBookIdIn(anyList())).thenReturn(
            List.of(Book.builder().bookTitle("bookTitle").stock(0).build()));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));

        assertThrows(ConflictException.class,
            () -> bookService.updateStock(bookStockUpdateRequestDto));

        verify(bookRepository, times(1)).findBookByBookIdIn(anyList());
    }

    @Test
    @DisplayName("재고 업데이트 - 도서 상태 검증 실패")
    void givenInvalidBookStatus_whenUpdateStock_thenThrowNotFoundException() {
        List<BookStockUpdateRequestDto> bookStockUpdateRequestDto = List.of(mock(
            BookStockUpdateRequestDto.class));

        when(bookRepository.findBookByBookIdIn(anyList())).thenReturn(
            List.of(Book.builder().bookTitle("bookTitle").stock(0).build()));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.updateStock(bookStockUpdateRequestDto));
    }
}