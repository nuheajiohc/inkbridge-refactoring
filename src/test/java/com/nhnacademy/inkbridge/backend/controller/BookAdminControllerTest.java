package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.author.AuthorPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.author.AuthorReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminSelectedReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.PublisherReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.bookstatus.BookStatusReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.ParentCategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Tag;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.BookService;
import com.nhnacademy.inkbridge.backend.service.FileService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

/**
 * class: BookAdminRestControllerTest.
 *
 * @author minm063
 * @version 2024/02/16
 */
@AutoConfigureRestDocs
@WebMvcTest(BookAdminController.class)
@ExtendWith(RestDocumentationExtension.class)
class BookAdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;
    @MockBean
    FileService fileService;

    @MockBean
    Pageable pageable;

    ParentCategoryReadResponseDto parentCategoryReadResponseDto;
    PublisherReadResponseDto publisherReadResponseDto;
    AuthorReadResponseDto authorReadResponseDto;
    BookStatusReadResponseDto bookStatusReadResponseDtoList;
    TagReadResponseDto tagReadResponseDto;

    @BeforeEach
    void setup() {
        parentCategoryReadResponseDto = new ParentCategoryReadResponseDto(
            Category.create().categoryName("category").build());
        publisherReadResponseDto = PublisherReadResponseDto.builder().publisherId(1L)
            .publisherName("publisher").build();
        authorReadResponseDto = AuthorReadResponseDto.builder().authorId(1L).authorName("author")
            .build();
        bookStatusReadResponseDtoList = BookStatusReadResponseDto.builder().statusId(1L)
            .statusName("status").build();
        tagReadResponseDto = TagReadResponseDto.builder()
            .tag(Tag.builder().tagId(1L).tagName("tag").build()).build();
    }

    @Test
    @DisplayName("관리자 페이지 도서 목록 조회")
    void whenAdminReadBooks_thenReturnDtoList() throws Exception {
        BooksAdminPaginationReadResponseDto booksAdminPaginationReadResponseDto = BooksAdminPaginationReadResponseDto.builder()
            .bookId(1L).bookTitle("title").publisherName("publisher").statusName("status").build();
        Page<BooksAdminPaginationReadResponseDto> page = new PageImpl<>(
            List.of(booksAdminPaginationReadResponseDto));
        AuthorPaginationReadResponseDto authorPaginationReadResponseDto = AuthorPaginationReadResponseDto.builder()
            .authorName(List.of("authorName")).build();
        BooksAdminReadResponseDto booksAdminReadResponseDto = BooksAdminReadResponseDto.builder()
            .booksAdminPaginationReadResponseDtos(page)
            .authorPaginationReadResponseDtos(List.of(authorPaginationReadResponseDto)).build();

        when(bookService.readBooksByAdmin(any(Pageable.class))).thenReturn(
            booksAdminReadResponseDto);

        mockMvc.perform(get("/api/admin/books")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.booksAdminPaginationReadResponseDtos.content.[0].bookId", equalTo(1)))
            .andExpect(jsonPath("$.booksAdminPaginationReadResponseDtos.content.[0].bookTitle",
                equalTo("title")))
            .andExpect(jsonPath("$.booksAdminPaginationReadResponseDtos.content.[0].publisherName",
                equalTo("publisher")))
            .andExpect(jsonPath("$.booksAdminPaginationReadResponseDtos.content.[0].statusName",
                equalTo("status")))
            .andExpect(jsonPath("$.authorPaginationReadResponseDtos[0].authorName[0]",
                equalTo("authorName")))
            .andDo(document("book/admin/books-get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                relaxedResponseFields(
                    fieldWithPath(
                        "booksAdminPaginationReadResponseDtos.content[].bookId").description(
                        "도서 번호"),
                    fieldWithPath(
                        "booksAdminPaginationReadResponseDtos.content[].bookTitle").description(
                        "도서 이름"),
                    fieldWithPath("authorPaginationReadResponseDtos[].authorName[]").description(
                        "저자 이름"),
                    fieldWithPath(
                        "booksAdminPaginationReadResponseDtos.content[].publisherName").description(
                        "출판사 이름"),
                    fieldWithPath(
                        "booksAdminPaginationReadResponseDtos.content[].statusName").description(
                        "도서 상태 이름"),
                    fieldWithPath("authorPaginationReadResponseDtos[].authorName[]").description(
                        "작가 이름"),
                    fieldWithPath("booksAdminPaginationReadResponseDtos.totalPages").description(
                        "총 페이지"),
                    fieldWithPath("booksAdminPaginationReadResponseDtos.totalElements").description(
                        "총 개수"),
                    fieldWithPath("booksAdminPaginationReadResponseDtos.size").description(
                        "화면에 출력할 개수"),
                    fieldWithPath("booksAdminPaginationReadResponseDtos.number").description(
                        "현재 페이지"),
                    fieldWithPath(
                        "booksAdminPaginationReadResponseDtos.numberOfElements").description(
                        "현재 페이지 개수"))));
    }

    @Test
    @DisplayName("도서 번호로 도서 상세 조회")
    void givenBookId_whenAdminReadBook_thenReturnDto() throws Exception {
        BookAdminSelectedReadResponseDto bookAdminSelectedReadResponseDto = BookAdminSelectedReadResponseDto.builder()
            .bookTitle("title")
            .bookIndex("index")
            .description("description")
            .publicatedAt(LocalDate.of(2022, 2, 27))
            .isbn("1234567890123")
            .regularPrice(1L)
            .price(1L)
            .discountRatio(BigDecimal.valueOf(33.3))
            .stock(100)
            .isPackagable(true)
            .authorIdList(List.of(1L))
            .publisherId(1L)
            .statusId(1L)
            .url("test")
            .build();

        BookAdminDetailReadResponseDto bookAdminDetailReadResponseDto = BookAdminDetailReadResponseDto.builder()
            .adminSelectedReadResponseDto(bookAdminSelectedReadResponseDto)
            .parentCategoryReadResponseDtoList(List.of(parentCategoryReadResponseDto))
            .publisherReadResponseDtoList(List.of(publisherReadResponseDto))
            .authorReadResponseDtoList(List.of(authorReadResponseDto))
            .bookStatusReadResponseDtoList(List.of(bookStatusReadResponseDtoList))
            .tagReadResponseDtoList(List.of(tagReadResponseDto))
            .build();

        when(bookService.readBookByAdmin(anyLong())).thenReturn(bookAdminDetailReadResponseDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/admin/books/{bookId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.adminSelectedReadResponseDto.bookTitle", equalTo("title")))
            .andExpect(jsonPath("$.adminSelectedReadResponseDto.bookIndex", equalTo("index")))
            .andExpect(
                jsonPath("$.adminSelectedReadResponseDto.description", equalTo("description")))
            .andExpect(
                jsonPath("$.adminSelectedReadResponseDto.publicatedAt", equalTo("2022-02-27")))
            .andExpect(jsonPath("$.adminSelectedReadResponseDto.isbn", equalTo("1234567890123")))
            .andExpect(jsonPath("$.adminSelectedReadResponseDto.regularPrice", equalTo(1)))
            .andExpect(jsonPath("$.adminSelectedReadResponseDto.price", equalTo(1)))
            .andExpect(jsonPath("$.adminSelectedReadResponseDto.discountRatio", equalTo(33.3)))
            .andExpect(jsonPath("$.adminSelectedReadResponseDto.stock", equalTo(100)))
            .andExpect(jsonPath("$.adminSelectedReadResponseDto.isPackagable", equalTo(true)))
            .andExpect(jsonPath("$.adminSelectedReadResponseDto.authorIdList[0]", equalTo(1)))
            .andExpect(jsonPath("$.adminSelectedReadResponseDto.publisherId", equalTo(1)))
            .andExpect(jsonPath("$.adminSelectedReadResponseDto.statusId", equalTo(1)))
            .andExpect(jsonPath("$.adminSelectedReadResponseDto.url", equalTo("test")))
            .andExpect(jsonPath("$.parentCategoryReadResponseDtoList[0].categoryId", equalTo(null)))
            .andExpect(jsonPath("$.parentCategoryReadResponseDtoList[0].categoryName",
                equalTo("category")))
            .andExpect(jsonPath("$.publisherReadResponseDtoList[0].publisherId", equalTo(1)))
            .andExpect(
                jsonPath("$.publisherReadResponseDtoList[0].publisherName", equalTo("publisher")))
            .andExpect(jsonPath("$.authorReadResponseDtoList[0].authorId", equalTo(1)))
            .andExpect(jsonPath("$.authorReadResponseDtoList[0].authorName", equalTo("author")))
            .andExpect(jsonPath("$.bookStatusReadResponseDtoList[0].statusId", equalTo(1)))
            .andExpect(jsonPath("$.bookStatusReadResponseDtoList[0].statusName", equalTo("status")))
            .andExpect(jsonPath("$.tagReadResponseDtoList[0].tagId", equalTo(1)))
            .andExpect(jsonPath("$.tagReadResponseDtoList[0].tagName", equalTo("tag")))
            .andDo(document("book/admin/book-get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("bookId").description("도서 번호")),
                responseFields(
                    fieldWithPath("adminSelectedReadResponseDto.bookTitle").description("도서 이름"),
                    fieldWithPath("adminSelectedReadResponseDto.bookIndex").description("목차"),
                    fieldWithPath("adminSelectedReadResponseDto.description").description("도서 설명"),
                    fieldWithPath("adminSelectedReadResponseDto.publicatedAt").description("출판일자"),
                    fieldWithPath("adminSelectedReadResponseDto.isbn").description("isbn"),
                    fieldWithPath("adminSelectedReadResponseDto.regularPrice").description("정가"),
                    fieldWithPath("adminSelectedReadResponseDto.price").description("판매가"),
                    fieldWithPath("adminSelectedReadResponseDto.discountRatio").description("할인율"),
                    fieldWithPath("adminSelectedReadResponseDto.stock").description("재고"),
                    fieldWithPath("adminSelectedReadResponseDto.isPackagable").description("포장 여부"),
                    fieldWithPath("adminSelectedReadResponseDto.authorIdList").description(
                        "작가 번호 리스트"),
                    fieldWithPath("adminSelectedReadResponseDto.publisherId").description("출판사 번호"),
                    fieldWithPath("adminSelectedReadResponseDto.statusId").description("도서 상태 번호"),
                    fieldWithPath("adminSelectedReadResponseDto.url").description("도서 썸네일"),
                    fieldWithPath("adminSelectedReadResponseDto.categoryIdList").description(
                        "카테고리 번호 리스트"),
                    fieldWithPath("adminSelectedReadResponseDto.tagIdList").description(
                        "태그 번호 리스트"),
                    fieldWithPath("parentCategoryReadResponseDtoList[].categoryId").description(
                        "전체 출판사 번호 번호"),
                    fieldWithPath("parentCategoryReadResponseDtoList[].categoryName").description(
                        "전체 출판사 이름"),
                    fieldWithPath(
                        "parentCategoryReadResponseDtoList[].parentCategories").description(
                        "전체 출판사 이름"),
                    fieldWithPath("publisherReadResponseDtoList[].publisherId").description(
                        "전체 출판사 번호 번호"),
                    fieldWithPath("publisherReadResponseDtoList[].publisherName").description(
                        "전체 출판사 이름"),
                    fieldWithPath("authorReadResponseDtoList[].authorId").description(
                        "전체 출판사 번호 번호"),
                    fieldWithPath("authorReadResponseDtoList[].authorName").description(
                        "전체 출판사 이름"),
                    fieldWithPath("bookStatusReadResponseDtoList").description("전체 도서 상태 번호 리스트"),
                    fieldWithPath("bookStatusReadResponseDtoList[].statusId").description(
                        "전체 출판사 번호 번호"),
                    fieldWithPath("bookStatusReadResponseDtoList[].statusName").description(
                        "전체 출판사 이름"),
                    fieldWithPath("tagReadResponseDtoList[].tagId").description("전체 출판사 번호 번호"),
                    fieldWithPath("tagReadResponseDtoList[].tagName").description("전체 출판사 이름")
                )));
    }

    @Test
    @DisplayName("도서 등록 페이지")
    void whenAdminReadBook_thenReturnDto() throws Exception {
        BookAdminReadResponseDto bookAdminReadResponseDto = BookAdminReadResponseDto.builder()
            .parentCategoryReadResponseDtoList(List.of(parentCategoryReadResponseDto))
            .publisherReadResponseDtoList(List.of(publisherReadResponseDto))
            .authorReadResponseDtoList(List.of(authorReadResponseDto))
            .bookStatusReadResponseDtoList(List.of(bookStatusReadResponseDtoList))
            .tagReadResponseDtoList(List.of(tagReadResponseDto))
            .build();

        when(bookService.readBookByAdmin()).thenReturn(bookAdminReadResponseDto);

        mockMvc.perform(get("/api/admin/books/form")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.parentCategoryReadResponseDtoList[0].categoryId", equalTo(null)))
            .andExpect(jsonPath("$.parentCategoryReadResponseDtoList[0].categoryName",
                equalTo("category")))
            .andExpect(jsonPath("$.publisherReadResponseDtoList[0].publisherId", equalTo(1)))
            .andExpect(
                jsonPath("$.publisherReadResponseDtoList[0].publisherName", equalTo("publisher")))
            .andExpect(jsonPath("$.authorReadResponseDtoList[0].authorId", equalTo(1)))
            .andExpect(jsonPath("$.authorReadResponseDtoList[0].authorName", equalTo("author")))
            .andExpect(jsonPath("$.bookStatusReadResponseDtoList[0].statusId", equalTo(1)))
            .andExpect(jsonPath("$.bookStatusReadResponseDtoList[0].statusName", equalTo("status")))
            .andExpect(jsonPath("$.tagReadResponseDtoList[0].tagId", equalTo(1)))
            .andExpect(jsonPath("$.tagReadResponseDtoList[0].tagName", equalTo("tag")))
            .andDo(document("book/admin/book-get-form",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("parentCategoryReadResponseDtoList[].categoryId").description(
                        "전체 출판사 번호 번호"),
                    fieldWithPath("parentCategoryReadResponseDtoList[].categoryName").description(
                        "전체 출판사 이름"),
                    fieldWithPath(
                        "parentCategoryReadResponseDtoList[].parentCategories").description(
                        "전체 출판사 이름"),
                    fieldWithPath("publisherReadResponseDtoList[].publisherId").description(
                        "전체 출판사 번호 번호"),
                    fieldWithPath("publisherReadResponseDtoList[].publisherName").description(
                        "전체 출판사 이름"),
                    fieldWithPath("authorReadResponseDtoList[].authorId").description(
                        "전체 출판사 번호 번호"),
                    fieldWithPath("authorReadResponseDtoList[].authorName").description(
                        "전체 출판사 이름"),
                    fieldWithPath("bookStatusReadResponseDtoList[].statusId").description(
                        "전체 출판사 번호 번호"),
                    fieldWithPath("bookStatusReadResponseDtoList[].statusName").description(
                        "전체 출판사 이름"),
                    fieldWithPath("tagReadResponseDtoList[].tagId").description("전체 출판사 번호 번호"),
                    fieldWithPath("tagReadResponseDtoList[].tagName").description("전체 출판사 이름")
                )
            ));
    }

    @Test
    void whenCreateBook_thenReturnHttpStatus() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BookAdminCreateRequestDto bookAdminCreateRequestDto = new BookAdminCreateRequestDto();
        ReflectionTestUtils.setField(bookAdminCreateRequestDto, "bookTitle", "title");
        ReflectionTestUtils.setField(bookAdminCreateRequestDto, "isbn", "1234567890123");

        String dtoToJson = objectMapper.writeValueAsString(bookAdminCreateRequestDto);
        MockMultipartFile book = new MockMultipartFile("book", "book", "application/json",
            dtoToJson.getBytes());

        MockMultipartFile thumbnail = new MockMultipartFile(
            "image",
            "thumbnail",
            MediaType.IMAGE_PNG_VALUE,
            "thumbnail".getBytes()
        );

        when(fileService.saveThumbnail(any())).thenReturn(File.builder().build());
        doNothing().when(bookService)
            .createBook(mock(File.class), bookAdminCreateRequestDto);

        mockMvc.perform(multipart("/api/admin/books")
                .file(thumbnail)
                .file(book)
            )
            .andExpect(status().isCreated())
            .andDo(document("book/admin/book-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParts(
                    partWithName("book").description("도서 정보"),
                    partWithName("image").description("이미지 url")
                )
            ));
    }

    @Test
    void givenInvalidRequest_whenCreateBook_thenThrowValidationException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BookAdminCreateRequestDto bookAdminCreateRequestDto = new BookAdminCreateRequestDto();

        String dtoToJson = objectMapper.writeValueAsString(bookAdminCreateRequestDto);
        MockMultipartFile book = new MockMultipartFile("book", "book", "application/json",
            dtoToJson.getBytes());

        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        when(fileService.saveThumbnail(any())).thenReturn(File.builder().build());
        doNothing().when(bookService)
            .createBook(mock(File.class), bookAdminCreateRequestDto);

        mockMvc.perform(multipart("/api/admin/books")
                .file(thumbnail)
                .file(book)
            )
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                result -> assertTrue(result.getResolvedException() instanceof ValidationException))
            .andDo(document("book/admin/book-create-fail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParts(
                    partWithName("book").description("도서 정보"),
                    partWithName("image").description("이미지 url")
                ),
                responseFields(fieldWithPath("message").description("오류 메세지"))));
    }

    @Test
    void whenUpdateBook_thenReturnDto() throws Exception {
        MockMultipartHttpServletRequestBuilder builders = RestDocumentationRequestBuilders.multipart(
            "/api/admin/books/{bookId}", 1L);
        builders.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        ObjectMapper objectMapper = new ObjectMapper();
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = new BookAdminUpdateRequestDto();
        ReflectionTestUtils.setField(bookAdminUpdateRequestDto, "bookTitle", "title");
        ReflectionTestUtils.setField(bookAdminUpdateRequestDto, "isbn", "1234567890123");

        String dtoToJson = objectMapper.writeValueAsString(bookAdminUpdateRequestDto);
        MockMultipartFile book = new MockMultipartFile("book", "book",
            "application/json", dtoToJson.getBytes());
        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        when(fileService.saveThumbnail(any())).thenReturn(File.builder().build());
        doNothing().when(bookService).updateBookByAdmin(anyLong(), any(File.class), any());

        mockMvc.perform(builders.file(book).file(thumbnail))
            .andExpect(status().isOk())
            .andDo(document("book/admin/book-update",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("bookId").description("도서 번호")),
                requestParts(
                    partWithName("book").description("도서 정보"),
                    partWithName("image").description("이미지 url")
                )
            ));
    }

    @Test
    void givenInvalidRequest_whenUpdateBook_thenThrowValidationException() throws Exception {
        MockMultipartHttpServletRequestBuilder builders = RestDocumentationRequestBuilders.multipart(
            "/api/admin/books/{bookId}", 1L);
        builders.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        ObjectMapper objectMapper = new ObjectMapper();
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = new BookAdminUpdateRequestDto();

        String dtoToJson = objectMapper.writeValueAsString(bookAdminUpdateRequestDto);
        MockMultipartFile book = new MockMultipartFile("book", "book",
            "application/json", dtoToJson.getBytes());
        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        when(fileService.saveThumbnail(any())).thenReturn(File.builder().build());
        doNothing().when(bookService).updateBookByAdmin(anyLong(), any(File.class), any());

        mockMvc.perform(builders.file(book).file(thumbnail))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                result -> assertTrue(result.getResolvedException() instanceof ValidationException))
            .andDo(document("book/admin/book-update-fail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("bookId").description("도서 번호")),
                requestParts(
                    partWithName("book").description("도서 정보"),
                    partWithName("image").description("이미지 url")
                ),
                responseFields(fieldWithPath("message").description("오류 메세지"))
            ));
    }


}