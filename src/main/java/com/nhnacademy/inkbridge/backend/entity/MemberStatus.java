package com.nhnacademy.inkbridge.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: MemberStatus.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Entity
@Table(name = "member_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberStatus {

    @Id
    @Column(name = "member_status_id")
    private Integer memberStatusId;

    @Column(name = "member_status_name")
    private String memberStatusName;

    @Builder(builderMethodName = "create")
    public MemberStatus(Integer memberStatusId, String memberStatusName) {
        this.memberStatusId = memberStatusId;
        this.memberStatusName = memberStatusName;
    }
}
