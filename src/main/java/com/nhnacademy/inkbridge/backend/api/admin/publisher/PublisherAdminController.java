package com.nhnacademy.inkbridge.backend.api.admin.publisher;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.inkbridge.backend.api.support.ApiSuccessResponse;
import com.nhnacademy.inkbridge.backend.api.support.ResponseMessage;
import com.nhnacademy.inkbridge.backend.domain.admin.publisher.PublisherAdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/publishers")
public class PublisherAdminController {

	private final PublisherAdminService publisherAdminService;

	@PostMapping
	public ApiSuccessResponse<PublisherCreateResponse> createPublisher(
		@Valid @RequestBody PublisherCreateRequest request) {

		Integer publisherId = publisherAdminService.create(request.toPublisher());
		return ApiSuccessResponse.success(new PublisherCreateResponse(publisherId), ResponseMessage.PUBLISHER_CREATED);
	}

	@PutMapping("/{publisherId}")
	public ApiSuccessResponse<Void> updatePublisher(@PathVariable Integer publisherId,
		@Valid @RequestBody PublisherUpdateRequest request) {

		publisherAdminService.update(publisherId, request.toPublisher());
		return ApiSuccessResponse.success(ResponseMessage.PUBLISHER_UPDATED);
	}
}
