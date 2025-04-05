package com.nhnacademy.inkbridge.backend.api.support.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PageResponse<T> {

	private List<T> content;
	private int page;
	private int size;
	private long count;
	private int totalPages;
	private boolean isFirst;
	private boolean isLast;

	@Builder
	public PageResponse(List<T> content, int page, int size, long count, int totalPages, boolean isFirst,
		boolean isLast) {
		this.content = content;
		this.page = page;
		this.size = size;
		this.count = count;
		this.totalPages = totalPages;
		this.isFirst = isFirst;
		this.isLast = isLast;
	}

	public static <T> PageResponse<T> from(Page<T> pages){
		return PageResponse.<T>builder()
			.content(pages.getContent())
			.page(pages.getNumber())
			.size(pages.getSize())
			.count(pages.getTotalElements())
			.totalPages(pages.getTotalPages())
			.isFirst(pages.isFirst())
			.isLast(pages.isLast())
			.build();
	}
}
