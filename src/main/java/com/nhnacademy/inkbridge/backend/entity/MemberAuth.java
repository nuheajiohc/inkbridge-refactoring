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
 * class: MemberAuth.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Entity
@Table(name = "member_auth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberAuth {

    @Id
    @Column(name = "member_auth_id")
    private Integer memberAuthId;

    @Column(name = "member_auth_name")
    private String memberAuthName;

    @Builder(builderMethodName = "create")
    public MemberAuth(Integer memberAuthId, String memberAuthName) {
        this.memberAuthId = memberAuthId;
        this.memberAuthName = memberAuthName;
    }
}
