package com.nhnacademy.inkbridge.backend.api.admin.publisher;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.inkbridge.backend.api.support.response.ApiSuccessResponse;
import com.nhnacademy.inkbridge.backend.api.support.response.PageResponse;
import com.nhnacademy.inkbridge.backend.api.support.response.ResponseMessage;
import com.nhnacademy.inkbridge.backend.domain.admin.publisher.Publisher;
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

	@DeleteMapping("/{publisherId}")
	public ApiSuccessResponse<Void> deletePublisher(@PathVariable Integer publisherId) {

		publisherAdminService.delete(publisherId);
		return ApiSuccessResponse.success(ResponseMessage.PUBLISHER_DELETED);
	}

	@GetMapping("/{publishId}")
	public ApiSuccessResponse<PublisherResponse> getPublisher(@PathVariable Integer publishId) {

		Publisher publisher = publisherAdminService.getPublisher(publishId);
		return ApiSuccessResponse.success(
			PublisherResponse.builder()
				.id(publisher.getId())
				.name(publisher.getName())
				.status(publisher.getStatus())
				.build(),
			ResponseMessage.PUBLISHER_READ_SUCCESS);
	}

	@GetMapping
	public ApiSuccessResponse<PageResponse<Publisher>> getPublishers(
		@Valid @ModelAttribute PublisherSearchRequest request) {

		Page<Publisher> publishers = publisherAdminService.getPublishers(request.getStatus(), request.getPage(),
			request.getSize());
		return ApiSuccessResponse.success(PageResponse.from(publishers), ResponseMessage.PUBLISHER_READ_SUCCESS);
	}

}
