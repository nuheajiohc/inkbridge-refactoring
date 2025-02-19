package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.BDDMockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookOrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewAverageReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.BookService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: BookRestControllerTest.
 *
 * @author minm063
 * @version 2024/02/16
 */
@AutoConfigureRestDocs
@WebMvcTest(BookController.class)
@ExtendWith(RestDocumentationExtension.class)
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @MockBean
    Pageable pageable;

    BooksReadResponseDto booksReadResponseDto;

    @BeforeEach
    void setup() {
        BooksPaginationReadResponseDto booksPaginationReadResponseDto = BooksPaginationReadResponseDto.builder()
            .bookId(1L)
            .bookTitle("title")
            .price(1000L)
            .publisherName("publisher")
            .fileUrl("url")
            .build();
        Page<BooksPaginationReadResponseDto> page = new PageImpl<>(
            List.of(booksPaginationReadResponseDto));

        List<AuthorPaginationReadResponseDto> authorPaginationReadResponseDtos = List.of(
            AuthorPaginationReadResponseDto.builder().authorName(List.of("authorName"))
                .build());

        booksReadResponseDto = BooksReadResponseDto.builder()
            .booksPaginationReadResponseDtos(page)
            .authorPaginationReadResponseDto(authorPaginationReadResponseDtos).build();
    }

    @Test
    @DisplayName(value = "메인 페이지 도서 전체 조회")
    void whenReadBooks_thenReturnDtoList() throws Exception {
        when(bookService.readBooks(any(Pageable.class))).thenReturn(booksReadResponseDto);

        mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.booksPaginationReadResponseDtos.content[0].bookId", equalTo(1)))
            .andExpect(jsonPath("$.booksPaginationReadResponseDtos.content[0].bookTitle",
                equalTo("title")))
            .andExpect(
                jsonPath("$.booksPaginationReadResponseDtos.content[0].price", equalTo(1000)))
            .andExpect(jsonPath("$.booksPaginationReadResponseDtos.content[0].publisherName",
                equalTo("publisher")))
            .andExpect(
                jsonPath("$.booksPaginationReadResponseDtos.content[0].fileUrl", equalTo("url")))
            .andExpect(jsonPath("$.authorPaginationReadResponseDto[0].authorName[0]",
                equalTo("authorName")))
            .andDo(document("book/books-get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                relaxedResponseFields(
                    fieldWithPath("booksPaginationReadResponseDtos.content[].bookId").description(
                        "도서 번호"),
                    fieldWithPath(
                        "booksPaginationReadResponseDtos.content[].bookTitle").description("도서 이름"),
                    fieldWithPath("booksPaginationReadResponseDtos.content[].price").description(
                        "도서 판매가"),
                    fieldWithPath(
                        "booksPaginationReadResponseDtos.content[].publisherName").description(
                        "출판사 이름"),
                    fieldWithPath("booksPaginationReadResponseDtos.content[].fileUrl").description(
                        "도서 썸네일"),
                    fieldWithPath("booksPaginationReadResponseDtos.totalPages").description(
                        "총 페이지"),
                    fieldWithPath("booksPaginationReadResponseDtos.totalElements").description(
                        "총 개수"),
                    fieldWithPath("booksPaginationReadResponseDtos.size").description("화면에 출력할 개수"),
                    fieldWithPath("booksPaginationReadResponseDtos.number").description("현재 페이지"),
                    fieldWithPath("booksPaginationReadResponseDtos.numberOfElements").description(
                        "현재 페이지 개수"),
                    fieldWithPath("authorPaginationReadResponseDto[].authorName").description(
                        "작가 이름")
                )));
    }

    @Test
    @DisplayName(value = "장바구니 페이지 도서 전체 조회")
    void whenGetCartBooks() throws Exception {
        List<BookOrderReadResponseDto> bookOrderReadResponseDto = List.of(
            BookOrderReadResponseDto.builder()
                .bookId(1L).bookTitle("bookTitle").regularPrice(10000L).price(1000L)
                .discountRatio(BigDecimal.valueOf(33.3)).stock(100).isPackagable(true)
                .thumbnail("thumbnail").build());

        when(bookService.getCartBooks(anySet())).thenReturn(bookOrderReadResponseDto);

        mockMvc.perform(get("/api/books/orders")
                .param("book-id", "1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[0].bookId", equalTo(1)))
            .andExpect(jsonPath("$.[0].bookTitle", equalTo("bookTitle")))
            .andExpect(jsonPath("$.[0].regularPrice", equalTo(10000)))
            .andExpect(jsonPath("$.[0].price", equalTo(1000)))
            .andExpect(jsonPath("$.[0].discountRatio", equalTo(33.3)))
            .andExpect(jsonPath("$.[0].stock", equalTo(100)))
            .andExpect(jsonPath("$.[0].isPackagable", equalTo(true)))
            .andExpect(jsonPath("$.[0].thumbnail", equalTo("thumbnail")))
            .andDo(document("book/books-get-cart",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(parameterWithName("book-id").description("도서 번호")),
                responseFields(
                    fieldWithPath("[].bookId").description("도서 번호"),
                    fieldWithPath("[].bookTitle").description("도서 이름"),
                    fieldWithPath("[].regularPrice").description("정가"),
                    fieldWithPath("[].price").description("판매가"),
                    fieldWithPath("[].discountRatio").description("할인율"),
                    fieldWithPath("[].stock").description("재고"),
                    fieldWithPath("[].isPackagable").description("포장 가능 여부"),
                    fieldWithPath("[].thumbnail").description("썸네일")
                )));
    }

    @Test
    @DisplayName(value = "도서 상세 조회")
    void whenReadBook_thenReturnDto() throws Exception {
        Map<Long, String> author = new HashMap<>();
        author.put(1L, "author");

        BookDetailReadResponseDto bookDetailReadResponseDto = BookDetailReadResponseDto.builder()
            .bookTitle("title").bookIndex("index").description("description").publicatedAt(
                LocalDate.now()).isbn("1234567890123").regularPrice(10000L).price(8000L)
            .discountRatio(new BigDecimal("3.3"))
            .isPackagable(true).thumbnail("thumbnail").publisherId(1L).publisherName("publisher")
            .authors(author).wish(1L).fileUrl(Set.of("url")).tagName(Set.of("tag"))
            .categoryName(Set.of("category")).build();
        ReviewAverageReadResponseDto reviewAverageReadResponseDto = ReviewAverageReadResponseDto.builder()
            .avg(3.33).count(1L).build();

        when(bookService.readBook(any(Pageable.class), anyLong(), anyLong())).thenReturn(
            BookReadResponseDto.builder().bookDetailReadResponseDto(bookDetailReadResponseDto)
                .reviewAverageReadResponseDto(reviewAverageReadResponseDto).build());

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/books/{bookId}", 1L)
                .param("memberId", "1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.bookTitle", equalTo("title")))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.bookIndex", equalTo("index")))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.description", equalTo("description")))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.isbn", equalTo("1234567890123")))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.regularPrice", equalTo(10000)))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.price", equalTo(8000)))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.discountRatio", equalTo(3.3)))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.isPackagable", equalTo(true)))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.thumbnail", equalTo("thumbnail")))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.publisherId", equalTo(1)))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.publisherName", equalTo("publisher")))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.authors", hasKey("1")))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.wish", equalTo(1)))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.fileUrl[0]", equalTo("url")))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.tagName[0]", equalTo("tag")))
            .andExpect(jsonPath("$.bookDetailReadResponseDto.categoryName[0]", equalTo("category")))
            .andDo(document("book/book-get-detail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("bookId").description("도서 번호")),
                requestParameters(parameterWithName("memberId").description("회원 번호")),
                responseFields(
                    fieldWithPath("bookDetailReadResponseDto.bookTitle").description("도서 이름"),
                    fieldWithPath("bookDetailReadResponseDto.bookIndex").description("도서 목차"),
                    fieldWithPath("bookDetailReadResponseDto.description").description("도서 설명"),
                    fieldWithPath("bookDetailReadResponseDto.publicatedAt").description("출판일시"),
                    fieldWithPath("bookDetailReadResponseDto.isbn").description("도서 isbn"),
                    fieldWithPath("bookDetailReadResponseDto.regularPrice").description("도서 정가"),
                    fieldWithPath("bookDetailReadResponseDto.price").description("도서 판매가"),
                    fieldWithPath("bookDetailReadResponseDto.discountRatio").description("도서 할인율"),
                    fieldWithPath("bookDetailReadResponseDto.isPackagable").description("도서 포장여부"),
                    fieldWithPath("bookDetailReadResponseDto.thumbnail").description("도서 썸네일"),
                    fieldWithPath("bookDetailReadResponseDto.statusName").description("도서 상태 이름"),
                    fieldWithPath("bookDetailReadResponseDto.publisherId").description("출판사 번호"),
                    fieldWithPath("bookDetailReadResponseDto.publisherName").description("출판사 이름"),
                    subsectionWithPath("bookDetailReadResponseDto.authors").description("작가"),
                    fieldWithPath("bookDetailReadResponseDto.wish").description("좋아요"),
                    fieldWithPath("bookDetailReadResponseDto.view").description("조회수"),
                    fieldWithPath("bookDetailReadResponseDto.fileUrl[]").description("도서 상세 이미지"),
                    fieldWithPath("bookDetailReadResponseDto.tagName[]").description("태그 이름"),
                    fieldWithPath("bookDetailReadResponseDto.categoryName[]").description(
                        "카테고리 이름"),
                    fieldWithPath("reviewAverageReadResponseDto.avg").description("리뷰 평점 평균"),
                    fieldWithPath("reviewAverageReadResponseDto.count").description("도서가 가지는 리뷰 개수")
                )));
    }
}