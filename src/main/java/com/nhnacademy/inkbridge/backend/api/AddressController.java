package com.nhnacademy.inkbridge.backend.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.inkbridge.backend.controller.header.HeaderConstants;
import com.nhnacademy.inkbridge.backend.domain.AddressService;
import com.nhnacademy.inkbridge.backend.api.support.ApiSuccessResponse;
import com.nhnacademy.inkbridge.backend.api.support.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my/addresses")
public class AddressController {

	private final AddressService addressService;

	@PostMapping
	public ApiSuccessResponse<Void> createAddress(
		@RequestHeader(HeaderConstants.MEMBER_ID_HEADER) Long userId,
		@Valid @RequestBody AddressCreateRequest request) {

		addressService.createAddress(userId, request.toAddress());
		return ApiSuccessResponse.success(ResponseMessage.ADDRESS_CREATED);
	}

	@PutMapping
	public ApiSuccessResponse<Void> updateAddress(
		@RequestHeader(HeaderConstants.MEMBER_ID_HEADER) Long userId,
		@Valid @RequestBody AddressUpdateRequest request){

		addressService.updateAddress(userId, request.toAddress());
		return ApiSuccessResponse.success(ResponseMessage.ADDRESS_UPDATED);
	}

	@DeleteMapping("/{addressId}")
	public ApiSuccessResponse<Void> deleteAddress(
		@RequestHeader(HeaderConstants.MEMBER_ID_HEADER) Long userId,
		@PathVariable Long addressId){

		addressService.deleteAddress(userId, addressId);
		return ApiSuccessResponse.success(ResponseMessage.ADDRESS_DELETED);
	}
}
