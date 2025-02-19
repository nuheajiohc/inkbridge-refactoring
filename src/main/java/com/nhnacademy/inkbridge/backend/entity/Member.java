package com.nhnacademy.inkbridge.backend.entity;

import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberUpdateRequestDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: member.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "member")
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "member_point")
    private Long memberPoint;

    @Column(name = "withdraw_at")
    private LocalDateTime withdrawAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_auth_id")
    private MemberAuth memberAuth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_status_id")
    private MemberStatus memberStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    private MemberGrade memberGrade;

    @Builder(builderMethodName = "create")
    public Member(String memberName, String phoneNumber, String email, LocalDate birthday,
        String password,
        LocalDateTime createdAt, Long memberPoint, MemberAuth memberAuth,
        MemberStatus memberStatus, MemberGrade memberGrade) {
        this.memberName = memberName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
        this.password = password;
        this.createdAt = createdAt;
        this.memberPoint = memberPoint;
        this.memberAuth = memberAuth;
        this.memberStatus = memberStatus;
        this.memberGrade = memberGrade;
    }

    public void updateMemberPoint(Long pointValue) {
        this.memberPoint += pointValue;
    }

    public void updateLastLoginDate() {
        this.lastLoginDate = LocalDateTime.now();
    }

    public void updateStatus(MemberStatus status) {
        this.memberStatus = status;
    }

    public void updateMember(MemberUpdateRequestDto update) {
        this.email = update.getEmail();
        this.memberName = update.getMemberName();
        this.phoneNumber = update.getPhoneNumber();
    }
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
    public void updateWithdrawAt() {
        this.withdrawAt = LocalDateTime.now();
    }
}
