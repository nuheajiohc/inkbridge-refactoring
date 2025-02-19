package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginInfoDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberInfoResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.QMember;
import com.nhnacademy.inkbridge.backend.entity.QMemberAuth;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.custom.MemberCustomRepository;
import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: MemberRepositoryImpl.
 *
 * @author devminseo
 * @version 3/2/24
 */
public class MemberRepositoryImpl extends QuerydslRepositorySupport implements MemberCustomRepository {
    public MemberRepositoryImpl() {
        super(Member.class);
    }

    QMember member = QMember.member;
    QMemberAuth memberAuth = QMemberAuth.memberAuth;

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberAuthLoginResponseDto findByMemberAuth(String email) {
        Optional<MemberAuthLoginInfoDto> memberResult = Optional.ofNullable(
                from(member)
                        .select(Projections.constructor(MemberAuthLoginInfoDto.class,
                                member.memberId,
                                member.email,
                                member.password,
                                member.memberAuth.memberAuthName))
                        .where(member.email.eq(email))
                        .fetchOne());
        if (memberResult.isEmpty()) {
            throw new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
        }

        return new MemberAuthLoginResponseDto(memberResult.get().getMemberId(), memberResult.get().getEmail(),
                memberResult.get()
                        .getPassword(), List.of(memberResult.get().getMemberAuthName()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MemberInfoResponseDto> findByMemberInfo(Long memberId) {
        Optional<Member> memberResult = Optional.ofNullable(
                from(member)
                        .leftJoin(memberAuth)
                        .on(member.memberAuth.memberAuthId.eq(memberAuth.memberAuthId))
                        .where(member.memberId.eq(memberId))
                        .select(member)
                        .fetchOne()
        );
        return memberResult.map(MemberInfoResponseDto::new);
    }
}
