package com.nhnacademy.inkbridge.backend.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: Category.
 *
 * @author nhn
 * @version 2024/02/08
 * @modifiedBy JBUM
 * @modifiedAt 2024/03/04
 * @modificationReason - 생성자(Builder) 추가
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category categoryParent;

    @OneToMany(mappedBy = "categoryParent")
    private List<Category> categoryChildren = new ArrayList<>();

    @Builder(builderMethodName = "create")
    public Category(String categoryName, Category categoryParent) {
        this.categoryName = categoryName;
        this.categoryParent = categoryParent;
    }

    @Builder
    public Category(Long categoryId, String categoryName, Category categoryParent,
        List<Category> categoryChildren) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryParent = categoryParent;
        this.categoryChildren = categoryChildren;
    }

    public void updateCategory(String categoryName) {
        this.categoryName = categoryName;
    }
}
