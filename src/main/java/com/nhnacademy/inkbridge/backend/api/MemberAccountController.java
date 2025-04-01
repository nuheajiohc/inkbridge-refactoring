package com.nhnacademy.inkbridge.backend.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.inkbridge.backend.api.support.ApiSuccessResponse;
import com.nhnacademy.inkbridge.backend.api.support.ResponseMessage;
import com.nhnacademy.inkbridge.backend.controller.header.HeaderConstants;
import com.nhnacademy.inkbridge.backend.domain.MemberAccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class MemberAccountController {

	private final MemberAccountService memberAccountService;

	@PostMapping
	public ApiSuccessResponse<Void> createMemberAccount(@Valid @RequestBody MemberAccountCreateRequest request){
		memberAccountService.create(request.toMember());
		return ApiSuccessResponse.success(ResponseMessage.ACCOUNT_CREATED);
	}

	@PutMapping
	public ApiSuccessResponse<Void> updateMemberAccount(
		@RequestHeader(HeaderConstants.MEMBER_ID_HEADER) Integer loginId,
		@Valid @RequestBody MemberAccountUpdateRequest request){
		memberAccountService.update(loginId, request.toMember());
		return ApiSuccessResponse.success(ResponseMessage.ACCOUNT_UPDATED);
	}

	@DeleteMapping
	public ApiSuccessResponse<Void> deleteMemberAccount(@RequestHeader(HeaderConstants.MEMBER_ID_HEADER) Integer loginId){
		memberAccountService.delete(loginId);
		return ApiSuccessResponse.success(ResponseMessage.ACCOUNT_DELETED);
	}
}
