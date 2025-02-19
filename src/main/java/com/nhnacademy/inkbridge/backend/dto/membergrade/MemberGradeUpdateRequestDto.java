package com.nhnacademy.inkbridge.backend.dto.membergrade;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: MemberGradeUpdateRequestDto.
 *
 * @author jeongbyeonghun
 * @version 3/6/24
 */

@Getter
@Setter
@NoArgsConstructor
public class MemberGradeUpdateRequestDto {

    private BigDecimal pointRate;

    private Long standardAmount;

}
