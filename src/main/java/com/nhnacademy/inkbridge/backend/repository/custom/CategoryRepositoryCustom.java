package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.category.CategoryNameReadResponseDto;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: CategoryRepositoryCustom.
 *
 * @author minm063
 * @version 2024/03/19
 */
@NoRepositoryBean
public interface CategoryRepositoryCustom {

    /**
     * categoryId로 카테고리 이름과 부모 카테고리 이름을 조회하는 메서드입니다.
     *
     * @param categoryId Long
     * @return CategoryNameReadResponseDto
     */
    CategoryNameReadResponseDto findCategoryByCategoryId(Long categoryId);
}
