package com.nhnacademy.inkbridge.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * class: ShoppingCart.
 *
 * @author brihgtclo
 * @version 2/14/24
 */
@Entity
@Table(name = "review_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewFile {

    @Id
    @Column(name = "file_id")
    private Long fileId;

    @MapsId("fileId")
    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public ReviewFile(Long fileId, Review review) {
        this.fileId = fileId;
        this.review = review;
    }
}