package com.nhnacademy.inkbridge.backend.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.inkbridge.backend.controller.header.HeaderConstants;
import com.nhnacademy.inkbridge.backend.domain.Address;
import com.nhnacademy.inkbridge.backend.domain.AddressService;
import com.nhnacademy.inkbridge.backend.api.support.ApiSuccessResponse;
import com.nhnacademy.inkbridge.backend.api.support.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my/addresses")
public class AddressController {

	private final AddressService addressService;

	@GetMapping
	public ApiSuccessResponse<AddressesResponse> getAddresses(
		@RequestHeader(HeaderConstants.MEMBER_ID_HEADER) Long userId){

		List<Address> addresses = addressService.getAddresses(userId);
		return ApiSuccessResponse.success(new AddressesResponse(addresses), ResponseMessage.ADDRESS_READ_SUCCESS);
	}

	@GetMapping("/{addressId}")
	public ApiSuccessResponse<?> getAddress(
		@RequestHeader(HeaderConstants.MEMBER_ID_HEADER) Long userId,
		@PathVariable Long addressId){

		Address address = addressService.getAddress(addressId);
		return ApiSuccessResponse.success(address, ResponseMessage.ADDRESS_READ_SUCCESS);
	}

	@PostMapping
	public ApiSuccessResponse<AddressCreateResponse> createAddress(
		@RequestHeader(HeaderConstants.MEMBER_ID_HEADER) Long userId,
		@Valid @RequestBody AddressCreateRequest request) {

		Long addressId = addressService.createAddress(userId, request.toAddress());
		return ApiSuccessResponse.success(new AddressCreateResponse(addressId), ResponseMessage.ADDRESS_CREATED);
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
