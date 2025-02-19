package com.nhnacademy.inkbridge.backend.dto.member.response;

import com.nhnacademy.inkbridge.backend.entity.Member;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: MemberInfoResponseDto.
 *
 * @author devminseo
 * @version 3/2/24
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponseDto {
    private Long memberId;
    private String memberName;
    private String email;
    private String phoneNumber;
    private String birthday;
    private Long memberPoint;
    private List<String> roles;

    /**
     * 멤버 엔티티로 dto 변환
     * @param member 엔티티
     */
    public MemberInfoResponseDto(Member member) {
        this.memberId = member.getMemberId();
        this.memberName = member.getMemberName();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
        this.birthday = member.getBirthday().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.memberPoint = member.getMemberPoint();
        this.roles = List.of(member.getMemberAuth().getMemberAuthName());
    }
}
