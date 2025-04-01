package com.nhnacademy.inkbridge.backend.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.inkbridge.backend.api.support.ApiSuccessResponse;
import com.nhnacademy.inkbridge.backend.api.support.ResponseMessage;
import com.nhnacademy.inkbridge.backend.domain.MemberAccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class MemberAccountController {

	private final MemberAccountService memberAccountService;

	@PostMapping
	public ApiSuccessResponse<?> createMemberAccount(@Valid @RequestBody MemberAccountCreateRequest request){
		memberAccountService.create(request.toMember());
		return ApiSuccessResponse.success(ResponseMessage.ACCOUNT_CREATED);
	}
}
