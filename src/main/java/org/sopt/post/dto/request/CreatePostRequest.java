package org.sopt.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePostRequest(
		@NotBlank String category,
		@NotBlank String title,
		@NotBlank String content,
		@NotNull Long memberId
) {}