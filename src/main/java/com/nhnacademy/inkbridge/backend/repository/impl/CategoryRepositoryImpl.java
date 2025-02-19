package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.category.CategoryNameReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.entity.QCategory;
import com.nhnacademy.inkbridge.backend.repository.custom.CategoryRepositoryCustom;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: CategoryRepositoryImpl.
 *
 * @author minm063
 * @version 2024/03/19
 */
public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements
    CategoryRepositoryCustom {

    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryNameReadResponseDto findCategoryByCategoryId(Long categoryId) {
        QCategory category = new QCategory("category");
        QCategory parentCategory = new QCategory("parentCategory");

        return from(category).leftJoin(parentCategory).on(category.categoryParent.categoryId.eq(
            parentCategory.categoryId)).where(category.categoryId.eq(categoryId)).select(
            Projections.constructor(CategoryNameReadResponseDto.class, category.categoryName,
                parentCategory.categoryName)).fetchOne();
    }
}
