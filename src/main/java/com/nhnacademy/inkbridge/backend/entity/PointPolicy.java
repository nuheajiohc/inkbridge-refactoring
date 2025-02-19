package com.nhnacademy.inkbridge.backend.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: AdminOption.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "point_policy")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_policy_id")
    private Long pointPolicyId;

    @Column(name = "accumulate_point")
    private Long accumulatePoint;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_policy_type_id")
    private PointPolicyType pointPolicyType;

    /**
     * 포인트 정책 생성자입니다.
     *
     * @param pointPolicyId Long
     * @param accumulatePoint Long
     * @param createdAt LocalDate
     * @param pointPolicyType PointPolicyType
     */
    @Builder
    public PointPolicy(Long pointPolicyId, Long accumulatePoint, LocalDate createdAt,
        PointPolicyType pointPolicyType) {
        this.pointPolicyId = pointPolicyId;
        this.accumulatePoint = accumulatePoint;
        this.createdAt = createdAt;
        this.pointPolicyType = pointPolicyType;
    }
}
