package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.membergrade.MemberGradeReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.membergrade.MemberGradeUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.service.MemberGradeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: MemberGradeController.
 *
 * @author jeongbyeonghun
 * @version 3/6/24
 */

@RestController
@RequestMapping("/api/admin/member/grade")
@RequiredArgsConstructor
public class MemberGradeController {

    private final MemberGradeService memberGradeService;

    @GetMapping
    public ResponseEntity<List<MemberGradeReadResponseDto>> getGradeList() {
        return ResponseEntity.ok(memberGradeService.getGradeList());
    }

    @PutMapping("/{gradeId}")
    public ResponseEntity<HttpStatus> updateGrade(@PathVariable(name = "gradeId") Integer gradeId,
        @RequestBody MemberGradeUpdateRequestDto memberGradeUpdateRequestDto) {
        memberGradeService.updateGrade(gradeId, memberGradeUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
