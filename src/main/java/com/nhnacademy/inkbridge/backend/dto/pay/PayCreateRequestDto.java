package com.nhnacademy.inkbridge.backend.dto.pay;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: PayCreateRequestDto.
 *
 * @author jangjaehun
 * @version 2024/03/16
 */
@Getter
@NoArgsConstructor
public class PayCreateRequestDto {

    @NotBlank(message = "결제 키 값은 공백일 수 없습니다.")
    @Size(max = 200, message = "결제 키의 길이는 0~200자 사이여야 합니다.")
    private String payKey;

    @NotBlank(message = "주문 코드 값은 공백일 수 없습니다.")
    @Size(min = 32, max = 32, message = "주문 코드는 32자여야 합니댜.")
    private String orderCode;

    @Min(value = 100, message = "결제 금액은 100원 이상이어야 합니다.")
    @NotNull(message = "결제 금액은 필수 항목입니다.")
    private Long totalAmount;

    @Min(value = 0, message = "환불 가능 금액은 0원 이상이어야 합니다.")
    @NotNull(message = "환불 가능 금액은 필수 항목입니다.")
    private Long balanceAmount;

    @NotNull(message = "결제 승인 시간은 필수 항목입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime approvedAt;

    @NotNull(message = "결제 시간은 필수 항목입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime requestedAt;

    @NotNull(message = "부가세는 필수 항목입니다.")
    @Min(value = 0, message = "부가세는 0원 이상이어야 합니다.")
    private Long vat;

    @NotNull(message = "개별 환불 여부는 필수 항목입니다.")
    private Boolean isPartialCancelable;

    @NotBlank(message = "결제 수단은 필수 항목입니다.")
    @Size(max = 10, message = "결제 수단 길이는 0~10자 사이입니다.")
    private String method;

    @NotBlank(message = "결제 상태는 필수 항목입니다.")
    @Size(max = 20, message = "결제 상태 길이는 0~20자 사이입니다.")
    private String status;

    @NotBlank(message = "결제회사 정보는 필수 항목입니다.")
    @Size(max = 64, message = "결제 회사 길이는 0~64자 사이입니다.")
    private String provider;
}
