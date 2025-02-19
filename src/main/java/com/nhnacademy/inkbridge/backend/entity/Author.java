package com.nhnacademy.inkbridge.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: Author.
 *
 * @author nhn
 * @version 2024/02/08
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "author")
public class Author {

    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "author_introduce")
    private String authorIntroduce;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    @Builder
    public Author(Long authorId, String authorName, String authorIntroduce, File file) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorIntroduce = authorIntroduce;
        this.file = file;
    }

    /**
     * 작가 정보를 수정하는 메서드입니다.
     *
     * @param authorName      String
     * @param authorIntroduce String
     * @param file            File
     */
    public void updateAuthor(String authorName, String authorIntroduce, File file) {
        this.authorName = authorName;
        this.authorIntroduce = authorIntroduce;
        this.file = file;
    }
}
