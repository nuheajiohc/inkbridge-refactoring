package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import com.nhnacademy.inkbridge.backend.repository.custom.MemberCouponCustomRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: MemberCouponRepository.
 *
 * @author JBum
 * @version 2024/02/19
 */
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long>,
    MemberCouponCustomRepository {

    /**
     * 특정 쿠폰이 특정 회원에게 이미 발급되었는지 확인합니다.
     *
     * @param coupon 쿠폰 발급 여부를 확인할 쿠폰
     * @param member 쿠폰 발급 여부를 확인할 회원
     * @return 해당 회원이 해당 쿠폰을 이미 발급받았으면 true, 그렇지 않으면 false를 반환합니다.
     */
    boolean existsByCouponAndMember(Coupon coupon, Member member);

    /**
     * 사용자가 가진 쿠폰들중 사용이 가능한것들만 보여준다.
     *
     * @param memberId 조회할 회원 Id
     * @return 현재 사용자가 가진 사용가능한 쿠폰
     */
    Page<MemberCoupon> findByMember_MemberIdAndUsedAtIsNullAndExpiredAtAfterOrExpiredAt(
        Long memberId, LocalDate now, LocalDate now2, Pageable pageable);

    /**
     * 사용자가 가진 쿠폰들중 사용이 불가능한것들만 보여준다.
     *
     * @param memberId 조회할 회원 Id
     * @return 현재 사용자가 가진 사용불가능한 쿠폰
     */
    Page<MemberCoupon> findByMember_MemberIdAndUsedAtIsNotNull(
        Long memberId, Pageable pageable);

    /**
     * 사용자가 가진 쿠폰들중 사용한것들만 보여준다.
     *
     * @param memberId 조회할 회원 Id
     * @return 현재 사용자가 가진 사용한 쿠폰
     */
    Page<MemberCoupon> findByMember_MemberIdAndExpiredAtBeforeAndUsedAtIsNull(Long memberId,
        LocalDate now, Pageable pageable);

    /**
     * 사용 처리할 쿠폰을 찾습니다. 찾을 때는 해당 쿠폰id와  맴버id로 찾습니다.
     *
     * @param memberCouponIds 맴버의 쿠폰id들
     * @param memberId        맴버id
     */
    List<MemberCoupon> findAllByMemberCouponIdInAndMember_MemberId(List<Long> memberCouponIds,
        Long memberId);
}
