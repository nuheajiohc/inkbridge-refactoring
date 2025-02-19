package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.annotation.Auth;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberUpdatePasswordRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: MemberMyPageController.
 *
 * @author devminseo
 * @version 3/19/24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/members")
@Slf4j
public class MemberMyPageController {
    private final MemberService memberService;

    @Auth
    @PutMapping("/{memberId}")
    public ResponseEntity<Void> updateMember(@Valid @RequestBody MemberUpdateRequestDto memberUpdateRequestDto,
                                             @PathVariable Long memberId) {
        memberService.updateMember(memberUpdateRequestDto,memberId);
        return ResponseEntity.ok().build();
    }

    @Auth
    @PutMapping("/{memberId}/password")
    public ResponseEntity<Boolean> updatePassword(@Valid @RequestBody
                                                  MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto,
                                                  @PathVariable("memberId") Long memberId) {
        Boolean result = memberService.updatePassword(memberUpdatePasswordRequestDto, memberId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }

    @Auth
    @GetMapping("/{memberId}/password")
    public ResponseEntity<String> getPassword(@PathVariable("memberId") Long memberId) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(memberService.getPassword(memberId));
    }

    @Auth
    @DeleteMapping("/{memberId}/delete")
    public ResponseEntity<Void> deleteMember(@PathVariable("memberId") Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok().build();
    }
}
