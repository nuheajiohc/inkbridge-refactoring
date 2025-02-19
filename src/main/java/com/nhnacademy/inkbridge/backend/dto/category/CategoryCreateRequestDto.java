package com.nhnacademy.inkbridge.backend.dto.category;

import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: CategoryCreateRequestDto.
 *
 * @author choijaehun
 * @version 2/16/24
 */

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class CategoryCreateRequestDto {

    @Size(min = 1, max = 10)
    private String categoryName;
    private Long parentId;
}
