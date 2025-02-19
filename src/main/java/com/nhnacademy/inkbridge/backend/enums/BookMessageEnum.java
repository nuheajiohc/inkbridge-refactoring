package com.nhnacademy.inkbridge.backend.enums;

/**
 * class: BookMessageEnum.
 *
 * @author minm063
 * @version 2024/02/15
 */
public enum BookMessageEnum {
    BOOK_NOT_FOUND("존재하지 않는 도서입니다."),
    BOOK_THUMBNAIL_NOT_FOUND("도서 썸네일을 찾지 못했습니다."),
    BOOK_PUBLISHER_NOT_FOUND("도서 출판사를 찾지 못했습니다."),
    BOOK_AUTHOR_NOT_FOUND("도서 작가를 찾지 못했습니다."),
    BOOK_TAG_NOT_FOUND("도서 태그를 찾지 못했습니다."),
    BOOK_CATEGORY_NOT_FOUND("도서 카테고리를 찾지 못했습니다."),
    BOOK_STATUS_NOT_FOUND("도서 상태를 찾지 못했습니다."),
    BOOK_FILE_NOT_FOUND("도서 파일을 찾지 못했습니다.");

    private final String message;

    BookMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
