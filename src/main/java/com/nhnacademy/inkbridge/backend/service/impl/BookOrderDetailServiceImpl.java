package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.book.BookStockUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderDetailCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.BookOrderDetail;
import com.nhnacademy.inkbridge.backend.entity.BookOrderStatus;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import com.nhnacademy.inkbridge.backend.entity.Wrapping;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.OrderMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.OrderStatusEnum;
import com.nhnacademy.inkbridge.backend.exception.AlreadyProcessedException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookOrderDetailRepository;
import com.nhnacademy.inkbridge.backend.repository.BookOrderRepository;
import com.nhnacademy.inkbridge.backend.repository.BookOrderStatusRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberCouponRepository;
import com.nhnacademy.inkbridge.backend.repository.WrappingRepository;
import com.nhnacademy.inkbridge.backend.service.BookOrderDetailService;
import com.nhnacademy.inkbridge.backend.dto.order.OrderBooksIdResponseDto;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: BookOrderDetailServiceImpl.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookOrderDetailServiceImpl implements BookOrderDetailService {

    private final BookOrderRepository bookOrderRepository;
    private final BookOrderDetailRepository bookOrderDetailRepository;
    private final BookOrderStatusRepository bookOrderStatusRepository;
    private final BookRepository bookRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final WrappingRepository wrappingRepository;

    /**
     * {@inheritDoc}
     *
     * @param orderId        주문 번호
     * @param requestDtoList 주문 상세 정보 목록
     */
    @Override
    public void createBookOrderDetail(Long orderId,
        List<BookOrderDetailCreateRequestDto> requestDtoList) {

        BookOrder bookOrder = bookOrderRepository.findById(orderId)
            .orElseThrow(
                () -> new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage()));

        List<BookOrderDetail> bookOrderDetailList = requestDtoList.stream()
            .map(requestDto -> {
                Book book = bookRepository.findById(requestDto.getBookId())
                    .orElseThrow(
                        () -> new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage()));

                MemberCoupon coupon = Objects.nonNull(requestDto.getCouponId())
                    ? memberCouponRepository.findById(requestDto.getCouponId())
                    .orElseThrow(() -> new NotFoundException(
                        CouponMessageEnum.COUPON_NOT_FOUND.getMessage())) : null;

                BookOrderStatus bookOrderStatus = bookOrderStatusRepository.findById(
                        OrderStatusEnum.WAITING.getOrderStatusId())
                    .orElseThrow(() -> new NotFoundException(
                        OrderMessageEnum.ORDER_STATUS_NOT_FOUND.getMessage()));

                Wrapping wrapping = Objects.nonNull(requestDto.getWrappingId())
                    ? wrappingRepository.findById(requestDto.getWrappingId())
                    .orElseThrow(() -> new NotFoundException(
                        OrderMessageEnum.WRAPPING_NOT_FOUND.getMessage())) : null;

                return BookOrderDetail.builder()
                    .bookPrice(requestDto.getPrice())
                    .wrappingPrice(requestDto.getWrappingPrice())
                    .amount(requestDto.getAmount())
                    .bookOrderStatus(bookOrderStatus)
                    .wrapping(wrapping)
                    .book(book)
                    .memberCoupon(coupon)
                    .bookOrder(bookOrder)
                    .build();
            }).collect(Collectors.toList());

        bookOrderDetailRepository.saveAll(bookOrderDetailList);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 적용 쿠폰 번호 목록
     */
    @Transactional(readOnly = true)
    @Override
    public List<Long> getUsedCouponIdByOrderCode(String orderCode) {
        return bookOrderDetailRepository.findAllByOrderCode(orderCode);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 주문 수량 목록
     */
    @Override
    public List<BookStockUpdateRequestDto> getBookStock(String orderCode) {
        return bookOrderDetailRepository.findBookStockByOrderCode(orderCode);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 도서 번호 목록
     */
    @Override
    public List<OrderBooksIdResponseDto> getOrderBooksIdByOrderId(String orderCode) {
        if (!bookOrderRepository.existsByOrderCode(orderCode)) {
            throw new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage());
        }

        return bookOrderDetailRepository.findBookIdByOrderCode(orderCode);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 번호
     * @return 주문 상세 목록
     */
    @Transactional(readOnly = true)
    @Override
    public List<OrderDetailReadResponseDto> getOrderDetailListByOrderId(Long orderId) {
        return bookOrderDetailRepository.findAllOrderDetailByOrderId(orderId);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 주문 상세 목록
     */
    @Transactional(readOnly = true)
    @Override
    public List<OrderDetailReadResponseDto> getOrderDetailByOrderCode(String orderCode) {
        return bookOrderDetailRepository.findAllOrderDetailByOrderCode(orderCode);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문번호
     * @param status  주문 상태
     */
    @Transactional
    @Override
    public void changeOrderStatus(Long orderId, OrderStatusEnum status) {
        List<BookOrderDetail> bookOrderDetailList = bookOrderDetailRepository.findOrderDetailByOrderId(
            orderId);

        BookOrderStatus bookOrderStatus = bookOrderStatusRepository.findById(
            status.getOrderStatusId()).orElseThrow(
            () -> new NotFoundException(OrderMessageEnum.ORDER_STATUS_NOT_FOUND.getMessage()));

        bookOrderDetailList.forEach(bookOrder -> {
            if (bookOrder.getBookOrderStatus() == bookOrderStatus) {
                throw new AlreadyProcessedException(
                    OrderMessageEnum.ALREADY_PROCESSED.getMessage());
            }

            bookOrder.updateStatus(bookOrderStatus);
        });

    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @param status  주문 상태
     */
    @Override
    public void changeOrderStatusByOrderCode(String orderCode, OrderStatusEnum status) {
        List<BookOrderDetail> bookOrderDetailList = bookOrderDetailRepository.findOrderDetailByOrderCode(
            orderCode);

        BookOrderStatus bookOrderStatus = bookOrderStatusRepository.findById(
            status.getOrderStatusId()).orElseThrow(
            () -> new NotFoundException(OrderMessageEnum.ORDER_STATUS_NOT_FOUND.getMessage()));

        bookOrderDetailList.forEach(bookOrder -> {
            if (bookOrder.getBookOrderStatus() == bookOrderStatus) {
                throw new AlreadyProcessedException(
                    OrderMessageEnum.ALREADY_PROCESSED.getMessage());
            }

            bookOrder.updateStatus(bookOrderStatus);
        });

    }

}
