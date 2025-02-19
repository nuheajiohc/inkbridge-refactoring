package com.nhnacademy.inkbridge.backend.dto.order;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * class: WrappingCreateRequestDto.
 *
 * @author JBum
 * @version 2024/03/12
 */
@Getter
@AllArgsConstructor
@ToString
public class WrappingCreateRequestDto {

    @NotBlank(message = "포장지 이름이 공란입니다.")
    @Length(max = 20, message = "20글자가 최대입니다")
    private String wrappingName;

    @NotNull(message = "가격을 입력해주세요.")
    @Min(value = 0, message = "최소 0원이상의 가격으로 작성해주세요")
    private Long price;

    @Column(nullable = false, columnDefinition = "boolean default true")
    @NotNull
    private Boolean isActive;
}
