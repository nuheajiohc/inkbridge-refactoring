package com.nhnacademy.inkbridge.backend.repository.impl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.author.AuthorPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.QAuthor;
import com.nhnacademy.inkbridge.backend.entity.QBook;
import com.nhnacademy.inkbridge.backend.entity.QBookAuthor;
import com.nhnacademy.inkbridge.backend.entity.QFile;
import com.nhnacademy.inkbridge.backend.repository.custom.AuthorRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: AuthorRepositoryImpl.
 *
 * @author minm063
 * @version 2024/03/15
 */
@Slf4j
public class AuthorRepositoryImpl extends QuerydslRepositorySupport implements
    AuthorRepositoryCustom {

    public AuthorRepositoryImpl() {
        super(Author.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorInfoReadResponseDto findByAuthorId(Long authorId) {
        QAuthor author = QAuthor.author;
        QFile file = QFile.file;

        return from(author)
            .innerJoin(file).on(file.eq(author.file))
            .where(author.authorId.eq(authorId))
            .select(Projections.constructor(AuthorInfoReadResponseDto.class, author.authorId,
                author.authorName, author.authorIntroduce, file.fileUrl))
            .fetchOne();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuthorInfoReadResponseDto> findByAuthorName(String authorName) {
        QAuthor author = QAuthor.author;
        QFile file = QFile.file;

        return from(author)
            .innerJoin(file).on(file.eq(author.file))
            .where(author.authorName.eq(authorName))
            .select(Projections.constructor(AuthorInfoReadResponseDto.class, author.authorId,
                author.authorName, author.authorIntroduce, file.fileUrl))
            .fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<AuthorInfoReadResponseDto> findAllAuthors(Pageable pageable) {
        QAuthor author = QAuthor.author;
        QFile file = QFile.file;

        List<AuthorInfoReadResponseDto> authors = from(author)
            .innerJoin(file).on(file.eq(author.file))
            .select(Projections.constructor(AuthorInfoReadResponseDto.class, author.authorId,
                author.authorName, author.authorIntroduce, file.fileUrl))
            .orderBy(author.authorId.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(authors, pageable, getCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuthorPaginationReadResponseDto> findAuthorNameByBookId(List<Long> bookId) {
        QBook book = QBook.book;
        QAuthor author = QAuthor.author;
        QBookAuthor bookAuthor = QBookAuthor.bookAuthor;

        return from(book)
            .innerJoin(bookAuthor).on(bookAuthor.book.eq(book))
            .innerJoin(author).on(author.eq(bookAuthor.author))
            .where(book.bookId.in(bookId))
            .orderBy(book.bookId.desc())
            .select(Projections.constructor(AuthorPaginationReadResponseDto.class,
                list(author.authorName)))
            .transform(groupBy(book.bookId).list(
                Projections.constructor(AuthorPaginationReadResponseDto.class,
                    list(Projections.constructor(String.class, author.authorName))
                )));
    }

    /**
     * 작가 데이터 개수를 조회하는 메서드입니다.
     *
     * @return Long
     */
    private Long getCount() {
        QAuthor author = QAuthor.author;

        return from(author)
            .select(author.count())
            .fetchOne();
    }
}
