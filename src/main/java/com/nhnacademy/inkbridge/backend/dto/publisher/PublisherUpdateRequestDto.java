package com.nhnacademy.inkbridge.backend.dto.publisher;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * class: PublisherUpdateRequestDto.
 *
 * @author choijaehun
 * @version 2024/03/20
 */

@Getter
@EqualsAndHashCode
public class PublisherUpdateRequestDto {

    @NotBlank
    @Size(min = 1, max = 30)
    private String publisherName;
}
