package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberInfoResponseDto;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: MemberCustomRepository.
 *
 * @author devminseo
 * @version 3/2/24
 */
@NoRepositoryBean
public interface MemberCustomRepository {
    /**
     * 로그인에 필요한 회원 정보를 가져오는 메서드.
     *
     * @param email 아이디
     * @return 회원 정보
     */
    MemberAuthLoginResponseDto findByMemberAuth(String email);

    /**
     * 로그인후 회원 정보를 가져오는 메서드.
     *
     * @param memberId 회원 아이디
     * @return 회원 정보
     */
    Optional<MemberInfoResponseDto> findByMemberInfo(Long memberId);


}
