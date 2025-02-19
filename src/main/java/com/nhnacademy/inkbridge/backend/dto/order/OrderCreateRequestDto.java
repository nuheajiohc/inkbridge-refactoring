package com.nhnacademy.inkbridge.backend.dto.order;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * class: OrderCreateRequestDto.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    @Valid
    private List<BookOrderDetailCreateRequestDto> bookOrderList;
    @Valid
    private BookOrderCreateRequestDto bookOrder;

    @Getter
    @NoArgsConstructor
    public static class BookOrderDetailCreateRequestDto {

        @NotNull(message = "도서 번호는 필수 항목입니다.")
        private Long bookId;

        @NotNull(message = "가격은 필수 항목 입니다.")
        private Long price;

        @NotNull(message = "수량은 필수 항목입니다.")
        @Min(value = 1, message = "수량은 1개 이상 입력되어야 합니다.")
        private Integer amount;

        private Long wrappingId;

        private Long couponId;

        @NotNull(message = "포장 가격은 필수 항목입니다.")
        @Min(value = 0, message = "포장 가격은 음수가 될 수 없습니다.")
        private Long wrappingPrice;
    }

    @Getter
    @NoArgsConstructor
    public static class BookOrderCreateRequestDto {

        @Size(max = 64, message = "주문 이름은 64자까지 입력 가능합니다.")
        @NotBlank(message = "주문 이름은 필수 항목입니다.")
        private String orderName;

        @Size(max = 20, message = "수취인 이름은 20자까지 입력 가능합니다.")
        @NotBlank(message = "수취인 이름은 필수 항목입니다.")
        private String receiverName;

        @Pattern(regexp = "\\d{11}", message = "전화번호는 11자리 숫자로 입력해주세요")
        @NotBlank(message = "수취인 전화번호는 필수 항목입니다.")
        private String receiverPhoneNumber;

        @Pattern(regexp = "\\d{5}", message = "우편번호는 5자리 숫자로 입력해주세요.")
        @NotBlank(message = "우편 번호는 필수 항목입니다.")
        private String zipCode;

        @Size(max = 200, message = "주소는 200자까지 입력가능합니다.")
        @NotBlank(message = "주소는 필수 항목입니다.")
        private String address;

        @NotBlank(message = "상세 주소는 필수 항목입니다.")
        @Size(max = 200, message = "상세 주소는 200자까지 입력가능합니다.")
        private String detailAddress;

        @NotBlank(message = "주문인 이름은 필수 항목입니다.")
        @Size(max = 20, message = "주문인 이름은 20자까지 입력 가능합니다.")
        private String senderName;

        @NotBlank(message = "주문인 전화번호는 필수 항목입니다.")
        @Pattern(regexp = "\\d{11}", message = "전화번호는 11자리 숫자로 입력해주세요")
        private String senderPhoneNumber;

        @NotBlank(message = "주문인 이메일은 필수 항목입니다.")
        @Size(max = 64, message = "주문인 이메일은 64자까지 입력가능합니다.")
        @Email(message = "이메일 형식을 지켜서 입력해주세요.")
        private String senderEmail;

        @NotNull(message = "배송 예정일은 필수 항목입니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        private LocalDate deliveryDate;

        @NotNull(message = "포인트는 필수 항목입니다.")
        @Min(value = 0, message = "포인트는 음수가 될 수 없습니다.")
        private Long usePoint;

        @NotNull(message = "결제 금액은 필수 항목입니다.")
        @Min(value = 0, message = "결제 금액은 음수가 될 수 없습니다.")
        private Long payAmount;

        private Long memberId;

        @NotNull(message = "배송비는 필수 항목입니다.")
        @Min(value = 0, message = "배송비는 음수가 될 수 없습니다.")
        private Long deliveryPrice;
    }
}
