package org.sopt.global.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(
		List<T> content,
		PageInfo pageInfo
) {

	public static <T> PageResponse<T> from(Page<T> page) {
		return new PageResponse<>(
				page.getContent(),
				new PageInfo(
						page.getNumber() + 1,
						page.getSize(),
						page.getTotalElements(),
						page.getTotalPages(),
						page.hasNext(),
						page.hasPrevious()
				)
		);
	}

	public record PageInfo(
			int page,
			int size,
			long totalElements,
			int totalPages,
			boolean hasNext,
			boolean hasPrevious
	) {
	}
}
