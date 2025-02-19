package com.nhnacademy.inkbridge.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.dto.order.BookOrderDetailResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pay.PayReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.OrderMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.AlreadyProcessedException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.facade.OrderFacade;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: OrderAdminControllerTest.
 *
 * @author jangjaehun
 * @version 2024/03/21
 */
@WebMvcTest(OrderAdminController.class)
@AutoConfigureRestDocs
class OrderAdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderFacade orderFacade;

    @Test
    @DisplayName("주문 목록 조회")
    void testGetOrderList() throws Exception {
        OrderReadResponseDto response = new OrderReadResponseDto(1L, "orderCode",
            "orderName", LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            LocalDate.of(2024, 1, 1), 30000L);

        given(orderFacade.getOrderList(any())).willReturn(new PageImpl<>(List.of(response)));

        mockMvc.perform(get("/api/admin/orders")
                .queryParam("page", "1")
                .queryParam("size", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.content[0].orderId").value(response.getOrderId()),
                jsonPath("$.content[0].orderCode").value(response.getOrderCode()),
                jsonPath("$.content[0].orderName").value(response.getOrderName()),
                jsonPath("$.content[0].orderAt").value(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                        .format(response.getOrderAt())),
                jsonPath("$.content[0].deliveryDate").value(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd").format(response.getDeliveryDate())),
                jsonPath("$.content[0].totalPrice").value(response.getTotalPrice())
            )
            .andDo(document("order/admin/page-get-200",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                relaxedResponseFields(
                    fieldWithPath("content[].orderId").description("주문 번호"),
                    fieldWithPath("content[].orderCode").description("주문 코드"),
                    fieldWithPath("content[].orderName").description("주문 이름"),
                    fieldWithPath("content[].orderAt").description("주문일"),
                    fieldWithPath("content[].deliveryDate").description("배송 예정일"),
                    fieldWithPath("content[].totalPrice").description("주문 금액"),
                    fieldWithPath("pageable").description("페이지네이션 정보"),
                    fieldWithPath("last").description("마지막 페이지인지 여부"),
                    fieldWithPath("totalPages").description("페이지 수"),
                    fieldWithPath("totalElements").description("전체 데이터 수"),
                    fieldWithPath("number").description("현재 페이지 번호"),
                    fieldWithPath("first").description("첫 페이지인지 여부"),
                    fieldWithPath("sort.empty").description("정렬기준 비어있는지 여부"),
                    fieldWithPath("sort.sorted").description("정렬이 되어있는지"),
                    fieldWithPath("sort.unsorted").description("정렬이 안되어있는지"),
                    fieldWithPath("size").description("한 페이지 크기"),
                    fieldWithPath("numberOfElements").description("데이터 수"),
                    fieldWithPath("empty").description("페이지가 비어있는지")
                )));

    }

    @Test
    @DisplayName("주문 번호로 주문 조회 - 일치하는 주문이 없는 경우")
    void testGetOrderDetailByOrderId_not_found() throws Exception {
        given(orderFacade.getOrderDetailByOrderId(anyLong()))
            .willThrow(new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage()));

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/admin/orders/{orderId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                status().isNotFound(),
                exception -> assertThat(exception.getResolvedException())
                    .isInstanceOf(NotFoundException.class)
            )
            .andDo(document("order/admin/get-order-id/404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("orderId").description("주문 번호")
                )));
    }

    @Test
    @DisplayName("주문 번호로 주문 조회 - 성공")
    void testGetOrderDetailByOrderId_success() throws Exception {
        OrderResponseDto orderResponse = new OrderResponseDto("orderCode",
            "orderName", "receiver", "01011112222",
            "11111", "address", "detailAddress", "sender",
            "01033334444", "test@inkbridge.store",
            LocalDate.of(2024, 1, 1), 0L, 30000L,
            5000L, LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            LocalDate.of(2024, 1, 1));

        PayReadResponseDto payResponse = new PayReadResponseDto("paymentKey",
            "method", "status",
            LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            30000L, 30000L, 3000L, true, "provider");

        OrderDetailReadResponseDto orderDetailReadResponseDto = new OrderDetailReadResponseDto(1L,
            30000L, 0L, 1,
            "wrappingName", "WAITING", 1L,
            "thumbnailUrl", "bookTitle", "PERCENT",
            "couponName", 15000L, 5L);
        List<OrderDetailReadResponseDto> detailResponseList = List.of(
            orderDetailReadResponseDto);

        given(orderFacade.getOrderDetailByOrderId(anyLong())).willReturn(
            BookOrderDetailResponseDto.builder().payInfo(payResponse)
                .orderDetailInfoList(detailResponseList).orderInfo(orderResponse).build());

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/admin/orders/{orderId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.orderInfo.orderCode").value(orderResponse.getOrderCode()),
                jsonPath("$.orderInfo.orderName").value(orderResponse.getOrderName()),
                jsonPath("$.orderInfo.receiverName").value(orderResponse.getReceiverName()),
                jsonPath("$.orderInfo.receiverPhoneNumber").value(
                    orderResponse.getReceiverPhoneNumber()),
                jsonPath("$.orderInfo.zipCode").value(orderResponse.getZipCode()),
                jsonPath("$.orderInfo.address").value(orderResponse.getAddress()),
                jsonPath("$.orderInfo.detailAddress").value(orderResponse.getDetailAddress()),
                jsonPath("$.orderInfo.senderName").value(orderResponse.getSenderName()),
                jsonPath("$.orderInfo.senderPhoneNumber").value(
                    orderResponse.getSenderPhoneNumber()),
                jsonPath("$.orderInfo.senderEmail").value(orderResponse.getSenderEmail()),
                jsonPath("$.orderInfo.deliveryDate").value(DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    .format(orderResponse.getDeliveryDate())),
                jsonPath("$.orderInfo.usePoint").value(orderResponse.getUsePoint()),
                jsonPath("$.orderInfo.payAmount").value(orderResponse.getPayAmount()),
                jsonPath("$.orderInfo.deliveryPrice").value(orderResponse.getDeliveryPrice()),
                jsonPath("$.orderInfo.orderAt").value(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                        .format(orderResponse.getOrderAt())),
                jsonPath("$.orderInfo.shipDate").value(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd").format(orderResponse.getShipDate())),
                jsonPath("$.payInfo.paymentKey").value(payResponse.getPaymentKey()),
                jsonPath("$.payInfo.method").value(payResponse.getMethod()),
                jsonPath("$.payInfo.status").value(payResponse.getStatus()),
                jsonPath("$.payInfo.requestedAt").value(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                        .format(payResponse.getRequestedAt())),
                jsonPath("$.payInfo.totalAmount").value(payResponse.getTotalAmount()),
                jsonPath("$.payInfo.balanceAmount").value(payResponse.getBalanceAmount()),
                jsonPath("$.payInfo.vat").value(payResponse.getVat()),
                jsonPath("$.payInfo.isPartialCancelable").value(
                    payResponse.getIsPartialCancelable()),
                jsonPath("$.payInfo.provider").value(payResponse.getProvider()),
                jsonPath("$.orderDetailInfoList[0].orderDetailId").value(
                    orderDetailReadResponseDto.getOrderDetailId()),
                jsonPath("$.orderDetailInfoList[0].bookPrice").value(
                    orderDetailReadResponseDto.getBookPrice()),
                jsonPath("$.orderDetailInfoList[0].wrappingPrice").value(
                    orderDetailReadResponseDto.getWrappingPrice()),
                jsonPath("$.orderDetailInfoList[0].amount").value(
                    orderDetailReadResponseDto.getAmount()),
                jsonPath("$.orderDetailInfoList[0].wrappingName").value(
                    orderDetailReadResponseDto.getWrappingName()),
                jsonPath("$.orderDetailInfoList[0].orderStatus").value(
                    orderDetailReadResponseDto.getOrderStatus()),
                jsonPath("$.orderDetailInfoList[0].bookId").value(
                    orderDetailReadResponseDto.getBookId()),
                jsonPath("$.orderDetailInfoList[0].thumbnailUrl").value(
                    orderDetailReadResponseDto.getThumbnailUrl()),
                jsonPath("$.orderDetailInfoList[0].bookTitle").value(
                    orderDetailReadResponseDto.getBookTitle()),
                jsonPath("$.orderDetailInfoList[0].couponTypeName").value(
                    orderDetailReadResponseDto.getCouponTypeName()),
                jsonPath("$.orderDetailInfoList[0].couponName").value(
                    orderDetailReadResponseDto.getCouponName()),
                jsonPath("$.orderDetailInfoList[0].maxDiscountPrice").value(
                    orderDetailReadResponseDto.getMaxDiscountPrice()),
                jsonPath("$.orderDetailInfoList[0].discountPrice").value(
                    orderDetailReadResponseDto.getDiscountPrice())
            )
            .andDo(document("order/admin/get-order-id/200",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("orderId").description("주문 번호")
                ),
                relaxedResponseFields(
                    fieldWithPath("orderInfo.orderCode").description("주문 코드"),
                    fieldWithPath("orderInfo.orderName").description("주문 이름"),
                    fieldWithPath("orderInfo.receiverName").description("수취인 이름"),
                    fieldWithPath("orderInfo.receiverPhoneNumber").description("수취인 전화번호"),
                    fieldWithPath("orderInfo.zipCode").description("우편 번호"),
                    fieldWithPath("orderInfo.address").description("주소"),
                    fieldWithPath("orderInfo.detailAddress").description("상세 주소"),
                    fieldWithPath("orderInfo.senderName").description("주문인 이름"),
                    fieldWithPath("orderInfo.senderPhoneNumber").description("주문인 전화번호"),
                    fieldWithPath("orderInfo.senderEmail").description("주문인 이메일"),
                    fieldWithPath("orderInfo.deliveryDate").description("배송 예정일"),
                    fieldWithPath("orderInfo.usePoint").description("사용 포인트"),
                    fieldWithPath("orderInfo.payAmount").description("총 가격"),
                    fieldWithPath("orderInfo.deliveryPrice").description("배송비"),
                    fieldWithPath("orderInfo.orderAt").description("주문일"),
                    fieldWithPath("orderInfo.shipDate").description("출고일"),
                    fieldWithPath("payInfo.paymentKey").description("결제 키"),
                    fieldWithPath("payInfo.method").description("결제 방법"),
                    fieldWithPath("payInfo.status").description("결제 상태"),
                    fieldWithPath("payInfo.requestedAt").description("결제 요청 시간"),
                    fieldWithPath("payInfo.totalAmount").description("결제 금액"),
                    fieldWithPath("payInfo.balanceAmount").description("취소 가능 금액"),
                    fieldWithPath("payInfo.vat").description("부가세"),
                    fieldWithPath("payInfo.isPartialCancelable").description("부분 취소 여부"),
                    fieldWithPath("payInfo.provider").description("결제 회사"),
                    fieldWithPath("orderDetailInfoList[].orderDetailId").description("주문 상세 번호"),
                    fieldWithPath("orderDetailInfoList[].bookPrice").description("도서 가격"),
                    fieldWithPath("orderDetailInfoList[].wrappingPrice").description("포장 가격"),
                    fieldWithPath("orderDetailInfoList[].amount").description("도서 수량"),
                    fieldWithPath("orderDetailInfoList[].wrappingName").description("포장지 이름"),
                    fieldWithPath("orderDetailInfoList[].orderStatus").description("주문 상태"),
                    fieldWithPath("orderDetailInfoList[].bookId").description("도서 번호"),
                    fieldWithPath("orderDetailInfoList[].thumbnailUrl").description("도서 이미지 경로"),
                    fieldWithPath("orderDetailInfoList[].bookTitle").description("도서 이름"),
                    fieldWithPath("orderDetailInfoList[].couponTypeName").description("쿠폰 타입"),
                    fieldWithPath("orderDetailInfoList[].couponName").description("쿠폰 이름"),
                    fieldWithPath("orderDetailInfoList[].maxDiscountPrice").description(
                        "쿠폰 최대 할인 금액"),
                    fieldWithPath("orderDetailInfoList[].discountPrice").description("쿠폰 할인가")
                )));
    }

    @Test
    @DisplayName("주문 상태 변경 - 이미 배송중으로 상태가 변경된 경우")
    void testUpdateOrderStatus_already_process() throws Exception {
        doThrow(new AlreadyProcessedException(OrderMessageEnum.ALREADY_PROCESSED.getMessage()))
            .when(orderFacade).updateStatus(anyLong(), any());

        mockMvc.perform(put("/api/admin/orders/{orderId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andDo(document("order/admin/put-409",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));

        verify(orderFacade, times(1)).updateStatus(anyLong(), any());
    }

    @Test
    @DisplayName("주문 상태 변경 - 성공")
    void testUpdateOrderStatus_success() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/admin/orders/{orderId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("order/admin/put-200",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("orderId").description("주문 번호")
                )));

        verify(orderFacade, times(1)).updateStatus(anyLong(), any());
    }
}