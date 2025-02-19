//package com.nhnacademy.inkbridge.backend.repository;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.nhnacademy.inkbridge.backend.entity.Coupon;
//import com.nhnacademy.inkbridge.backend.entity.CouponType;
//import java.time.LocalDate;
//import java.util.Optional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
///**
// * class: CouponRepositoryTest.
// *
// * @author JBum
// * @version 2024/02/20
// */
//@ActiveProfiles("local")
//@DataJpaTest
//class CouponRepositoryTest {
//
//    List<CouponStatus> couponStatuses;
//    @Autowired
//    private CouponRepository couponRepository;
//    @Autowired
//    private CouponTypeRepository couponTypeRepository;
//    @Autowired
//    private CouponStatusRepository couponStatusRepository;
//    private Coupon coupon;
//    private CouponType couponType;
//
//    @BeforeEach
//    void setUp() {
//        couponType = new CouponType(1, "test");
//        couponTypeRepository.save(couponType);
//        List<String> couponStatusName = List.of("NORMAL", "DELETE", "EXPIRATION", "WAIT");
//        couponStatuses = new ArrayList<>();
//        int i = 1;
//        for (String couponName : couponStatusName) {
//            couponStatuses.add(
//                CouponStatus.builder().couponStatusId(i).couponStatusName(couponName).build());
//            i++;
//        }
//        couponStatusRepository.saveAllAndFlush(couponStatuses);
//        for (int j = 1; j <= 100; j++) {
//            // 쿠폰 생성
//            coupon = Coupon.builder()
//                .couponId("ABC1234" + j)
//                .couponName("Test Coupon")
//                .minPrice(10000L)
//                .maxDiscountPrice(5000L)
//                .discountPrice(2000L)
//                .basicIssuedDate(LocalDate.of(2024, 2, 20))
//                .basicExpiredDate(LocalDate.of(2024, 12, 31))
//                .validity(30)
//                .isBirth(true)
//                .couponType(couponType)
//                .couponStatus(couponStatuses.get(j % 4))
//                .build();
//            couponRepository.saveAndFlush(coupon);
//        }
//
//
//    }
//
//    @Test
//    void findByCouponStatus() {
//
//        Pageable pageable = PageRequest.of(0, 30);
//        assertAll(
//            () -> assertEquals(25, couponRepository.findByCouponStatus(
//                couponStatuses.get(0),
//                pageable).getContent().size()),
//            () -> assertEquals(25, couponRepository.findByCouponStatus(
//                couponStatuses.get(1), pageable).getContent().size())
//        );
//
//    }
//}