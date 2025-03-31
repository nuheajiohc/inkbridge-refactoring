package com.nhnacademy.inkbridge.backend.api.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.inkbridge.backend.api.support.ApiSuccessResponse;
import com.nhnacademy.inkbridge.backend.api.support.ResponseMessage;
import com.nhnacademy.inkbridge.backend.controller.header.HeaderConstants;
import com.nhnacademy.inkbridge.backend.domain.admin.AdminAccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminAccountController {

	private final AdminAccountService adminAccountService;

	@PostMapping("/accounts")
	public ApiSuccessResponse<?> createAdminAccount(@Valid @RequestBody AdminAccountCreateRequest request) {
		adminAccountService.create(request.toAdmin());
		return ApiSuccessResponse.success(ResponseMessage.ADMIN_ACCOUNT_CREATED);
	}

	@PutMapping("/accounts")
	public ApiSuccessResponse<?> updateAdminAccount(
		@RequestHeader(HeaderConstants.MEMBER_ID_HEADER) Integer loginId,
		@Valid @RequestBody AdminAccountUpdateRequest request) {

		adminAccountService.update(loginId,request.toAdmin());
		return ApiSuccessResponse.success(ResponseMessage.ADMIN_ACCOUNT_UPDATED);
	}


}
