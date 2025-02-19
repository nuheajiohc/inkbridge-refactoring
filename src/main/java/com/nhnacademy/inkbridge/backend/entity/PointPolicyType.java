package com.nhnacademy.inkbridge.backend.entity;

import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeUpdateRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: PointPolicyType.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@Getter
@Entity
@Table(name = "point_policy_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointPolicyType {

    @Id
    @Column(name = "point_policy_type_id")
    private Integer pointPolicyTypeId;

    @Column(name = "policy_type")
    private String policyType;

    @Builder
    public PointPolicyType(Integer pointPolicyTypeId, String policyType) {
        this.pointPolicyTypeId = pointPolicyTypeId;
        this.policyType = policyType;
    }

    /**
     * method: updatePointPolicyType.
     *
     * @param pointPolicyTypeUpdateRequestDto PointPolicyTypeUpdateRequestDto
     */
    public void setPointPolicyType(
        PointPolicyTypeUpdateRequestDto pointPolicyTypeUpdateRequestDto) {
        this.policyType = pointPolicyTypeUpdateRequestDto.getPolicyType();
    }
}
